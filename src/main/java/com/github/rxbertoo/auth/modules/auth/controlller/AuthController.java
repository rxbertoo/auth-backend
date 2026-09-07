package com.github.rxbertoo.auth.modules.auth.controlller;


import com.github.rxbertoo.auth.common.dto.MessageResponse;
import com.github.rxbertoo.auth.modules.auth.dto.AuthLoginDTO;
import com.github.rxbertoo.auth.modules.auth.dto.AuthRegisterDTO;
import com.github.rxbertoo.auth.modules.auth.dto.AuthResponseDTO;
import com.github.rxbertoo.auth.modules.auth.service.AuthService;
import com.github.rxbertoo.auth.modules.user.entity.UserEntity;
import com.github.rxbertoo.auth.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthLoginDTO dto) {

        AuthResponseDTO authResponse = authService.login(dto);
        ResponseCookie cookie = ResponseCookie.from(JwtService.COOKIE_NAME, authResponse.getToken())
                .httpOnly(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(authResponse);
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = ResponseCookie.from(JwtService.COOKIE_NAME, "")
                .httpOnly(true)
                .path("/")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("User logged out successfully"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthRegisterDTO dto) {
        authService.register(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("User registered successfully"));
    }
}
