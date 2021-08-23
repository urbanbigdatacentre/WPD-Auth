package org.waterproofingdata.wpdauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.waterproofingdata.wpdauth.service.ForgotPasswordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/forgotpassword")
@Api(tags = "forgotpassword")
public class ForgotPasswordController {
	@Autowired
	private ForgotPasswordService forgotPasswordService;
	
	  @PostMapping("/sendkey")
	  @ApiOperation(value = "${ForgotPasswordController.sendkey}")
	  @ApiResponses(value = {//
	      @ApiResponse(code = 400, message = "Something went wrong"), //
	      @ApiResponse(code = 422, message = "Invalid email supplied")})
	  public void sendkey(@ApiParam("Email") @RequestParam String email) {
		  forgotPasswordService.sendkey(email);
	  }
	  
	  @PostMapping("/login")
	  @ApiOperation(value = "${UserController.login}")
	  @ApiResponses(value = {//
	      @ApiResponse(code = 400, message = "Something went wrong"), //
	      @ApiResponse(code = 422, message = "Invalid username/password supplied")})
	  public String login(//
	      @ApiParam("Email") @RequestParam String email, //
	      @ApiParam("Key") @RequestParam String key) {
	    return forgotPasswordService.loginByEmailAndKey(email, key);
	  }
}
