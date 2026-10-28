# BiblioRed APF3

Reconstrucción asistida desde los mockups y documentos de los avances 1 y 2. Requiere revisión y comprensión por el equipo antes de sustentar.

## Inicio rápido en Windows

1. Instalar JDK 17 o superior si no está disponible.
2. Extraer TODO el ZIP a una carpeta con permiso de escritura.
3. Ejecutar `iniciar.bat` y mantener su ventana abierta.
4. Abrir http://localhost:8080.
5. Detener con Ctrl+C en esa ventana.

El JAR ejecutable está en `app/bibliored.jar`. No abrir index.html como archivo: necesita el servidor Java. El puerto puede cambiarse con `java -jar app/bibliored.jar --server.port=8081`.

## Cuentas de demostración local

- lector@bibliored.local — USUARIO
- personal@bibliored.local — PERSONAL
- admin@bibliored.local — ADMIN

Contraseña de las tres cuentas: `BiblioRed2026!`. Los datos son ficticios. No utilizar estas cuentas en producción.

## Recorrido para sustentar

1. Acceder como lector y buscar un título.
2. Solicitar un ejemplar para hoy y revisar el historial.
3. Salir e ingresar como personal o administrador.
4. Abrir Panel de gestión y confirmar la entrega.
5. Registrar la devolución y consultar disponibilidad.
6. Descargar el inventario Excel.

La fecha límite se fija 14 días después de la entrega. La tarifa académica de retraso es S/1 por día. Máximo: tres movimientos abiertos. No hay pasarela de pago. El personal puede atender todas las bibliotecas en este MVP; falta restringir por sede.

## Desarrollo y pruebas

Requisitos: JDK 17 y Maven 3.9. Desde esta carpeta:

```text
mvn test package
mvn javadoc:javadoc
java -jar target/bibliored-0.3.0.jar
```

Las pruebas utilizan H2 en memoria. La aplicación local persiste en `data/bibliored.mv.db`, excluido de Git. `docs/evidencias` contiene resultados reales; `docs/javadoc/index.html` es la documentación generada. Los commits de reconstrucción están en `.git` y también se entregan en `historial.bundle` por si el extractor omite carpetas ocultas.

## MySQL preparado, pendiente de validar

Ejecutar `database/mysql.sql` en una base nueva. Registrar bibliotecas y usuarios iniciales con hash BCrypt mediante un procedimiento administrativo seguro. Definir DB_URL, DB_USER y DB_PASSWORD. Iniciar con `--spring.profiles.active=mysql`. Ese perfil exige HTTPS por la cookie Secure y no carga usuarios demo. No se ha ejecutado una integración real MySQL.

## Alcance y pendientes

Implementados: acceso, catálogo, disponibilidad, solicitudes, entregas y devoluciones. Parciales: multas (sin regularización), gestión administrativa (alta de libros y consulta de usuarios), reportes (solo inventario). Faltan CRUD integral, recuperación de contraseña, configuración de reglas, aislamiento por sede, concurrencia por usuario, rendimiento y despliegue institucional. No se publicó en GitHub; el remoto debe configurarse con la cuenta y repositorio del equipo. No se fabricaron evidencias de taller, colaboración o autoría previa.
