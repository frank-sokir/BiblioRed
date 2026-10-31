# BiblioRed APF3 con MySQL

## Cómo iniciar el proyecto

Requisitos: Java 17 o superior, Maven y MySQL en ejecución. Puedes usar IntelliJ IDEA.

### En esta computadora

La base bibliored ya está importada en una instancia independiente de MySQL en 127.0.0.1:3308. La conexión está guardada en application-local.properties. Basta ejecutar mvn spring-boot:run. MySQL arranca al iniciar sesión en Windows mediante el acceso directo BiblioRed MySQL. Los datos están en %LOCALAPPDATA%/BiblioRed/MySQL/data. El servidor original del puerto 3306 no se modificó.

Las credenciales locales no se incluyen en el ZIP. Si extraes el ZIP en otra carpeta de esta misma PC, copia allí el application-local.properties de la carpeta Codigo que ya está configurada. Para otra PC realiza los pasos siguientes.

### Preparación en otra computadora

1. Abre MySQL Workbench y conecta con tu servidor.
2. Abre `database/mysql.sql` con File > Open SQL Script y ejecuta el archivo completo con el botón del rayo.
3. El script crea bibliored, las siete tablas relacionadas y datos ficticios para probar la página. No elimina datos existentes. Está preparado para una instalación nueva: si ya tienes tablas de otra versión, no ejecutes borrados para solucionarlo.
4. Copia `application-local.properties.example` y cambia el nombre de la copia a `application-local.properties`, junto a `pom.xml`.
5. Abre la copia y configura el usuario y la contraseña de tu servidor MySQL. Si tu puerto es distinto de 3306, añade `spring.datasource.url` con la dirección y el puerto correctos, como se explica en Configuración. La contraseña de MySQL es distinta de la contraseña de acceso a la página. Este archivo queda excluido de Git.

Esta preparación se realiza una sola vez. No vuelvas a ejecutar el SQL para iniciar la página cada día.

### Iniciar cada vez

Abre una terminal dentro de la carpeta `Codigo`, donde está `pom.xml`, y ejecuta:

```bash
mvn spring-boot:run
```

Ese comando compila el proyecto, conecta con MySQL, comprueba las tablas y enciende el servidor Java. No crea tablas automáticamente y no inicia el servicio MySQL: MySQL debe estar encendido previamente.

Cuando aparezca `Started BiblioRedApplication`, abre http://localhost:8080.

En IntelliJ también puedes abrir `BiblioRedApplication.java` y presionar Run. Configura el directorio de trabajo en la carpeta que contiene `pom.xml` y `application-local.properties`. No se necesita ningún archivo .bat o .cmd.

Para detener la aplicación, presiona Ctrl+C en la terminal o Stop en IntelliJ.

## Cuentas de demostración

El SQL importa estas cuentas, con contraseñas almacenadas como hash BCrypt:

- Lector: lector@bibliored.local
- Personal: personal@bibliored.local
- Administrador: admin@bibliored.local

Contraseña de las tres cuentas: `BiblioRed2026!`.

## Configuración

La conexión predeterminada apunta a `localhost:3306/bibliored`. Si usas otro puerto, modifica la URL en `application-local.properties` con la propiedad `spring.datasource.url`. También puedes configurar DB_URL, DB_USER y DB_PASSWORD mediante variables de entorno.

No subas tus credenciales a GitHub. El archivo `application-local.properties.example` muestra el formato de configuración.

## Errores frecuentes

- Access denied: comprueba el usuario y la contraseña en application-local.properties.
- Communications link failure: verifica que el servicio MySQL esté iniciado y que coincidan host y puerto.
- Unknown database o Missing table: importa database/mysql.sql en una base nueva del servidor configurado.
- Puerto 8080 ocupado: cierra la instancia anterior o ejecuta `mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081`.

## Pruebas y compilación

```bash
mvn test
mvn clean package
mvn javadoc:javadoc
```

H2 se utiliza únicamente en las pruebas automáticas, con una base temporal independiente de MySQL. La ejecución normal usa MySQL y no carga datos automáticamente: los datos iniciales provienen del SQL importado.

La descarga de GitHub contiene el código fuente. Puedes generar el JAR con `mvn clean package`. La forma normal de iniciar es `mvn spring-boot:run`.

## Alcance

Funcionan acceso por roles, catálogo, solicitudes, entregas y devoluciones, control de restricciones, generación de multas e inventario XLSX. Permanecen pendientes el CRUD administrativo integral, regularización de multas, reportes adicionales. El código está publicado en https://github.com/frank-sokir/BiblioRed.

## Evidencia de esta actualización

El 29 de octubre de 2026 se validó el SQL en una instancia MySQL 9.7.1 independiente, se inició el servidor con Maven y se verificaron acceso BCrypt, catálogo, circulación completa, disponibilidad, reporte Excel y permisos. Las 18 pruebas automáticas también aprobaron. Se conservan evidencias en docs/evidencias.

El informe y las diapositivas actualizados describen el diseño y la persistencia en MySQL. La guía actual y docs/ACTUALIZACION_MYSQL.md explican la instalación y la validación.

Reconstrucción asistida desde los avances 1 y 2. El equipo debe revisar y comprender el código antes de sustentar.
