# SGHSS - Backend (Spring Boot)

Pacote: com.vidaplus.sghss

Banco default: vida_plus_db

## Como usar

1. Ajuste `src/main/resources/application.properties` com sua senha MySQL.
2. Crie o banco `vida_plus_db` no MySQL.
3. Rode `mvn spring-boot:run` (ou importe no IntelliJ como Maven project).

Endpoints básicos:
- POST /auth/register
- POST /auth/login
- CRUD: /pacientes, /profissionais, /consultas, /prontuarios

Swagger UI: http://localhost:8080/swagger-ui.html
