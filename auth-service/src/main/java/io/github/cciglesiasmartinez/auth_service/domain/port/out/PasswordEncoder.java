package io.github.cciglesiasmartinez.auth_service.domain.port.out;

import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.EncodedPassword;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.PlainPassword;

public interface PasswordEncoder {

    EncodedPassword encode(PlainPassword plainPassword);
    boolean matches(PlainPassword plainPassword, EncodedPassword encodedPassword);

}
