package org.waterproofingdata.wpdauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.waterproofingdata.wpdauth.model.ForgotPasswordQuestionsUsersAnswers;

public interface ForgotPasswordQuestionsUsersAnswersRepository extends JpaRepository<ForgotPasswordQuestionsUsersAnswers, Integer> {
	@Query(value = "SELECT fqua.* FROM forgotpassword_questions_users_answers fqua WHERE fqua.forgotpassword_questions_id = ?1 AND fqua.users_id = ?2", nativeQuery = true)
	ForgotPasswordQuestionsUsersAnswers findByForgotPasswordQuestionsAndUserid(Integer forgotpasswordquestionsid, Integer usersid);
}
