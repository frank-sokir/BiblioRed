@echo off
cd /d "%~dp0"
where java >nul 2>nul
if errorlevel 1 (echo Se requiere Java 17 o superior. & pause & exit /b 1)
if not exist "app\bibliored.jar" (echo No se encontro app\bibliored.jar. Compila con mvn package. & pause & exit /b 1)
echo Abre http://localhost:8080 en tu navegador.
echo Para detener BiblioRed presiona Ctrl+C.
java -jar app\bibliored.jar
pause
