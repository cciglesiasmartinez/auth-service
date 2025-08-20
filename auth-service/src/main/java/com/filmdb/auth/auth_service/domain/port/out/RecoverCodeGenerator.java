package com.filmdb.auth.auth_service.domain.port.out;

import com.filmdb.auth.auth_service.domain.model.valueobject.RecoverCodeString;

public interface RecoverCodeGenerator {

    RecoverCodeString generate();

}
