package com.filmdb.auth.auth_service.adapter.in.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test class.
 */

@RestController
public class HealthCheckController {
    @GetMapping("/ping")
    public String ping() {
        System.out.println("==> /ping endpoint was called :3");
        return "Auth API is active :3";
    }
}