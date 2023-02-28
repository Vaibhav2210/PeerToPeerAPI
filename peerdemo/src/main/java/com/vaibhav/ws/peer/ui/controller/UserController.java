package com.vaibhav.ws.peer.ui.controller;

import java.util.Date;
import java.util.List;

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
import com.vaibhav.ws.peer.BlockRepository;
import com.vaibhav.ws.peer.blockchain.Block;
import com.vaibhav.ws.peer.blockchain.Blockchain;
import com.vaibhav.ws.peer.blockchain.Constants;
import com.vaibhav.ws.peer.blockchain.Miner;
import com.vaibhav.ws.peer.blockchain.SHA256Helper;
import com.vaibhav.ws.peer.io.entity.BankEntity;
import com.vaibhav.ws.peer.io.entity.BlockEntity;
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
	
	@Autowired
	BlockRepository blockRepository;
	
	
	final static public String KEY = "1D2A907A05656A7E1A570B14C573D192";
	
	AesCryptUtil aesCryptUtil;
	
	List<BlockEntity> blockList;
	
	
	@GetMapping
	public String getUser() {
		
		return "<html><body>"
	            + "<h1>WELCOME</h1>"
	            + "</body></html>";
	}
	
	@PostMapping("/blocks")
	@ResponseStatus(HttpStatus.CREATED)
	public BlockEntity blockEntity() {
		
		BlockEntity blockEntity = new BlockEntity();
		return blockEntry(blockEntity);
	}
	
	private BlockEntity blockEntry(BlockEntity blockEntity) {
		
		// TODO Auto-generated method stub
		
		blockList = (List<BlockEntity>) blockRepository.findAll();
		
		blockEntity.setHash("VaibhavTest");
		blockEntity.setId(7);
		blockEntity.setNonce(1233);
		blockEntity.setPreviousHash("VaibhavTest");
		blockEntity.setTimestamp(new Date().getTime());
		blockEntity.setTransaction("VaibhavTest");
	
		return blockRepository.save(blockEntity);
		
	}

	@PostMapping("/banks")
	@ResponseStatus(HttpStatus.CREATED)
	public BankEntity bankEntity(@RequestBody BankEntity bankEntity) {
		return bankRepository.save(bankEntity);
	}
	
	
	@PostMapping("/new")
	public UserRest newpostUser(@RequestBody UserDetailsRquestModel newUserDetails) {
		
		UserRest retunValue = new UserRest();
		
		
		retunValue.setFirstname(newUserDetails.getFirstname());
		retunValue.setLastname(newUserDetails.getLastname());
		retunValue.setPeerBankCode(newUserDetails.getPeerBankCode());
		retunValue.setPhone(newUserDetails.getPhone());
		retunValue.setEmail(newUserDetails.getEmail());
		
		retunValue.setAmount("1000.00");
		retunValue.setCurrency("INR");
		retunValue.setOptional("Optinal Field");
		retunValue.setPeerBankName("State Bank of India");
		retunValue.setPeerBankBranch("Goregaon West");
		
		retunValue.setPid("123456789");
		retunValue.setUVR("UVR123456789QWERYT");
		
		
		Blockchain blockchain = new Blockchain();
		Miner miner = new Miner();
		
		Block block0 = new Block(0, "" , Constants.GENESIS_PREV_HASH);
		miner.mine(block0, blockchain);
		
		Block block1 = new Block(1, "transaction1" , blockchain.getBlockChain().get(blockchain.getSize()-1).getHash());
		miner.mine(block1, blockchain);
		
		Block block2 = new Block(2, "transaction2" , blockchain.getBlockChain().get(blockchain.getSize()-1).getHash());
		miner.mine(block2, blockchain);
		
		System.out.println("\n"+ "BLOCKCHAIN \n"+blockchain);
		System.out.println("Miner's Reward : "+miner.getReward());
		
		//miner.checkUserData();
		
		BlockEntity blockEntity = new BlockEntity();
		blockEntry(blockEntity);
		
		return retunValue;
		
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
