package com.ccn.springai.service;

import com.ccn.springai.domain.GoogleOtp;
import com.ccn.springai.domain.User;
import com.ccn.springai.repository.GoogleOtpRepository;
import com.ccn.springai.repository.UserRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GoogleOtpService {

    private static final String ISSUER = "ALPS";

    private final GoogleOtpRepository googleOtpRepository;
    private final UserRepository userRepository;
    private final GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();

    public Optional<GoogleOtp> findByUser(User user) {
        return googleOtpRepository.findByUser(user);
    }

    @Transactional
    public GoogleOtp getOrCreateOtp(User user) {
        return googleOtpRepository.findByUser(user).orElseGet(() -> {
            GoogleAuthenticatorKey key = googleAuthenticator.createCredentials();
            GoogleOtp otp = GoogleOtp.builder()
                    .user(user)
                    .secretKey(key.getKey())
                    .build();
            return googleOtpRepository.save(otp);
        });
    }

    public String generateQrBase64(String secret, String email) {
        try {
            String encodedIssuer = URLEncoder.encode(ISSUER, StandardCharsets.UTF_8);
            String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);
            String otpAuthUrl = String.format(
                    "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                    encodedIssuer, encodedEmail, secret, encodedIssuer
            );

            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(otpAuthUrl, BarcodeFormat.QR_CODE, 250, 250);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("QR 코드 생성 실패", e);
        }
    }

    public boolean verifyCode(String secret, int code) {
        return googleAuthenticator.authorize(secret, code);
    }

    @Transactional
    public boolean enableOtp(User user, int code) {
        GoogleOtp otp = googleOtpRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("OTP 설정 정보가 없습니다."));

        if (verifyCode(otp.getSecretKey(), code)) {
            otp.enable();
            return true;
        }
        otp.incrementFailCount();
        return false;
    }

    @Transactional
    public void resetOtp(User user) {
        googleOtpRepository.findByUser(user).ifPresent(googleOtpRepository::delete);
    }

    @Transactional
    public boolean validateOtp(User user, int code) {
        GoogleOtp otp = googleOtpRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("OTP 설정 정보가 없습니다."));

        if (verifyCode(otp.getSecretKey(), code)) {
            otp.resetFailCount();
            return true;
        }
        otp.incrementFailCount();
        return false;
    }
}
