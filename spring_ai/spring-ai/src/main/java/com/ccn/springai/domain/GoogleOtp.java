package com.ccn.springai.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "google_otp")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GoogleOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "secret_key", nullable = false)
    private String secretKey;

    @Column(name = "otp_enabled", nullable = false)
    private boolean otpEnabled = false;

    @Column(name = "fail_count", nullable = false)
    private int failCount = 0;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public GoogleOtp(User user, String secretKey) {
        this.user = user;
        this.secretKey = secretKey;
        this.otpEnabled = false;
        this.failCount = 0;
    }

    public void enable() {
        this.otpEnabled = true;
        this.failCount = 0;
    }

    public void incrementFailCount() {
        this.failCount++;
    }

    public void resetFailCount() {
        this.failCount = 0;
    }
}
