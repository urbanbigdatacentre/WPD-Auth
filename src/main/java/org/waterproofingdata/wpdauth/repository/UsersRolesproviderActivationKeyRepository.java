package org.waterproofingdata.wpdauth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.waterproofingdata.wpdauth.model.UsersRolesproviderActivationKey;

public interface UsersRolesproviderActivationKeyRepository extends JpaRepository<UsersRolesproviderActivationKey, Integer> {
	List<UsersRolesproviderActivationKey> findByUsersid(Integer usersid);
}
