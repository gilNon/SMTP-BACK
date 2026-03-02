# Guía de Spring Security con JWT

## 📋 Resumen

Este proyecto implementa autenticación y autorización usando **Spring Security** con **JSON Web Tokens (JWT)**. Las contraseñas se almacenan encriptadas usando **BCrypt**.

## 🏗️ Arquitectura y Componentes

### 1. **UserEntity** (`model/UserEntity.java`)
- Implementa `UserDetails` de Spring Security
- Almacena información del usuario con contraseña encriptada
- Define roles y permisos del usuario
- Campos: email (username), password, firstName, lastName, role, isActive

### 2. **UserRepository** (`repository/UserRepository.java`)
- Métodos para buscar usuarios por email
- Verifica si un email ya existe

### 3. **JwtService** (`security/JwtService.java`)
- **Genera tokens JWT** cuando un usuario se registra o inicia sesión
- **Valida tokens JWT** en cada petición
- **Extrae información** del token (email del usuario)
- Configuración:
  - `jwt.secret`: Clave secreta para firmar tokens (cámbiala en producción)
  - `jwt.expiration`: Tiempo de expiración (24 horas por defecto)

### 4. **JwtAuthenticationFilter** (`security/JwtAuthenticationFilter.java`)
- **Intercepta todas las peticiones HTTP**
- Extrae el token del header `Authorization: Bearer <token>`
- Valida el token y autentica al usuario
- Si el token es válido, permite el acceso; si no, rechaza la petición

### 5. **ApplicationConfig** (`security/ApplicationConfig.java`)
- Define beans de Spring Security:
  - `UserDetailsService`: Carga usuarios desde la base de datos
  - `PasswordEncoder`: BCrypt para encriptar contraseñas
  - `AuthenticationManager`: Gestiona la autenticación

### 6. **SecurityConfig** (`security/SecurityConfig.java`)
- **Configuración principal de seguridad**
- Define qué endpoints son públicos y cuáles requieren autenticación:
  - `/auth/**` → Público (registro y login)
  - Todos los demás → Requieren autenticación
- Desactiva CSRF (no necesario para APIs REST stateless)
- Configura sesiones como STATELESS (sin cookies de sesión)

### 7. **AuthService** (`service/AuthService.java`)
- **Registro**: Crea usuario, encripta contraseña, genera token JWT
- **Login**: Valida credenciales, genera token JWT

### 8. **AuthController** (`controller/AuthController.java`)
- **POST /api/v1/auth/register**: Registrar nuevo usuario
- **POST /api/v1/auth/login**: Iniciar sesión

## 🔄 Flujo de Autenticación

### Registro de Usuario
```
1. Cliente → POST /api/v1/auth/register
   Body: { firstName, lastName, email, password }

2. AuthController recibe la petición
   ↓
3. AuthService.register():
   - Verifica que el email no exista
   - Crea UserEntity
   - Encripta la contraseña con BCrypt
   - Guarda en base de datos
   - Genera token JWT
   ↓
4. Responde con: { token, email, firstName, lastName, role }
```

### Login
```
1. Cliente → POST /api/v1/auth/login
   Body: { email, password }

2. AuthController recibe la petición
   ↓
3. AuthService.login():
   - AuthenticationManager valida credenciales
   - Si son correctas, genera token JWT
   ↓
4. Responde con: { token, email, firstName, lastName, role }
```

### Acceso a Endpoints Protegidos
```
1. Cliente → GET /api/v1/emails
   Header: Authorization: Bearer <token>

2. JwtAuthenticationFilter intercepta:
   - Extrae el token del header
   - Valida el token con JwtService
   - Si es válido, autentica al usuario
   - Permite el acceso al endpoint
   ↓
3. MailsController procesa la petición
   ↓
4. Responde con los datos
```

## 🔐 Seguridad de Contraseñas

Las contraseñas se encriptan usando **BCrypt**:
- **Nunca se almacenan en texto plano**
- BCrypt genera un hash único con salt
- Es computacionalmente costoso de romper
- Ejemplo: `password123` → `$2a$10$N9qo8uLOickgx2ZMRZoMye...`

## 📝 Ejemplos de Uso

### 1. Registrar Usuario
```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Juan",
    "lastName": "Pérez",
    "email": "juan@example.com",
    "password": "password123"
  }'
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "juan@example.com",
  "firstName": "Juan",
  "lastName": "Pérez",
  "role": "USER"
}
```

### 2. Iniciar Sesión
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "juan@example.com",
    "password": "password123"
  }'
```

### 3. Acceder a Endpoint Protegido
```bash
curl -X GET http://localhost:8080/api/v1/emails \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

## 🗄️ Migración de Base de Datos

Necesitas crear la tabla `users` en PostgreSQL:

```sql
CREATE TABLE users (
    id_user UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_users_email ON users(email);
```

## ⚙️ Configuración

En `application.yaml`:

```yaml
jwt:
  secret: ${JWT_SECRET:404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970}
  expiration: ${JWT_EXPIRATION:86400000}  # 24 horas en milisegundos
```

**⚠️ IMPORTANTE:** En producción, usa variables de entorno:
```bash
export JWT_SECRET="tu-clave-secreta-muy-larga-y-segura"
export JWT_EXPIRATION=86400000
```

## 🔒 Roles y Permisos

Actualmente hay 2 roles definidos en `Role.java`:
- **USER**: Usuario normal
- **ADMIN**: Administrador

Para proteger endpoints por rol, modifica `SecurityConfig.java`:

```java
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/auth/**").permitAll()
    .requestMatchers("/admin/**").hasRole("ADMIN")
    .anyRequest().authenticated()
)
```

## 🧪 Testing

### Probar con Postman/Insomnia:

1. **Registrar usuario** → Guardar el token
2. **Login** → Guardar el token
3. **Acceder a /emails** → Agregar header `Authorization: Bearer <token>`

### Verificar encriptación:

Consulta la base de datos:
```sql
SELECT email, password FROM users;
```
Verás que las contraseñas están encriptadas con BCrypt.

## 🚀 Próximos Pasos

1. **Manejo de errores**: Crear excepciones personalizadas
2. **Refresh tokens**: Implementar tokens de refresco
3. **Roles avanzados**: Agregar más roles y permisos
4. **Rate limiting**: Limitar intentos de login
5. **Email verification**: Verificar email al registrarse
6. **Password reset**: Recuperación de contraseña

## 📚 Conceptos Clave

- **JWT**: Token autofirmado que contiene información del usuario
- **BCrypt**: Algoritmo de hash para contraseñas
- **Stateless**: No se guardan sesiones en el servidor
- **Bearer Token**: Esquema de autenticación HTTP
- **Filter Chain**: Cadena de filtros que procesa cada petición
- **UserDetails**: Interfaz de Spring Security para usuarios
- **AuthenticationManager**: Gestiona el proceso de autenticación
