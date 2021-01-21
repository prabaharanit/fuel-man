package com.mayavan.fuelman.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mayavan.fuelman.exception.ResourceNotFoundException;
import com.mayavan.fuelman.repo.model.CreditBookMO;
import com.mayavan.fuelman.repo.model.CreditTransactionMO;

@Service
public interface CreditBookService {
	
	public CreditBookMO createCreditBook(CreditBookMO creditBookMO)throws Exception;
	
	public List<CreditBookMO> getAllCreditBook() throws ResourceNotFoundException;
	
	public List<CreditBookMO> getCreditEntriesByVhOwnerId(int id)throws Exception;
    
    public CreditBookMO getCreditBookForId(Integer Id)throws ResourceNotFoundException;
    
    public CreditBookMO updateCreditBook(CreditBookMO creditBookMO)throws Exception;
    
    public void updateCreditBookList(List<CreditBookMO> creditBookMOLst)throws Exception;

    public CreditTransactionMO createCreditTransaction(CreditTransactionMO creditTransactionMO)throws Exception;

	public List<CreditTransactionMO> getAllCreditTransaction()throws Exception;
	
	public CreditTransactionMO getCreditTransByVhOwnerId(int vhOwnerId)throws Exception;

	public CreditTransactionMO updateCreditTransaction(CreditTransactionMO creditTransactionMO) throws Exception;

}
