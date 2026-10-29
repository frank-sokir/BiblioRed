# Actualización de inicio y persistencia

29 de octubre de 2026.

Inicio habitual: mvn spring-boot:run desde Codigo.

MySQL es la conexión predeterminada. database/mysql.sql prepara siete tablas, claves, índices y datos de demostración. La aplicación usa ddl-auto=validate y spring.sql.init.mode=never: no crea tablas ni siembra datos durante el arranque normal.

La clase DatosDemo y schema.sql se trasladaron a src/test. H2 tiene alcance test en Maven y no se distribuye como motor de la aplicación normal.

Los controles de sesión, roles, BCrypt y CSRF permanecen activos. La cookie Secure se activa en el perfil prod para HTTPS. El perfil local funciona con HTTP en localhost.

application-local.properties contiene la configuración privada y no se versiona. La contraseña de MySQL debe introducirse en cada equipo. Las contraseñas del SQL son hashes BCrypt de las cuentas ficticias de demostración.

La integración se comprobó contra una instancia temporal MySQL independiente en 127.0.0.1:3307 y con el servidor Java en 8081 para no interferir con los servicios del usuario. El comando habitual apunta a MySQL 3306 y a la página 8080. La cuenta real del usuario no se modificó ni se utilizó para la prueba.

Comprobaciones: importación SQL, arranque Maven con validación del esquema, acceso, seis títulos y dieciocho ejemplares, reserva, entrega, devolución, disponibilidad, inventario Excel y rechazo de acceso administrativo para lector. Suite automática: 18 pruebas aprobadas.

El informe y el PPT anteriores documentaban H2 como entorno de demostración y MySQL como pendiente. Esta nota deja constancia del cambio de versión sin atribuir esas pruebas a la entrega anterior.
