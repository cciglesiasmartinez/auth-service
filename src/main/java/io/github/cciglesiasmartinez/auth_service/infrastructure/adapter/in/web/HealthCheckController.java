package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test class.
 */
@Slf4j
@RestController
public class HealthCheckController {
    @GetMapping("/ping")
    public String ping() {
        log.info("/ping endpoint called.");
        return "Auth API is active :3";
    }
    @GetMapping("/admin/check-security")
    public String checkSecurity() {
        log.info("/check-security called");
        return "Security check";
    }
}