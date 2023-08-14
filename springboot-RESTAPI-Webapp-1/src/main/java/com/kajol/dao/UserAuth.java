package com.kajol.dao;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class UserAuth {
   @Id	
   private Integer id;
   private String name;
   private String password;
}
