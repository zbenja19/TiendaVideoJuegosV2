package com.tiendavideojuegos.tiendavideojuegos.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PedidosDTO {

    private Integer id;
    private Integer clienteId;
    private LocalDate fechaAgregada;
    private Double montoTotal;
    private Boolean estado;
}
