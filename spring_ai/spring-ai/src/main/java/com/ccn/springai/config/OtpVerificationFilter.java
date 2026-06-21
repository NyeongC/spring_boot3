package com.ccn.springai.config;

import com.ccn.springai.domain.GoogleOtp;
import com.ccn.springai.domain.User;
import com.ccn.springai.repository.UserRepository;
import com.ccn.springai.service.GoogleOtpService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class OtpVerificationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final GoogleOtpService googleOtpService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            filterChain.doFilter(request, response);
            return;
        }

        String path = request.getRequestURI();
        if (isOtpExemptPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        Boolean otpVerified = session != null ? (Boolean) session.getAttribute("OTP_VERIFIED") : null;

        if (Boolean.TRUE.equals(otpVerified)) {
            filterChain.doFilter(request, response);
            return;
        }

        User user = userRepository.findByEmail(auth.getName()).orElse(null);
        if (user == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<GoogleOtp> otp = googleOtpService.findByUser(user);

        if (otp.isEmpty() || !otp.get().isOtpEnabled()) {
            response.sendRedirect("/otp/setup");
        } else {
            response.sendRedirect("/otp/verify");
        }
    }

    private boolean isOtpExemptPath(String path) {
        return path.startsWith("/otp")
                || path.equals("/login")
                || path.equals("/logout")
                || path.equals("/signup")
                || path.equals("/user")
                || path.startsWith("/static")
                || path.startsWith("/h2-console")
                || path.startsWith("/uploads");
    }
}
