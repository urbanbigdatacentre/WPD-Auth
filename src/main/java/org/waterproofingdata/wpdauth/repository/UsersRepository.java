package org.waterproofingdata.wpdauth.repository;

import org.waterproofingdata.wpdauth.model.Users;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsersRepository extends JpaRepository<Users, Integer> {
	  boolean existsByUsername(String username);

	  Users findByUsername(String username);

	  //@Transactional
	  //void activateByUsername(String username, String activationkey);
	  //PostGreSQL -> SELECT uuid_generate_v4();

}
