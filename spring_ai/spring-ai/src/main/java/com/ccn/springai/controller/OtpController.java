package com.ccn.springai.controller;

import com.ccn.springai.domain.GoogleOtp;
import com.ccn.springai.domain.User;
import com.ccn.springai.repository.UserRepository;
import com.ccn.springai.service.GoogleOtpService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/otp")
public class OtpController {

    private final GoogleOtpService googleOtpService;
    private final UserRepository userRepository;

    @GetMapping("/setup")
    public String setupPage(Authentication auth, Model model) {
        User user = getUser(auth);
        GoogleOtp otp = googleOtpService.getOrCreateOtp(user);
        String qrBase64 = googleOtpService.generateQrBase64(otp.getSecretKey(), user.getEmail());

        model.addAttribute("qrBase64", qrBase64);
        model.addAttribute("secretKey", otp.getSecretKey());
        return "otp-setup";
    }

    @PostMapping("/register")
    public String registerOtp(@RequestParam("code") String codeStr,
                              Authentication auth,
                              HttpSession session,
                              Model model) {
        User user = getUser(auth);

        int code;
        try {
            code = Integer.parseInt(codeStr.trim());
        } catch (NumberFormatException e) {
            model.addAttribute("error", "숫자 6자리를 입력해주세요.");
            return reloadSetup(user, model);
        }

        boolean success = googleOtpService.enableOtp(user, code);
        if (success) {
            session.setAttribute("OTP_VERIFIED", true);
            return "redirect:/articles";
        }

        model.addAttribute("error", "OTP 코드가 일치하지 않습니다. 다시 시도해주세요.");
        return reloadSetup(user, model);
    }

    @GetMapping("/verify")
    public String verifyPage() {
        return "otp-verify";
    }

    @PostMapping("/reset")
    public String resetOtp(Authentication auth) {
        User user = getUser(auth);
        googleOtpService.resetOtp(user);
        return "redirect:/otp/setup";
    }

    @PostMapping("/validate")
    public String validateOtp(@RequestParam("code") String codeStr,
                              Authentication auth,
                              HttpSession session,
                              Model model) {
        User user = getUser(auth);

        int code;
        try {
            code = Integer.parseInt(codeStr.trim());
        } catch (NumberFormatException e) {
            model.addAttribute("error", "숫자 6자리를 입력해주세요.");
            return "otp-verify";
        }

        boolean success = googleOtpService.validateOtp(user, code);
        if (success) {
            session.setAttribute("OTP_VERIFIED", true);
            return "redirect:/articles";
        }

        model.addAttribute("error", "OTP 코드가 일치하지 않습니다. 다시 시도해주세요.");
        return "otp-verify";
    }

    private User getUser(Authentication auth) {
        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));
    }

    private String reloadSetup(User user, Model model) {
        GoogleOtp otp = googleOtpService.getOrCreateOtp(user);
        String qrBase64 = googleOtpService.generateQrBase64(otp.getSecretKey(), user.getEmail());
        model.addAttribute("qrBase64", qrBase64);
        model.addAttribute("secretKey", otp.getSecretKey());
        return "otp-setup";
    }
}
