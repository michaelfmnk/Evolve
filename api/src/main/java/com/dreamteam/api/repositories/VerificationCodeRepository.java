package com.dreamteam.api.repositories;

import com.dreamteam.api.entities.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository
        extends JpaRepository<VerificationCode, VerificationCode.VerificationCodePK> {

}
