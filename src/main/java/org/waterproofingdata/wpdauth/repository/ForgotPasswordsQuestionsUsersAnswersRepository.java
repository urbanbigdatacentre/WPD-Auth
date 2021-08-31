package org.waterproofingdata.wpdauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.waterproofingdata.wpdauth.model.ForgotPasswordsQuestionsUsersAnswers;

public interface ForgotPasswordsQuestionsUsersAnswersRepository extends JpaRepository<ForgotPasswordsQuestionsUsersAnswers, Integer> {
	@Query(value = "SELECT fqua.* FROM auth.forgotpassword_questions_users_answers fqua WHERE fqua.forgotpassword_questions_id = ?1 AND fqua.users_id = ?2", nativeQuery = true)
	ForgotPasswordsQuestionsUsersAnswers findByForgotPasswordQuestionsAndUserid(Integer forgotpasswordquestionsid, Integer usersid);
}
