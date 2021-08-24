package org.waterproofingdata.wpdauth.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.waterproofingdata.wpdauth.dto.ForgotPasswordQuestionsResponseDTO;
import org.waterproofingdata.wpdauth.dto.ForgotPasswordQuestionsUsersAnswersRequestDTO;
import org.waterproofingdata.wpdauth.model.ForgotPasswordQuestionsUsersAnswers;
import org.waterproofingdata.wpdauth.dto.CustomMapper;
import org.waterproofingdata.wpdauth.service.ForgotPasswordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

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
	  
	  @PostMapping("/passwordupdatebyemail")
	  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_INSTITUTION') or hasRole('ROLE_CLIENT')")
	  @ApiOperation(value = "${ForgotPasswordController.passwordupdatebyemail}", authorizations = { @Authorization(value="apiKey") })
	  @ApiResponses(value = {//
	      @ApiResponse(code = 400, message = "Something went wrong"), //
	      @ApiResponse(code = 403, message = "Access denied"), //
	      @ApiResponse(code = 404, message = "The user doesn't exist"), //
	      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	  public void passwordupdatebyemail(@ApiParam("Email") @RequestParam String email, @ApiParam("NewPassword") @RequestParam String newPassword) {
	    forgotPasswordService.passwordUpdateByEmail(email, newPassword);
	  }	
	  
	  @PostMapping("/findallforgotpasswordquestions")
	  @ApiOperation(value = "${ForgotPasswordController.findallforgotpasswordquestions}")
	  @ApiResponses(value = {//
	      @ApiResponse(code = 400, message = "Something went wrong")})
	  public List<ForgotPasswordQuestionsResponseDTO> findallforgotpasswordquestions() {
		  return CustomMapper.mapAll(forgotPasswordService.findAllForgotPasswordQuestions(), ForgotPasswordQuestionsResponseDTO.class);
	  }	 
	  
	  @PostMapping("/saveforgotpasswordquestionsusersanswers")
	  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_INSTITUTION') or hasRole('ROLE_CLIENT')")
	  @ApiOperation(value = "${ForgotPasswordController.saveforgotpasswordquestionsusersanswers}", authorizations = { @Authorization(value="apiKey") })
	  @ApiResponses(value = {//
	      @ApiResponse(code = 400, message = "Something went wrong"), //
	      @ApiResponse(code = 403, message = "Access denied"), //
	      @ApiResponse(code = 404, message = "The user or forgot password questions don't exist"), //
	      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	  public void saveforgotpasswordquestionsusersanswers(@ApiParam("Forgot Password Questions Users Answers") @RequestBody ForgotPasswordQuestionsUsersAnswersRequestDTO answer) {
		forgotPasswordService.saveForgotPasswordQuestionsUsersAnswers(CustomMapper.map(answer, ForgotPasswordQuestionsUsersAnswers.class));
	  }	 
	  
	  @PostMapping("/loginbyemailandanswers")
	  @ApiOperation(value = "${ForgotPasswordController.loginbyemailandanswers}")
	  @ApiResponses(value = {//
	      @ApiResponse(code = 400, message = "Something went wrong"), //
	      @ApiResponse(code = 404, message = "The user or answer don't exist"), //
	      @ApiResponse(code = 422, message = "Invalid answers supplied to login. Must have at least 2 correct ones.")})
	  public String loginbyemailandanswers(//
	      @ApiParam("Email") @RequestParam String email, //
	      @ApiParam("Answers") @RequestParam List<ForgotPasswordQuestionsUsersAnswersRequestDTO> answers) {
	    return forgotPasswordService.loginByEmailAndAnswers(email, CustomMapper.mapAll(answers, ForgotPasswordQuestionsUsersAnswers.class));
	  }	  
}
