package io.github.cciglesiasmartinez.auth_service.application.context;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RequestContext {

    private String ip;

    private String userAgent;

}
