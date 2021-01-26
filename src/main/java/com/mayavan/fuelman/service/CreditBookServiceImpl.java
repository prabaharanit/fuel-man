package com.mayavan.fuelman.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mayavan.fuelman.exception.ResourceNotFoundException;
import com.mayavan.fuelman.repo.CreditBookRepository;
import com.mayavan.fuelman.repo.CreditTransactionRepository;
import com.mayavan.fuelman.repo.model.CreditBook;
import com.mayavan.fuelman.repo.model.CreditBookMO;
import com.mayavan.fuelman.repo.model.CreditTransaction;
import com.mayavan.fuelman.repo.model.CreditTransactionMO;
import com.mayavan.fuelman.repo.model.FuelPriceMO;
import com.mayavan.fuelman.util.DateAndTimeUtility;
import com.mayavan.fuelman.util.DateAndTimeUtility.DATEFORMAT;

@Service
public class CreditBookServiceImpl implements CreditBookService {

	Logger logger = LoggerFactory.getLogger(CreditBookServiceImpl.class);

	@Autowired
	public CreditBookRepository creditBookRepository;

	@Autowired
	public CreditTransactionRepository creditTransactionRepo;

	@Autowired
	public VehicleService vehicleServiceImpl;

	@Autowired
	public VehicleOwnerServiceImpl vehicleOwnerServiceImpl;

	@Autowired
	public FuelService fuelPriceServiceImpl;

	@Override
	public CreditBookMO createCreditBook(CreditBookMO creditBookMO) throws Exception {
		try {
			updateDebitTransaction(creditBookMO, null);
			creditBookRepository.save(mapMOtoDO(creditBookMO, new CreditBook()));
		} catch (DataIntegrityViolationException pExp) {
			throw new Exception(pExp.getRootCause().getLocalizedMessage());
		} catch (Exception exp) {
			logger.error("Error while creating new credit entry", exp);
			throw new Exception("Error while creating new credit entry", exp);
		}
		return creditBookMO;
	}

	private void updateDebitTransaction(CreditBookMO creditBookMO, CreditBook oldCreditBook) throws Exception {
		CreditTransactionMO creditTransMO = getCreditTransByVhOwnerId(creditBookMO.getVehicleMO().getVhOwner().getId());

		if (creditTransMO == null) {
			creditTransMO = new CreditTransactionMO();
			creditTransMO.setVhOwner(creditBookMO.getVehicleMO().getVhOwner());
			creditTransMO.setDebit(creditBookMO.getAmount_of_sale());
			createCreditTransaction(creditTransMO);
		} else {
			creditTransMO.setVhOwner(creditBookMO.getVehicleMO().getVhOwner());
			double sumDebit = 0;
			if (null != oldCreditBook) {
				sumDebit = creditTransMO.getDebit() - oldCreditBook.getAmount_of_sale();
				sumDebit += creditBookMO.getAmount_of_sale();
				System.out.println("sum debit calculation " + sumDebit + "   " + creditBookMO.getAmount_of_sale()
						+ "   " + creditTransMO.getDebit() + "   " + oldCreditBook.getAmount_of_sale());
			} else {
				// creditTransMO.addDebit(creditBookMO.getAmount_of_sale());
				sumDebit = creditBookMO.getAmount_of_sale() + creditTransMO.getDebit();
			}
			if (sumDebit >= creditTransMO.getCredit()) {
				creditTransMO.setDebit(sumDebit);
				System.out.println("inside update debit transaction " + creditTransMO.getId());
				updateCreditTransaction(creditTransMO);
			} else {
				createCreditTransaction(creditTransMO);
			}
		}
	}

	@Override
	public List<CreditBookMO> getAllCreditBook() throws ResourceNotFoundException {
		List<CreditBookMO> creditBookMOs = new ArrayList<CreditBookMO>();
		creditBookRepository.findAll().forEach(creditBook -> {
			CreditBookMO creditBookMO = mapDOtoMO(creditBook, new CreditBookMO());
			creditBookMOs.add(creditBookMO);
		});

		Collections.sort(creditBookMOs, new Comparator<CreditBookMO>() {
			DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy h:mm:ss");

			@Override
			public int compare(CreditBookMO o1, CreditBookMO o2) {
				try {
					return -1 * formatter.parse(o1.getCreated_dttm()).compareTo(formatter.parse(o2.getCreated_dttm()));
				} catch (ParseException e) {
					throw new IllegalArgumentException(e);
				}
			}
		});
		creditBookMOs.forEach(obj -> System.out.println("date of sale ::::" + obj.getCreated_dttm()));
		// TODO Auto-generated method stub
		return creditBookMOs;
	}

