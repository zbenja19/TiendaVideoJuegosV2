package com.tienda.catalogo_videojuegos.DTO;

import lombok.Data;

@Data
public class ProveedorDTO {

    private Integer idProveedor;
    private String nombre;
    private String email;
    private String telefono;

}
