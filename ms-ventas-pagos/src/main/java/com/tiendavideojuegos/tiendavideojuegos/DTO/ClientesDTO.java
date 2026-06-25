package com.tiendavideojuegos.tiendavideojuegos.DTO;

import lombok.Data;

@Data
public class ClientesDTO {

    private Integer id;
    private String nombre;
    private String correo;
    private String telefono;
    private String contrasena;
    private String pedidos;
}
