package com.filmdb.auth.auth_service.infrastructure.persistence.mysql.mapper;

import com.filmdb.auth.auth_service.domain.model.valueobject.Email;
import com.filmdb.auth.auth_service.domain.model.valueobject.EncodedPassword;
import com.filmdb.auth.auth_service.domain.model.valueobject.UserId;
import com.filmdb.auth.auth_service.domain.model.valueobject.Username;

//@Mapper(componentModel = "spring")
public interface UserValueObjectMapper {

    // UserId
    default String mapFromUserId(UserId id) { return id.value(); }
    default UserId mapToUserId(String id) { return UserId.of(id); }

    // Username
    default String mapFromUsername(Username username) { return username.value(); }
    default Username mapToUsername(String username) { return Username.of(username); }

    // Email
    default String mapFromEmail(Email email) { return email.value(); }
    default Email mapToEmail(String email) { return Email.of(email); }

    // EncodedPassword
    default String mapFromEncodedPassword(EncodedPassword password) { return password.value(); }
    default EncodedPassword mapToEncodedPassword(String password) { return EncodedPassword.of(password); }
}
