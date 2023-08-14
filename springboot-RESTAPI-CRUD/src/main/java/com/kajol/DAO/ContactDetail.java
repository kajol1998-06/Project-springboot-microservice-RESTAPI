package com.kajol.DAO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class ContactDetail {
	 @Id
	 private Integer id;
     private String phoneNo;
     private String email;
     private String city;
     
//     @OneToOne(mappedBy = "contact")
//     private UserValue user;
}
