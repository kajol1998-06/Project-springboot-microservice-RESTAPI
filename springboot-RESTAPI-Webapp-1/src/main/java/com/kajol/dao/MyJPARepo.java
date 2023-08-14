package com.kajol.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MyJPARepo extends JpaRepository<UserAuth, Integer> {

	UserAuth findByName(String name);
}
