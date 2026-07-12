@echo off
cd /d "%~dp0"

echo Iniciando Servidor Eureka...
cd eureka
start cmd /k "mvnw spring-boot:run"

echo Esperando 15 segundos...
timeout /t 15 /nobreak > nul

echo Iniciando API Gateway...
cd ..\api-gateway
start cmd /k "mvnw spring-boot:run"

echo Iniciando Microservicio Catalogo...
cd ..\ms-catalogo-videojuegos
start cmd /k "mvnw spring-boot:run"

echo Iniciando Microservicio Marketing...
cd ..\ms-marketing-biblioteca
start cmd /k "mvnw spring-boot:run"

echo Iniciando Microservicio Ventas...
cd ..\ms-ventas-pagos
start cmd /k "mvnw spring-boot:run"

echo Ecosistema iniciado.
echo Eureka: http://localhost:8761
echo Gateway: http://localhost:8080

pause


--Comando para iniciar todos los microservicios
--.\iniciar_todo.bat