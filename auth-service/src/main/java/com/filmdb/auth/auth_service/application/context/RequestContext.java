package com.filmdb.auth.auth_service.application.context;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RequestContext {

    private String ip;

    private String userAgent;

}
