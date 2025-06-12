package com.filmdb.auth.auth_service.domain.services;

import com.filmdb.auth.auth_service.domain.model.EncodedPassword;
import com.filmdb.auth.auth_service.domain.model.PlainPassword;

public interface PasswordEncoder {

    EncodedPassword encode(PlainPassword plainPassword);
    boolean matches(PlainPassword plainPassword, EncodedPassword encodedPassword);

}
