package org.waterproofingdata.wpdauth.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import org.waterproofingdata.wpdauth.model.EduCemadenOrganizations;
import org.waterproofingdata.wpdauth.model.Roles;
import org.waterproofingdata.wpdauth.model.UsersRolesproviderActivationKey;

@Getter
@Setter
public class UsersResponseDTO {
	  @ApiModelProperty(position = 0)
	  private Integer id;
	  
	  @ApiModelProperty(position = 1)
	  private String username;
	  
	  @ApiModelProperty(position = 2)
	  List<Roles> roles;
	  
	  @ApiModelProperty(position = 3)
	  EduCemadenOrganizations eduCemadenOrganization;
	  
	  @ApiModelProperty(position = 4)
	  List<UsersRolesproviderActivationKey> rolesProviderActivationKeys;
}
