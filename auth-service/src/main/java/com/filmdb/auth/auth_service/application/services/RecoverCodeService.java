package com.filmdb.auth.auth_service.application.services;

import com.filmdb.auth.auth_service.domain.model.RecoverCode;
import com.filmdb.auth.auth_service.domain.model.valueobject.Email;

public interface RecoverCodeService {

    RecoverCode generate(Email email, String ipAddress, String userAgent);
    void delete(RecoverCode recoverCode);

}
