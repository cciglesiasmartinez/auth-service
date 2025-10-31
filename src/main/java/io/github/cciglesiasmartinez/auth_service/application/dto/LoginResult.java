package io.github.cciglesiasmartinez.auth_service.application.dto;

import io.github.cciglesiasmartinez.auth_service.domain.model.RefreshToken;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.LoginResponse;

public record LoginResult(Envelope<LoginResponse> envelope, RefreshToken refreshToken) {}
