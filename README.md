# BiblioRed APF3 con MySQL

## Cómo iniciar el proyecto

Requisitos: Java 17 o superior, Maven y MySQL en ejecución. Puedes usar IntelliJ IDEA.

### Preparación de una sola vez

1. Abre MySQL Workbench y conecta con tu servidor.
2. Abre `database/mysql.sql` con File > Open SQL Script y ejecuta el archivo completo con el botón del rayo.
3. El script crea bibliored, las siete tablas relacionadas y datos ficticios para probar la página. No elimina datos existentes. Está preparado para una instalación nueva: si ya tienes tablas de otra versión, no ejecutes borrados para solucionarlo.
4. Abre `application-local.properties` junto a `pom.xml`. Escribe tu usuario y contraseña de MySQL en las dos propiedades. La contraseña de MySQL no es la contraseña de acceso a la página. Este archivo queda excluido de Git.

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

El JAR compilado se conserva como entrega secundaria en app/bibliored.jar. La forma normal de trabajar y sustentar es `mvn spring-boot:run`.

## Alcance

Funcionan acceso por roles, catálogo, solicitudes, entregas y devoluciones, control de restricciones, generación de multas e inventario XLSX. Permanecen pendientes el CRUD administrativo integral, regularización de multas, reportes adicionales y publicación GitHub.

## Evidencia de esta actualización

El 29 de octubre de 2026 se validó el SQL en una instancia MySQL 9.7.1 independiente, se inició el servidor con Maven y se verificaron acceso BCrypt, catálogo, circulación completa, disponibilidad, reporte Excel y permisos. Las 18 pruebas automáticas también aprobaron. Se conservan evidencias en docs/evidencias.

El informe y las diapositivas del paquete describen la entrega original. Esta actualización cambia el inicio y la persistencia a MySQL. La guía actual y ACTUALIZACION_MYSQL.md documentan esta diferencia.

Reconstrucción asistida desde los avances 1 y 2. El equipo debe revisar y comprender el código antes de sustentar.
