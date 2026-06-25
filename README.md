## Tienda Video Juegos 
## V1 del apartado ventas-pagos

## En esta etapa se contruyo la base del backend, con el modelo tipico de Spring Boot:
1. model
2. repository
3. service
4. controller
5. DTO
6. config

## Clases implementadas
1. Clientes
2. DetallePedidos
3. Pedidos
4. Pago

## Model
Dentro del model se asignaros atributos base a las clases creadas.

## Repository
Se crearon las interfaces que se conectan con la base de datos, extendiendo de JpaRepository. 
Esto permite guardar, buscar, actualizar y eliminar información sin tener que escribir consultas SQL manualmente.

## Service
Aqui se aplica la logica de negocio, manejando las operaciones principales del codigo, (crear, buscar, actulizar, eliminar).

## Controller
Se crearon los controladores que exponen los endpoints de la API 
(rutas que se pueden llamar desde Postman o el frontend), conectando las peticiones HTTP con la lógica del Service.

## DTO
Se crearon clases DTO (Data Transfer Object) para controlar exactamente qué información se entrega al cliente que consume la API, 
sin exponer todos los campos del modelo.

## Config
Se creó la clase WebClientConfig, donde se configura el WebClient.Builder como un Bean de Spring.
