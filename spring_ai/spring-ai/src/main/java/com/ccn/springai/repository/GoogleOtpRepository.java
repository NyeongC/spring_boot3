package com.ccn.springai.repository;

import com.ccn.springai.domain.GoogleOtp;
import com.ccn.springai.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoogleOtpRepository extends JpaRepository<GoogleOtp, Long> {
    Optional<GoogleOtp> findByUser(User user);
}
