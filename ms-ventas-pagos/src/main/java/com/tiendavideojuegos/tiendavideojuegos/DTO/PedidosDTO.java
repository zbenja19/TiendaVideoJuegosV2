package com.tiendavideojuegos.tiendavideojuegos.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class PedidosDTO {

    private Integer id;
    private Date fechaAgregada;
    private Double montoTotal;
    private Boolean estado;
}
