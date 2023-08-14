package com.kajol.ResidConfig;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kajol.dao.MyJPARepo;
import com.kajol.dao.UserAuth;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private MyJPARepo jpa;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserAuth user1;
		if(username==null)
			 throw new UsernameNotFoundException("user not found");
		else
			user1=jpa.findByName(username);
		
		return new User(user1.getName(),user1.getName(),Collections.emptyList());
	}
	

}
