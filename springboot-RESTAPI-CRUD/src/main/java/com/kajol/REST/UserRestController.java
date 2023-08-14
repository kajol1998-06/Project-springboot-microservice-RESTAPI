package com.kajol.REST;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kajol.DAO.MyJPARepository;
import com.kajol.DAO.UserValue;

import antlr.collections.List;

@RestController
public class UserRestController {

	@Autowired
	private MyJPARepository repo;
	
	@GetMapping("/hey")
	public String welcome()
	{
		return "weicome to my curd operation";
	}
	@PostMapping("/saveUser")
	public ResponseEntity<UserValue> saveUser(@RequestBody UserValue user)
	{
		  if(user==null)
			  return new ResponseEntity<UserValue>(new UserValue(),HttpStatus.BAD_REQUEST);
	      
		  repo.save(user);
	      return new ResponseEntity<UserValue>(user,HttpStatus.CREATED);
	}
	
	@GetMapping("/getUser")
	public ResponseEntity<UserValue> getUser(@RequestParam("id") Integer id)
	{
		Optional<UserValue> ops=repo.findById(id);
		if(!ops.isPresent())
			return new ResponseEntity<UserValue>(new UserValue(),HttpStatus.BAD_REQUEST);
		UserValue user=ops.get();
		System.out.print(user);
		return new ResponseEntity<UserValue>(user,HttpStatus.ACCEPTED);
    }
	
	@DeleteMapping("/deleteUser")
	public ResponseEntity<String> deleteUser(@RequestParam ("id") Integer id)
	{
		Optional<UserValue> ops=repo.findById(id);
		if(!ops.isPresent())
			return new ResponseEntity<String>("Id not present",HttpStatus.BAD_REQUEST);
		repo.deleteById(id);
		return new ResponseEntity<String>("succesfull deleted",HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/updateUser")
	public ResponseEntity<UserValue> updateUser(@RequestBody UserValue user)
	{
		Optional<UserValue> ops=repo.findById(user.getId());
		if(!ops.isPresent())
			return new ResponseEntity<UserValue>(new UserValue(),HttpStatus.BAD_REQUEST);
		repo.updateUserDeatils(user.getName(),user.getContctNo(), user.getService(),user.getPrice(), user.getMnt(), user.getBill(), user.getId());
		return new ResponseEntity<UserValue>(user,HttpStatus.ACCEPTED);
		
	}
	@GetMapping("/getAllUser")
	public ResponseEntity<ArrayList<UserValue>> getAllUser()
	{
		ArrayList<UserValue> arr=new ArrayList<>();
		arr=(ArrayList<UserValue>)(repo.findAll());
		return new ResponseEntity<ArrayList<UserValue>>(arr,HttpStatus.ACCEPTED);
	}
	
}
