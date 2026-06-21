package com.ccn.springai.config;

import com.ccn.springai.domain.GoogleOtp;
import com.ccn.springai.domain.User;
import com.ccn.springai.repository.UserRepository;
import com.ccn.springai.service.GoogleOtpService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final GoogleOtpService googleOtpService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            response.sendRedirect("/articles");
            return;
        }

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        Optional<GoogleOtp> otp = googleOtpService.findByUser(user);

        if (otp.isEmpty() || !otp.get().isOtpEnabled()) {
            response.sendRedirect("/otp/setup");
        } else {
            response.sendRedirect("/otp/verify");
        }
    }
}
