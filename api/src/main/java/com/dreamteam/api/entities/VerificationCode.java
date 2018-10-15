package com.dreamteam.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Delegate;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "verification_codes")
public class VerificationCode {

    @Delegate
    @EmbeddedId
    private VerificationCodePK verificationCodePK;

    @Data
    @Builder
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VerificationCodePK implements Serializable {
        @Column(name = "user_id")
        private Integer userId;
        @Column(name = "verification_code")
        private String verificationCode;
    }
}
