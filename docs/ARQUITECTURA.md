# Arquitectura de BiblioRed

## Capas

Vista: HTML/CSS/JS en resources/static. Control: ApiController y Errores. Negocio: PrestamoService. Persistencia: siete DAO JpaRepository y siete entidades JPA. Seguridad: SeguridadConfig. Estrategia: PoliticaMulta con MultaDiaria.

## Flujo de estados

Ejemplar: DISPONIBLE → RESERVADO → PRESTADO → DISPONIBLE.
Préstamo: SOLICITADO → ACTIVO → DEVUELTO. VENCIDO se deriva de ACTIVO con fechaLimite anterior a hoy.

El flujo usa transacciones y bloqueo pesimista por ejemplar. El máximo por lector todavía requiere bloqueo por usuario para solicitudes concurrentes en ejemplares distintos.

## Modelo

Biblioteca 1:N Usuario y Ejemplar. Libro 1:N Ejemplar. Usuario 1:N Prestamo. Ejemplar 1:N Prestamo. Prestamo 1:0..1 Devolucion y Multa. Las asociaciones se almacenan como IDs; los esquemas incorporan FK. Campos públicos y respuesta directa de entidades simplifican este MVP; encapsulación y DTO de salida quedan pendientes.

## TDD

Se añadió primero la prueba rechazaSolicitudConPrestamoVencido. Su ejecución inicial falló porque solicitar no bloqueaba al lector con atraso. Se agregó esa validación y la suite volvió a verde. La evidencia roja corresponde a ese ciclo real y no a toda la historia del proyecto. Se aplicó formato consistente después de implementar.

## Seguridad

Sesión Spring Security; BCrypt; CSRF; validación de entrada; escape de textos de vista; permisos en rutas; lectura de historial filtrada por usuario. Hash excluido de JSON. Logback registra IDs operativos sin credenciales. El perfil local es demostrativo; falta hardening, rate limiting, revisión de dependencias y permisos por sede.

## GitHub

No existe remoto configurado. Crear un repositorio del equipo, añadir el remoto y publicar los commits locales. main estable, ramas por funcionalidad, revisión y pruebas antes de integrar. No presentar este historial como si fuera recuperado del proyecto perdido.
