package org.waterproofingdata.wpdauth.repository;

import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.waterproofingdata.wpdauth.model.EduCemadenOrganizations;

public interface EduCemadenOrganizationsRepository extends JpaRepository<EduCemadenOrganizations, Integer> {
	EduCemadenOrganizations findByPhone(String phone);
	
	EduCemadenOrganizations findByEmail(String email);
	
	@Query(value = "SELECT e.*, ueo.activationkey FROM auth.educemaden_organizations e INNER JOIN auth.users_educemaden_organizations ueo ON e.id = ueo.educemaden_organizations_id WHERE ueo.users_id = ?1", nativeQuery = true)
	EduCemadenOrganizations findByUserId(Integer userid);
	
	@Transactional
	@Query(value = "INSERT INTO auth.users_educemaden_organizations(id, users_id, educemaden_organizations_id, activationkey) VALUES (DEFAULT, ?1, ?2, ?3)", nativeQuery = true)
	void insertUsersEduCemadenOrganizations(Integer userid, Integer eduCemadenOrganizationsid, String activationkey);
}
