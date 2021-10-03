package org.waterproofingdata.wpdauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.waterproofingdata.wpdauth.model.UsersEducemadenOrganizations;

public interface UsersEducemadenOrganizationsRepository extends JpaRepository<UsersEducemadenOrganizations, Integer> {
	
	UsersEducemadenOrganizations findByUsersid(Integer usersid);
	
	UsersEducemadenOrganizations findByActivationkey(String activationkey);
	
	@Query(value = "SELECT * FROM auth.users_educemaden_organizations WHERE users_id = ?1 AND active = 1", nativeQuery = true)
	UsersEducemadenOrganizations findByUserIdAndActivated(Integer userid);
}
