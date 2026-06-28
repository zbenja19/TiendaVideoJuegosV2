CREATE TABLE clientes(
        id INT AUTO_INCREMENT PRIMARY KEY,
        nombre VARCHAR(50)  NOT NULL,
        correo VARCHAR(100) NOT NULL,
        telefono VARCHAR(15) NOT NULL,
        contrasena VARCHAR(15) NOT NULL
);

INSERT INTO clientes (nombre, correo, telefono, contrasena) VALUES ('Juan Mondaca', 'ju.mondacah@duocuc.cl', '+56 9 1122 4477', 'juanMondacaEsIgualALeoRey');   