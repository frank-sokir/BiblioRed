# BiblioRed con MySQL local

29 de octubre de 2026.

Se importó database/mysql.sql en una instancia independiente MySQL 9.7.1 en 127.0.0.1:3308. Las siete tablas, bibliotecas, seis libros, dieciocho ejemplares y tres cuentas están disponibles. El servidor original de 3306 conserva su configuración y datos.

Inicio: mvn spring-boot:run desde Codigo. La aplicación abre el servidor Java en 8080, conecta mediante JDBC y valida el esquema sin crear tablas ni cargar datos. H2 solo se utiliza en las pruebas.

application-local.properties contiene la conexión privada y está excluido de Git y del ZIP. La cuenta de aplicación tiene permisos SELECT, INSERT, UPDATE y DELETE sobre bibliored. Los archivos privados de administración están en %LOCALAPPDATA%/BiblioRed/MySQL. La instancia arranca al iniciar sesión de Windows con un acceso directo al ejecutable mysqld ya instalado.

Prueba real aprobada: login BCrypt, catálogo, reserva, entrega, devolución, disponibilidad, inventario XLSX y permisos por rol. El movimiento de demostración queda registrado como devuelto. La suite automatizada contiene 18 pruebas aprobadas.

El informe y la presentación MySQL describen esta versión. La administración integral y otros reportes siguen siendo ampliaciones pendientes del avance; no se presentan como implementados.
