package org.waterproofingdata.wpdauth.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.waterproofingdata.wpdauth.model.Users;


public interface UsersRepository extends JpaRepository<Users, Integer> {
	  boolean existsByUsername(String username);

	  Users findByUsername(String username);

	  @Transactional
	  @Query(value = "UPDATE users SET active = ?2 WHERE username = ?1", nativeQuery = true)
	  void activateByUsername(String username, Integer active);
	  
	  @Transactional
	  @Query(value = "INSERT INTO users_rolesprovider_activationkey(users_id, roles_id, activationkey) VALUES VALUES (?1, ?2, ?3);", nativeQuery = true)
	  void insertUsersRolesProvider(Integer idUser, Integer idRoles, String activationkey);

	  @Transactional
	  @Query(value = "INSERT INTO users_educemaden_organizations(users_id, educemaden_organizations_id, activationkey) VALUES (?1, ?2, ?3);", nativeQuery = true)
	  void insertUsersEduCemadenOrganizations(Integer idUser, Integer idEduCemadenOrganizations, String activationkey);
}
