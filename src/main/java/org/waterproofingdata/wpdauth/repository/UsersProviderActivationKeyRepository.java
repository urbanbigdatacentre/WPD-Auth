package org.waterproofingdata.wpdauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.waterproofingdata.wpdauth.model.UsersProviderActivationKey;

public interface UsersProviderActivationKeyRepository extends JpaRepository<UsersProviderActivationKey, Integer> {
	UsersProviderActivationKey findByActivationkey(String activationkey);
	
	UsersProviderActivationKey findByUsersid(Integer usersid);
}
