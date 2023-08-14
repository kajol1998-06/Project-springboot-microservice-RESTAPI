package com.kajol.rest;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;

import com.kajol.Reside.DataLoadClass;
import com.kajol.dao.ServicePrice;
import com.kajol.dao.UserDetails;


@Controller
public class MyRestController {
	
	 private final WebClient.Builder webClientBuilder;
	 
	 private  RedisTemplate<String, ServicePrice> redisTemplate=null;
	 HashOperations<String, String,String> ops;
	   Map<String,String> m;
	 
	public MyRestController(WebClient.Builder webClientBuilder,RedisTemplate<String, ServicePrice> redisTemplate) {
	        this.webClientBuilder = webClientBuilder;
	        this.redisTemplate=redisTemplate;
	        ops=redisTemplate.opsForHash();
	        m=ops.entries("price_key4");
	    }
	@GetMapping("/getData")
	public String getalluserdetail(Model model)
	{
		    List<UserDetails> users=getAllUser();
		    model.addAttribute("users", users);
		    System.out.print(users);
		    return "user-list";
		    
	}
	@GetMapping("/add")
	public String getwelcomepage(Model model)
	{
		UserDetails user=new UserDetails();
		model.addAttribute("user", user);
		Set<String> s=m.keySet();
		model.addAttribute("services",s);
		return "index";
	}
	
	@PostMapping("/add")
	public String addUser(@ModelAttribute UserDetails user,Model model)
	{
		webClientBuilder.build()
        .post()
        .uri("http://localhost:8080/saveUser")
        .bodyValue(user)
        .retrieve()
        .bodyToMono(UserDetails.class)
        .block();
		model.addAttribute("user", new UserDetails());
		return "index";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteuser(@PathVariable Integer id,Model model)
	{
		 webClientBuilder.build()
         .delete()
         .uri("http://localhost:8080/deleteUser?id=" + id)
         .retrieve()
         .bodyToMono(String.class)
         .block();
		   List<UserDetails> users=getAllUser();
		    model.addAttribute("users", users);
		    System.out.print(users);
		    return "user-list";
	}
	@GetMapping("/update/{id}")
	public String setValue(@PathVariable Integer id,Model model)
	{
		UserDetails user=webClientBuilder.build()
        .get()
        .uri("http://localhost:8080/getUser?id=" + id)
        .retrieve()
        .bodyToMono(UserDetails.class)
        .block();
		model.addAttribute("user", user);
		return "index";
	}
	
	@PostMapping("/update/{id}")
	public String UpdateUser(@PathVariable Integer id,Model model,@ModelAttribute UserDetails user)
	{
		String service=user.getService();
		String price=m.get(service);
		Integer bill=user.getMnt()*(Integer.valueOf(price));
		user.setBill(bill);
		user.setPrice(price);
		webClientBuilder.build()
        .put()
        .uri("http://localhost:8080/updateUser?id=" + id)
        .bodyValue(user)
        .retrieve()
        .bodyToMono(UserDetails.class)
        .block();
		 List<UserDetails> users=getAllUser();
		    model.addAttribute("users", users);
		    System.out.print(users);
		    return "user-list";
	}
	@PostMapping("/process")
	public String processForm(@ModelAttribute UserDetails user,Model model)
	{
		String service=user.getService();
		String price=m.get(service);
		UserDetails user1=new UserDetails();
		Integer bill=(Integer.valueOf(price))*user.getMnt();
		user1.setBill(bill);
		user1.setContctNo(user.getContctNo());
		user1.setMnt(user.getMnt());
		user1.setPrice(price);
		user1.setName(user.getName());
		user1.setService(service);
		model.addAttribute("user",user1);
		System.out.print(user.getMnt());
		return "Bill";
	}
	public List<UserDetails> getAllUser()
	{
		 String serverUrl="http://localhost:8080/getAllUser";
		    List<UserDetails> users=webClientBuilder.build()
											        .get()
											        .uri(serverUrl)
											        .retrieve()
											        .bodyToFlux(UserDetails.class)
											        .collectList()
											        .block();
		    return users;
	}
}
