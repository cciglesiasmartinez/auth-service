package io.github.cciglesiasmartinez.auth_service.domain.port.out;

import io.github.cciglesiasmartinez.auth_service.domain.model.RecoverCode;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.RecoverCodeString;

import java.util.Optional;

public interface RecoverCodeRepository {

    Optional<RecoverCode> findByCodeString(RecoverCodeString recoverCodeString);
    void save(RecoverCode recoverCode);
    void delete(RecoverCode recoverCode);
}
