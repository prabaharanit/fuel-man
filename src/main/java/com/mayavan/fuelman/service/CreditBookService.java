package com.mayavan.fuelman.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mayavan.fuelman.exception.ResourceNotFoundException;
import com.mayavan.fuelman.repo.model.CreditBookMO;

@Service
public interface CreditBookService {
	
	public CreditBookMO createCreditBook(CreditBookMO creditBookMO);
	
    public List<CreditBookMO> getAllCreditBook() throws ResourceNotFoundException;
    
    public CreditBookMO getCreditBookForId(Integer Id)throws ResourceNotFoundException;
    
    public CreditBookMO updateCreditBook(CreditBookMO creditBookMO)throws ResourceNotFoundException;
}
