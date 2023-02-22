package com.vaibhav.ws.peer.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaibhav.ws.peer.BankRepository;
import com.vaibhav.ws.peer.UserRepository;
import com.vaibhav.ws.peer.io.entity.BankEntity;
import com.vaibhav.ws.peer.io.entity.UserEntity;
import com.vaibhav.ws.peer.service.UserService;
import com.vaibhav.ws.peer.shared.AesCryptUtil;
import com.vaibhav.ws.peer.shared.Utils;
import com.vaibhav.ws.peer.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BankRepository bankRepository;
	
	@Autowired 
	Utils utils;
	
	@Autowired 
	AesCryptUtil aesCryptUtil;
	
	final static public String KEY = "1D2A907A05656A7E1A570B14C573D192";
	
	String BankName="";
	String BankBranch="";
	String BankCode="";

	@Override
	public UserDto createUser(UserDto user) {
		
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		
		/*System.out.println(user.getAes_128bit_enckey());
		System.out.println(user.getAmount());
		System.out.println(user.getCurrency());
		System.out.println(user.getEncrypted_str());
		System.out.println(user.getFirstname());
		System.out.println(user.getId());
		System.out.println(user.getLastname());
		System.out.println(user.getOptional());
		System.out.println(user.getPeerBankBranch());
		System.out.println(user.getPeerBankCode());
		System.out.println(user.getPeerBankName());
		System.out.println(user.getPhone());
		System.out.println(user.getPid());
		System.out.println(user.getUVR());
		
		userEntity.setAes_128bit_enckey("test");
		userEntity.setAmount("10.00");
		userEntity.setCurrency("INR");
		userEntity.setEncrypted_str("test");
		userEntity.setId(1);
		userEntity.setOptional("optional");
		userEntity.setPeerBankBranch("test");
		userEntity.setPeerBankCode("test");
		userEntity.setPeerBankName("test");
		userEntity.setPhone("test");
		userEntity.setPid("test");
		userEntity.setUVR("test");*/
		
		String publicUserId = utils.generateUserId(10);
		userEntity.setPid(publicUserId);
		
		
		AesCryptUtil aesCryptUtil = new AesCryptUtil(KEY);
		String AESData = aesCryptUtil.encrypt(userEntity.getFirstname()+"&"+userEntity.getLastname()+"&"+userEntity.getAmount()+"&"+userEntity.getCurrency()+"&"+userEntity.getEmail());
		
		System.out.println(aesCryptUtil.encrypt(userEntity.getFirstname())); // test working
		System.out.println(AESData); // test working
		
		userEntity.setAes_128bit_enckey(KEY);
		userEntity.setEncrypted_str(AESData);
		userEntity.setUVR(AESData);
		
		List<BankEntity> bankList = bankRepository.findAll();
		
		for(int i = 0; i < bankList.size(); i++){
		if(bankList.get(i).getPeerBankCode().equals(userEntity.getPeerBankCode())) {
			System.out.println(bankList.get(i).getPeerBankCode());
			
			BankName=bankList.get(i).getPeerBankName().toString();
			BankBranch=bankList.get(i).getPeerBankBranch().toString();
			BankCode=bankList.get(i).getPeerBankCode().toString();

			
			System.out.println(BankName);
			System.out.println(BankBranch);
			System.out.println(BankCode);
			
			break;
			
			}	
		}
		
		System.out.println(BankName);
		System.out.println(BankBranch);
		System.out.println(BankCode);
		
		userEntity.setOptional("optional");
		userEntity.setPeerBankBranch(BankBranch);
		userEntity.setPeerBankName(BankName);
		
	
		//bankList.forEach(System.out::println); //printArrays
		
		UserEntity storedUserDetails = userRepository.save(userEntity);
		
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(storedUserDetails, returnValue);
		
		
		//printValues
		System.out.println(returnValue.getAes_128bit_enckey());
		System.out.println(returnValue.getAmount());
		System.out.println(returnValue.getCurrency());
		System.out.println(returnValue.getEncrypted_str());
		System.out.println(returnValue.getFirstname());
		System.out.println(returnValue.getId());
		System.out.println(returnValue.getLastname());
		System.out.println(returnValue.getOptional());
		System.out.println(returnValue.getPeerBankBranch());
		System.out.println(returnValue.getPeerBankCode());
		System.out.println(returnValue.getPeerBankName());
		System.out.println(returnValue.getPhone());
		System.out.println(returnValue.getPid());
		System.out.println(returnValue.getUVR());
		System.out.println(returnValue.getEmail());
		
		return returnValue;
	}

}