	@Override
	public List<CreditBookMO> getCreditEntriesByVhOwnerId(int vhOwnerId) throws Exception {
		List<CreditBookMO> creditBookMOs = new ArrayList<CreditBookMO>();
		List<Integer> vehicleIds = vehicleServiceImpl.getAllVehicleByVhOwner(vhOwnerId);
		creditBookRepository.findByVehicleIdIn(vehicleIds).forEach(creditBook -> {
			CreditBookMO creditBookMO = mapDOtoMO(creditBook, new CreditBookMO());
			creditBookMOs.add(creditBookMO);
		});
		return creditBookMOs;
	}

	@Override
	public CreditBookMO getCreditBookForId(Integer Id) throws ResourceNotFoundException {
		CreditBook creditBook = creditBookRepository.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException("No credit book found for this id :: " + Id));
		return mapDOtoMO(creditBook, new CreditBookMO());
	}

	@Override
	public CreditBookMO updateCreditBook(CreditBookMO creditBookMO) throws Exception {
		CreditBook creditBook = creditBookRepository.findById(creditBookMO.getId()).orElseThrow(
				() -> new ResourceNotFoundException("No credit book found for this id ::" + creditBookMO.getId()));
		updateDebitTransaction(creditBookMO, creditBook);
		creditBookRepository.save(mapMOtoDO(creditBookMO, creditBook));
		return creditBookMO;
	}

	@Override
	public void updateCreditBookList(List<CreditBookMO> creditBookMOLst) throws Exception {
		// List<CreditBook> creditBookLst = new ArrayList<CreditBook>();
		List<Integer> ids = creditBookMOLst.stream().map(CreditBookMO::getId).collect(Collectors.toList());
		List<CreditBook> creditBookLst = creditBookRepository.findAllById(ids);
		List<CreditBook> updatedCreditBookLst = new ArrayList<>();
		creditBookLst.forEach(creditBook -> {
			try {
				CreditBookMO creditBookMO = creditBookMOLst.stream().filter(obj -> creditBook.getId() == obj.getId())
						.findAny().orElse(null);

				updatedCreditBookLst.add(mapMOtoDO(creditBookMO, creditBook));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		creditBookRepository.saveAll(creditBookLst);
	}

	private CreditBookMO mapDOtoMO(CreditBook creditBook, CreditBookMO creditBookMO) {
		creditBookMO.setId(creditBook.getId());
		creditBookMO.setLitre_sale_volume(creditBook.getLitre_sale_volume());
		creditBookMO.setAmount_of_sale(creditBook.getAmount_of_sale());
		if (null != creditBook.getCredit_transaction_id())
			creditBookMO.setCredit_transaction_id(creditBook.getCredit_transaction_id());
		FuelPriceMO fuelPriceMO = new FuelPriceMO();
		try {
			creditBookMO.setVehicleMO(vehicleServiceImpl.getVehicleById(creditBook.getVehicleId()));
		} catch (ResourceNotFoundException exp) {
			new ResourceNotFoundException("no vehilce found for id =" + creditBook.getVehicleId());
		}
		try {
			fuelPriceMO.setFuel_type(fuelPriceServiceImpl.getFuelTypeById(creditBook.getFuel_type_id()));
		} catch (ResourceNotFoundException e) {
			new ResourceNotFoundException("No fuel type found for id =" + creditBook.getFuel_type_id());
		}
		fuelPriceMO.setPrice(creditBook.getFuelPrice());
		creditBookMO.setFuelPriceMO(fuelPriceMO);
		try {
			creditBookMO.setDttm_of_sale(DateAndTimeUtility.changeDateFormat(creditBook.getDttm_of_sale().toString(),
					DATEFORMAT.DB_DATE_TIME_FORMAT, DATEFORMAT.CLIENT_DATE_TIME_FORMAT));
			creditBookMO.setIs_paid(creditBook.getIs_paid());
			if (creditBook.getCreated_dttm() != null)
				creditBookMO
						.setCreated_dttm(DateAndTimeUtility.changeDateFormat(creditBook.getCreated_dttm().toString(),
								DATEFORMAT.DB_DATE_TIME_FORMAT, DATEFORMAT.CLIENT_DATE_TIME_FORMAT));
			if (creditBook.getModified_dttm() != null)
				creditBookMO
						.setModified_dttm(DateAndTimeUtility.changeDateFormat(creditBook.getModified_dttm().toString(),
								DATEFORMAT.DB_DATE_TIME_FORMAT, DATEFORMAT.CLIENT_DATE_TIME_FORMAT));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return creditBookMO;
	}

	private CreditBook mapMOtoDO(CreditBookMO creditBookMO, CreditBook creditBook) throws Exception {
		creditBook.setId(creditBookMO.getId());
		creditBook.setVehicleId(creditBookMO.getVehicleMO().getId());
		creditBook.setFuel_type_id(creditBookMO.getFuelPriceMO().getFuel_type().getId());
		creditBook.setLitre_sale_volume(creditBookMO.getLitre_sale_volume());
		creditBook.setAmount_of_sale(creditBookMO.getAmount_of_sale());
		creditBook.setFuelPrice(creditBookMO.getFuelPriceMO().getPrice());
		CreditTransactionMO creditTransactionMO = getCreditTransByVhOwnerId(
				creditBookMO.getVehicleMO().getVhOwner().getId());
		System.out.println("credit transaction id :::::::::::: " + creditTransactionMO.getId());
		creditBook.setCredit_transaction_id(creditTransactionMO.getId());
		try {
			creditBook.setDttm_of_sale(DateAndTimeUtility.convertStringToTimestamp(creditBookMO.getDttm_of_sale(),
					DATEFORMAT.CLIENT_DATE_TIME_FORMAT));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		creditBook.setIs_paid(creditBookMO.getIs_paid());
		creditBook.setComments(creditBookMO.getComments());
		return creditBook;
	}

	@Override
	public CreditTransactionMO createCreditTransaction(final CreditTransactionMO creditTransactionMO) throws Exception {
		try {
			CreditTransaction creditTransaction = creditTransactionRepo
					.findCreditTransdByVhOwnerAndCredit(creditTransactionMO.getVhOwner().getId(), 0);
			if (null != creditTransaction) {
				if (creditTransaction.getCredit() != 0
						&& creditTransactionMO.getDebit() >= creditTransaction.getCredit()) {
					double differenceAmt = creditTransactionMO.getDebit() - creditTransaction.getCredit();
					creditTransaction.setDebit(creditTransaction.getCredit());
					creditTransaction = creditTransactionRepo.save(mapMOtoDO(creditTransactionMO, creditTransaction));
					CreditTransaction newCreditTrans = new CreditTransaction();
					newCreditTrans.setVhOwnerId(creditTransactionMO.getVhOwner().getId());
					newCreditTrans.setDebit(differenceAmt);
					creditTransaction = creditTransactionRepo.save(newCreditTrans);
				} else {
					creditTransactionMO.setId(creditTransaction.getId());
					creditTransaction = creditTransactionRepo.save(mapMOtoDO(creditTransactionMO, creditTransaction));
				}
			} else {
				creditTransaction = creditTransactionRepo.save(mapMOtoDO(creditTransactionMO, new CreditTransaction()));
			}

			List<CreditBookMO> creditBookMOLst = creditTransactionMO.getcreditBookMOs();
			final int creditTransactionId = creditTransaction.getId();
			if (null != creditBookMOLst) {
				creditBookMOLst.forEach(creditBooKMO -> {
					creditBooKMO.setCredit_transaction_id(creditTransactionId);
				});
				updateCreditBookList(creditBookMOLst);
			}
		} catch (DataIntegrityViolationException pExp) {
			throw new Exception(pExp.getRootCause().getLocalizedMessage());
		} catch (Exception exp) {
			logger.error("Error while creating new credit transaction", exp);
			throw new Exception("Error while creating new credit transaction", exp);
		}
		return creditTransactionMO;
	}

	@Override
	public CreditTransactionMO updateCreditTransaction(CreditTransactionMO creditTransactionMO) throws Exception {
		CreditTransaction creditTransaction = creditTransactionRepo.findById(creditTransactionMO.getId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"No credit book found for this id ::" + creditTransactionMO.getId()));
		if (creditTransaction.getCredit() != 0 && creditTransactionMO.getDebit() >= creditTransaction.getCredit()) {
			//if debit is greater than credit, than update the difference in current transaction
			// and create new transaction for remaining amount
			double debitDiffAmt = creditTransaction.getCredit() - creditTransaction.getDebit();
			if(debitDiffAmt > 0) {
				creditTransaction.setDebit(creditTransaction.getDebit() + debitDiffAmt);
				creditTransaction = creditTransactionRepo.save(mapMOtoDO(creditTransactionMO, creditTransaction));
			}
			CreditTransaction newCreditTrans = new CreditTransaction();
			newCreditTrans.setVhOwnerId(creditTransactionMO.getVhOwner().getId());
			double differenceAmt = creditTransactionMO.getDebit() - creditTransaction.getCredit();
			newCreditTrans.setDebit(differenceAmt);
			creditTransaction = creditTransactionRepo.save(newCreditTrans);
		} else {
			creditTransaction = creditTransactionRepo.save(mapMOtoDO(creditTransactionMO, creditTransaction));
		}
		//creditTransactionRepo.save(mapMOtoDO(creditTransactionMO, creditTransaction));
		return creditTransactionMO;
	}

	@Override
	public List<CreditTransactionMO> getAllCreditTransaction() throws Exception {
		List<CreditTransactionMO> creditTransactionMOLst = new ArrayList<CreditTransactionMO>();
		creditTransactionRepo.findAll().forEach(creditTransaction -> {
			try {
				creditTransactionMOLst.add(mapDOtoMO(creditTransaction, new CreditTransactionMO()));
			} catch (ResourceNotFoundException resNotFoundExp) {
				logger.error("error while getting all credit transactions", resNotFoundExp);
				resNotFoundExp.printStackTrace();
			}
		});
		
		Collections.sort(creditTransactionMOLst, new Comparator<CreditTransactionMO>() {
			DateFormat f = new SimpleDateFormat("dd.MM.yyyy h:mm:ss");

			@Override
			public int compare(CreditTransactionMO o1, CreditTransactionMO o2) {
				try {
					return -1 * f.parse(o1.getCreated_dttm()).compareTo(f.parse(o2.getCreated_dttm()));
				} catch (ParseException e) {
					throw new IllegalArgumentException(e);
				}
			}
		});
		//creditTransactionMOLst.forEach(obj -> System.out.println("date of sale ::::" + obj.getCreated_dttm()));

		return creditTransactionMOLst;
	}

	private CreditTransaction mapMOtoDO(CreditTransactionMO creditTransactionMO, CreditTransaction creditTransaction) {
		creditTransaction.setId(creditTransactionMO.getId());
		creditTransaction.setCredit(creditTransactionMO.getCredit());
		try {
			if (null != creditTransactionMO.getCredited_dttm())
				creditTransaction.setCredited_dttm(DateAndTimeUtility.convertStringToTimestamp(
						creditTransactionMO.getCredited_dttm(), DATEFORMAT.CLIENT_DATE_TIME_FORMAT));
		} catch (ParseException parseExp) {
			logger.error("Error while parsing creditted datetime", parseExp);
			parseExp.printStackTrace();
		}
		creditTransaction.setVhOwnerId(creditTransactionMO.getVhOwner().getId());
		if (creditTransactionMO.getDebit() > 0) {
			creditTransaction.setDebit(creditTransactionMO.getDebit());
		}

		creditTransaction.setIs_deleted(creditTransactionMO.getIs_deleted());
		creditTransaction.setDescription(creditTransactionMO.getDescription());
		return creditTransaction;
	}

	private CreditTransactionMO mapDOtoMO(CreditTransaction creditTransaction, CreditTransactionMO creditTransactionMO)
			throws ResourceNotFoundException {
		creditTransactionMO.setId(creditTransaction.getId());
		creditTransactionMO.setCredit(creditTransaction.getCredit());
		try {
			if (creditTransaction.getCredited_dttm() != null)
				creditTransactionMO.setCredited_dttm(
						DateAndTimeUtility.changeDateFormat(creditTransaction.getCredited_dttm().toString(),
								DATEFORMAT.DB_DATE_TIME_FORMAT, DATEFORMAT.CLIENT_DATE_TIME_FORMAT));
			if (creditTransaction.getCreatedDttm() != null)
				creditTransactionMO.setCreated_dttm(
						DateAndTimeUtility.changeDateFormat(creditTransaction.getCreatedDttm().toString(),
								DATEFORMAT.DB_DATE_TIME_FORMAT, DATEFORMAT.CLIENT_DATE_TIME_FORMAT));
			if (creditTransaction.getModified_dttm() != null)
				creditTransactionMO.setModified_dttm(
						DateAndTimeUtility.changeDateFormat(creditTransaction.getModified_dttm().toString(),
								DATEFORMAT.DB_DATE_TIME_FORMAT, DATEFORMAT.CLIENT_DATE_TIME_FORMAT));
		} catch (ParseException parseExp) {
			logger.error("Error while parsing creditted datetime", parseExp);
			parseExp.printStackTrace();
		}

		creditTransactionMO.setVhOwner(vehicleOwnerServiceImpl.getVehicleOwnerById(creditTransaction.getVhOwnerId()));
		creditTransactionMO.setDebit(creditTransaction.getDebit());
		creditTransactionMO.setIs_deleted(creditTransaction.getIs_deleted());
		creditTransactionMO.setDescription(creditTransaction.getDescription());
		return creditTransactionMO;
	}

	@Override
	public CreditTransactionMO getCreditTransByVhOwnerId(int vhOwnerId) throws Exception {
		CreditTransactionMO creditTransactionMO = null;
		try {
			CreditTransaction creditTransaction = creditTransactionRepo
					.findTopByVhOwnerIdOrderByCreatedDttmDesc(vhOwnerId);
			if (null != creditTransaction)
				creditTransactionMO = mapDOtoMO(creditTransaction, new CreditTransactionMO());
		} catch (Exception exp) {
			System.out.println("error while getting credit transaction by owner id " + vhOwnerId);
		}

		return creditTransactionMO;
	}

}
