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
	
	  @PostMapping("/sendkeybyemail")
	  @ApiOperation(value = "${ForgotPasswordController.sendkeybyemail}")
	  @ApiResponses(value = {//
	      @ApiResponse(code = 400, message = "Something went wrong"), //
	      @ApiResponse(code = 422, message = "Invalid email supplied")})
	  public void sendkeybyemail(@ApiParam("Email") @RequestParam String email) {
		  forgotPasswordService.sendKeyByEmail(email);
	  }
	  
	  @PostMapping("/loginbyemailandkey")
	  @ApiOperation(value = "${ForgotPasswordController.loginbyemailandkey}")
	  @ApiResponses(value = {//
	      @ApiResponse(code = 400, message = "Something went wrong"), //
	      @ApiResponse(code = 422, message = "Invalid username/password supplied")})
	  public String loginbyemailandkey(//
	      @ApiParam("Email") @RequestParam String email, //
	      @ApiParam("Key") @RequestParam String key) {
	    return forgotPasswordService.loginByEmailAndKey(email, key);
	  }
}
