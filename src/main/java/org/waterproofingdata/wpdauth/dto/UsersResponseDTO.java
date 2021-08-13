package org.waterproofingdata.wpdauth.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import org.waterproofingdata.wpdauth.model.Roles;

@Getter
@Setter
public class UsersResponseDTO {
	  @ApiModelProperty(position = 0)
	  private Integer id;
	  @ApiModelProperty(position = 1)
	  private String username;
	  @ApiModelProperty(position = 2)
	  List<Roles> roles;
}
