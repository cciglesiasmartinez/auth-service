package io.github.cciglesiasmartinez.auth_service.application.context;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to encapsulate HTTP request attributes at the controller (infrastructure) level and transfer
 * them to the application layer (use cases).
 */
@AllArgsConstructor
@Getter
@Setter
public class RequestContext {

    private String ip;
    private String userAgent;

}
