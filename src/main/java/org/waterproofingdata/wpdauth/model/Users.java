package org.waterproofingdata.wpdauth.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.validation.constraints.Size;

import org.waterproofingdata.wpdauth.model.Roles;

import javax.persistence.ElementCollection;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Users {
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Integer id;

	  @Size(min = 4, max = 255, message = "Minimum name length: 4 characters")
	  @Column(unique = true, nullable = false)
	  private String username;

	  @Size(min = 8, message = "Minimum password length: 8 characters")
	  @Column(nullable = false)
	  private String password;
	  
	  @Column(unique = true, nullable = false)
	  private String email;	  
	  
	  @Size(min = 4, max = 255, message = "Minimum firstname length: 4 characters")
	  @Column(nullable = true)
	  private String firstname;

	  @Size(min = 4, max = 255, message = "Minimum surname length: 4 characters")
	  @Column(nullable = true)
	  private String surname;
	  
	  @Column(nullable = true)
	  private String avatar;

	  @Column(nullable = false)
	  private Integer active;
	  
	  @ElementCollection(fetch = FetchType.EAGER)
	  List<Roles> roles;
}
