package org.waterproofingdata.wpdauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.waterproofingdata.wpdauth.model.ForgotPasswordQuestions;

public interface ForgotPasswordQuestionsRepository extends JpaRepository<ForgotPasswordQuestions, Integer> {
}
