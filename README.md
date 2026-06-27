## TiendaVideoJuegosV2

## RepositorioTiendaVideoJuegos

# Integrantes del Proyecto
1. **Benjamín Trujillo**
2. **Matías Oviedo**
3. **Karol Carrasco**

## Descripción del proyecto
El proyecto consiste en una plataforma especializada en una tienda de venta de video juegos digitales. La plataforma permite visualizar un catálogo de juegos, el cual va por distintintas categorías de video juegos.
Buscando mejor organización del sistema al querer mantener el orden los video juegos dentro de la tienda.

## Listado de Microservicios Implementados 
1. **Registro de Usuarios**
2. **Pedidos y detalle de pedidos**
3. **Ofertas y detalles de ofertas**
4. **Metodos de pago**
5. **Plataforma de los video juegos**
6. **Proveedores**
7. **Biblioteca de video juegos**

## Rutas principales del Gateway
1. **Usuario** | "/api/v1/clientes" | GET | Listar a todos los clientes y buscar alguno en especifico por su id.
2. **Videojuegos** | "/api/v1/Videojuegos" | GET | Listar video juegos de la tienda y buscar por id o categoría.
3. **Pedidos** | "/api/v1/pedidos/" | GET | Listar los pedidos realizados.
4. **Ofertas** | ""/api/v1/ofertas" | POST | Agregar nuevas ofertas de video juegos dentro de la tienda.
5. **Biblioteca** | "/api/v1/biblioteca" | POST | Agregar nuevos video juegos dentro de la biblioteca.
6. **Pago** | "/api/v1/pagos" | POST | Agregar metodos de pago.
7. **Proveedor** | "/api/v1/proveedor" | PUT | Actualizar datos del proveedor.   

## Instrucciones de Ejecución

### Ejecución Local

#### Prerrequisitos
1. Java JDK 21+
2. Node.js
3. Docker Desktop/ Xammp/ Postman

#### Pasos para iniciar:

1. **Clonar el repositorio:**
```bash
git clone https://github.com/zbenja19/TiendaVideoJuegosV2.git

cd TiendaVideoJuegosV2

code .

2. **Levantar la infraestructura (Bases de datos/Docker):**
  docker-compose up -d

  ```
