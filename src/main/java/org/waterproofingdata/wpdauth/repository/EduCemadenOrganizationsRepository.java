package org.waterproofingdata.wpdauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.waterproofingdata.wpdauth.model.EduCemadenOrganizations;

public interface EduCemadenOrganizationsRepository extends JpaRepository<EduCemadenOrganizations, Integer> {
	EduCemadenOrganizations findByPhone(String phone);
	
	EduCemadenOrganizations findByEmail(String email);
}
