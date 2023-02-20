package com.vaibhav.ws.peer.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vaibhav.ws.peer.BankRepository;
import com.vaibhav.ws.peer.io.entity.BankEntity;
import com.vaibhav.ws.peer.service.UserService;
import com.vaibhav.ws.peer.shared.AesCryptUtil;
import com.vaibhav.ws.peer.shared.dto.UserDto;
import com.vaibhav.ws.peer.ui.model.request.UserDetailsRquestModel;
import com.vaibhav.ws.peer.ui.model.response.UserRest;

@RestController
@RequestMapping("/User")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	BankRepository bankRepository;
	
	
	final static public String KEY = "1D2A907A05656A7E1A570B14C573D192";
	
	AesCryptUtil aesCryptUtil;
	
	
	@GetMapping
	public String getUser() {
		
		return "<html><body>"
	            + "<h1>WELCOME</h1>"
	            + "</body></html>";
	}
	
	@PostMapping("/banks")
	@ResponseStatus(HttpStatus.CREATED)
	public BankEntity bankEntity(@RequestBody BankEntity bankEntity) {
		return bankRepository.save(bankEntity);
	}
	
	
	@PostMapping
	public UserRest postUser(@RequestBody UserDetailsRquestModel UserDetails) {
		
		
		/*AesCryptUtil aesCryptUtil = new AesCryptUtil(KEY);
		aesCryptUtil.encrypt("VAIBHAV");
		
		System.out.println(aesCryptUtil.encrypt("VAIBHAV"));*/ //testing code snippet
		
		
		
		UserRest retunValue = new UserRest();
		
		System.out.println(UserDetails.getFirstname());
		System.out.println(UserDetails.getLastname());
		System.out.println(UserDetails.getPeerBankCode());
		System.out.println(UserDetails.getPhone());
		
		
		/*retunValue.setFirstname(UserDetails.getFirstname());
		retunValue.setLastname(UserDetails.getLastname());
		retunValue.setPeerBankCode(UserDetails.getPeerBankCode());
		retunValue.setPhone(UserDetails.getPhone());
		
		retunValue.setAmount("1000.00");
		retunValue.setCurrency("INR");
		retunValue.setOptional("Optinal Field");
		retunValue.setPeerBankName("State Bank of India");
		retunValue.setPeerBankBranch("Goregaon West");
		
		retunValue.setPID("123456789");
		retunValue.setUVR("UVR123456789QWERYT");*/
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(UserDetails, userDto);
		
		UserDto createUser =userService.createUser(userDto);
		BeanUtils.copyProperties(createUser, retunValue);
		
		return retunValue;
	}
	
}
