package com.tienda.catalogo_videojuegos.DTO;

import lombok.Data;

@Data
public class VideoJuegoDTO {

    private Integer idVideoJuego;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String genero;
    private String nombreCategoria;// Cambie los atributos de Integer a String para
    private String nombrePlataforma;// poder validar sin errores en el dto
    private String nombreProveedor;
}
