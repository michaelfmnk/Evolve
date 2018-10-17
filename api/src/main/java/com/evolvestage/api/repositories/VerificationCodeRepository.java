package com.evolvestage.api.repositories;

import com.evolvestage.api.entities.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository
        extends JpaRepository<VerificationCode, VerificationCode.VerificationCodePK> {

}
