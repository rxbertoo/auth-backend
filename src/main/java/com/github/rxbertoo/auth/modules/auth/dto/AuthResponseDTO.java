package com.github.rxbertoo.auth.modules.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AuthResponseDTO {

    private String token;
    private UUID uuid;

    private String email;
    private String name;
}
