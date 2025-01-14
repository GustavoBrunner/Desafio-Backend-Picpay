package com.picpaysimplificado.dto;

public record AuthorizationDto(String status, Data data) {
    public static record Data(Boolean authorization) {}
}
