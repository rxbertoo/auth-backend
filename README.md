# Auth Backend - Spring Boot

Backend robusto, modular y seguro para la **gestión de autenticación** y **creación de usuarios**, desarrollado con **Java** y **Spring Boot**. Diseñado siguiendo las mejores prácticas de la industria en seguridad web: uso de **Spring Security**, control de acceso mediante tokens almacenados en **cookies de sesión (`HttpOnly`)**, protección y configuración estricta de **CORS**, y manejo global de excepciones.

---

## Características Principales

- **Registro de Usuarios**: Validación de datos de entrada y prevención de duplicados.
- **Autenticación Segura**:
  - Hashing seguro de contraseñas con **BCrypt**.
  - Emisión de tokens **JWT** transmitidos mediante **Cookies de Sesión (`HttpOnly`, `SameSite=Strict/Lax`, `Secure`)**, mitigando riesgos de ataques XSS (*Cross-Site Scripting*).
  - Endpoint de cierre de sesión (`/logout`) que invalida la cookie de sesión de forma segura.
- **Configuración CORS**: Políticas de origen cruzado configuradas explícitamente para permitir la integración segura con frontends (React, Vue, Angular, Next.js, etc.) con soporte para envío de credenciales (`allowCredentials=true`).
- **Arquitectura Modular por Dominios**: Código organizado por módulos funcionales (`auth`, `user`, `security`, `exception`, `common`).
- **Filtro de Seguridad Personalizado**: Interceptación y validación de tokens en cada petición mediante `JwtAuthenticationFilter`.
- **Manejo Centralizado de Excepciones**: Respuestas de error estandarizadas y controladas con `@ControllerAdvice` / `GlobalExceptionHandler` evitando fugas de stack trace.

---

## Stack Tecnológico

- **Lenguaje**: Java 17+ / 21
- **Framework**: Spring Boot 3.x
- **Seguridad**: Spring Security & BCrypt Password Encoder
- **Tokens**: JSON Web Tokens (JJWT)
- **Persistencia**: Spring Data JPA & Hibernate
- **Base de Datos**: PostgreSQL / MySQL / H2 (configurable)
- **Construcción**: Maven (`mvnw`)

---

## Estructura del Proyecto

```text
src/main/java/com/github/rxbertoo/auth/
├── AuthApplication.java           # Clase principal de arranque Spring Boot
├── common/                        # DTOs y utilidades transversales
│   └── dto/                       # Respuestas estándar (ErrorResponse, MessageResponse)
├── exception/                     # Excepciones de negocio y manejador global
│   ├── BusinessRuleException.java
│   ├── ErrorCode.java
│   ├── NotFoundException.java
│   └── handler/
│       └── GlobalExceptionHandler.java
├── modules/
│   ├── auth/                      # Módulo de Autenticación
│   │   ├── controlller/           # Endpoints de login, registro, logout
│   │   ├── dto/                   # DTOs (AuthLoginDTO, AuthRegisterDTO, AuthResponseDTO)
│   │   └── service/               # Lógica de autenticación y generación de cookies
│   └── user/                      # Módulo de Usuarios
│       ├── controller/            # Endpoints de consulta/gestión de usuarios
│       ├── dto/                   # DTOs de usuario (UserCreateDTO)
│       ├── entity/                # Entidad JPA UserEntity
│       ├── repository/            # Repositorio JPA
│       └── service/               # Lógica de creación y consulta de usuarios
└── security/                      # Infraestructura de Seguridad
    ├── JwtAuthenticationFilter.java  # Filtro para validación de token en cookie
    ├── JwtService.java               # Generación y parseo de JWT
    └── config/
        ├── CorsConfig.java           # Configuración CORS con soporte de credenciales
        ├── PasswordEncoderConfig.java# Bean BCryptPasswordEncoder
        └── SecurityConfig.java       # Configuración de rutas públicas/protegidas y sesiones
