# Evidencia local del APF3

27 de octubre de 2026. JDK 17.0.20; Maven 3.9.16; Spring Boot 3.5.7.
18 pruebas: 15 de integración y 3 unitarias. Cero fallos, errores u omitidas en la ejecución final.
Ciclo rojo: rechazaSolicitudConPrestamoVencido falla porque no se lanza la excepción. Ciclo verde: validación implementada, suite aprobada. Las pruebas no validan MySQL ni carga.

La compilación, empaquetado y Javadoc se ejecutaron con mvn test package javadoc:javadoc. Una segunda ejecución de mvn test verifica la mejora del caso de privacidad del historial; no hubo cambios posteriores en código de producción.

Prueba manual: lector solicita ejemplar; administrador confirma entrega y registra devolución. Capturas incluidas en el informe. Las cuentas, bibliotecas y movimientos son ficticios.
