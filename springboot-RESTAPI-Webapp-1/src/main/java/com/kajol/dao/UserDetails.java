package com.kajol.dao;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDetails {
  private Integer id;
  @NotBlank(message="name is mandatory")
  @Size(min=3,max=15,message="name should contain minimum 3 and maximum 15")
  private String name;
  @NotBlank(message="contact is mandatory")
  @Size(min=10,max=10,message="contactno should contain 10 digit")
  private String contctNo;
  @NotBlank(message="service is mandatory")
  private String service;
  private String price;
  @NotBlank(message="month is mandatory")
  @Positive(message="only positive number")
  private Integer mnt;
  private Integer bill;
}
