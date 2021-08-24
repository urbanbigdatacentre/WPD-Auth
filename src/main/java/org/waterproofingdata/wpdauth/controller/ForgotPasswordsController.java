package org.waterproofingdata.wpdauth.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.waterproofingdata.wpdauth.dto.ForgotPasswordsQuestionsResponseDTO;
import org.waterproofingdata.wpdauth.dto.ForgotPasswordsQuestionsUsersAnswersRequestDTO;
import org.waterproofingdata.wpdauth.model.ForgotPasswordsQuestionsUsersAnswers;
import org.waterproofingdata.wpdauth.dto.CustomMapper;
import org.waterproofingdata.wpdauth.service.ForgotPasswordsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/forgotpasswords")
@Api(tags = "forgotpasswords")
public class ForgotPasswordsController {
	@Autowired
	private ForgotPasswordsService forgotPasswordsService;
	
	  @PostMapping("/sendkeybyemail")
	  @ApiOperation(value = "${ForgotPasswordsController.sendkeybyemail}")
	  @ApiResponses(value = {//
	      @ApiResponse(code = 400, message = "Something went wrong"), //
	      @ApiResponse(code = 422, message = "Invalid email supplied")})
	  public void sendkeybyemail(@ApiParam("Email") @RequestParam String email) {
		  forgotPasswordsService.sendKeyByEmail(email);
	  }
	  
	  @PostMapping("/loginbyemailandkey")
	  @ApiOperation(value = "${ForgotPasswordsController.loginbyemailandkey}")
	  @ApiResponses(value = {//
	      @ApiResponse(code = 400, message = "Something went wrong"), //
	      @ApiResponse(code = 422, message = "Invalid username/password supplied")})
	  public String loginbyemailandkey(//
	      @ApiParam("Email") @RequestParam String email, //
	      @ApiParam("Key") @RequestParam String key) {
	    return forgotPasswordsService.loginByEmailAndKey(email, key);
	  }
	  
	  @PostMapping("/passwordupdatebyemail")
	  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_INSTITUTION') or hasRole('ROLE_CLIENT')")
	  @ApiOperation(value = "${ForgotPasswordsController.passwordupdatebyemail}", authorizations = { @Authorization(value="apiKey") })
	  @ApiResponses(value = {//
	      @ApiResponse(code = 400, message = "Something went wrong"), //
	      @ApiResponse(code = 403, message = "Access denied"), //
	      @ApiResponse(code = 404, message = "The user doesn't exist"), //
	      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	  public void passwordupdatebyemail(@ApiParam("Email") @RequestParam String email, @ApiParam("NewPassword") @RequestParam String newPassword) {
	    forgotPasswordsService.passwordUpdateByEmail(email, newPassword);
	  }	
	  
	  @GetMapping("/findallforgotpasswordquestions")
	  @ApiOperation(value = "${ForgotPasswordsController.findallforgotpasswordquestions}")
	  @ApiResponses(value = {//
	      @ApiResponse(code = 400, message = "Something went wrong")})
	  public List<ForgotPasswordsQuestionsResponseDTO> findallforgotpasswordquestions() {
		  return CustomMapper.mapAll(forgotPasswordsService.findAllForgotPasswordQuestions(), ForgotPasswordsQuestionsResponseDTO.class);
	  }	 
	  
	  @PostMapping("/saveforgotpasswordquestionsusersanswers")
	  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_INSTITUTION') or hasRole('ROLE_CLIENT')")
	  @ApiOperation(value = "${ForgotPasswordsController.saveforgotpasswordquestionsusersanswers}", authorizations = { @Authorization(value="apiKey") })
	  @ApiResponses(value = {//
	      @ApiResponse(code = 400, message = "Something went wrong"), //
	      @ApiResponse(code = 403, message = "Access denied"), //
	      @ApiResponse(code = 404, message = "The user or forgot password questions don't exist"), //
	      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	  public void saveforgotpasswordquestionsusersanswers(@ApiParam("Forgot Password Questions Users Answers") @RequestBody ForgotPasswordsQuestionsUsersAnswersRequestDTO answer) {
		forgotPasswordsService.saveForgotPasswordQuestionsUsersAnswers(CustomMapper.map(answer, ForgotPasswordsQuestionsUsersAnswers.class));
	  }	 
	  
	  @PostMapping("/loginbyemailandanswers")
	  @ApiOperation(value = "${ForgotPasswordsController.loginbyemailandanswers}")
	  @ApiResponses(value = {//
	      @ApiResponse(code = 400, message = "Something went wrong"), //
	      @ApiResponse(code = 404, message = "The user or answer don't exist"), //
	      @ApiResponse(code = 422, message = "Invalid answers supplied to login. Must have at least 2 correct ones.")})
	  public String loginbyemailandanswers(//
	      @ApiParam("Email") @RequestParam String email, //
	      @ApiParam("Answers") @RequestParam List<ForgotPasswordsQuestionsUsersAnswersRequestDTO> answers) {
	    return forgotPasswordsService.loginByEmailAndAnswers(email, CustomMapper.mapAll(answers, ForgotPasswordsQuestionsUsersAnswers.class));
	  }	  
}
