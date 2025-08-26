package io.github.cciglesiasmartinez.auth_service.domain.port.out;

import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.RecoverCodeString;

public interface RecoverCodeGenerator {

    RecoverCodeString generate();

}
