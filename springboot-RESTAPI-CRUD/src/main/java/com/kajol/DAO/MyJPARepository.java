package com.kajol.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MyJPARepository extends JpaRepository<UserValue, Integer> {
	@Transactional
	@Modifying
	@Query(value="update User_Value u set u.name = ? , u.contct_No = ? , u.service = ? , u.price = ? , u.mnt = ? , u.bill = ?  where u.id = ?",nativeQuery = true)
	int  updateUserDeatils(String name,String contctNo, String service, String price, Integer mnt,Integer bill, Integer id);
}
