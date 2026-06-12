package com.tiendavideojuegos.tiendavideojuegos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPago;

    @NotBlank(message = "El estado del pago es obligatorio")
    @Size(min = 5, max = 20, message = "El estado del pago debe ser de entre 10 y 20 caracteres")
    @Column(name = "estadoPago", nullable = false)
    private String estadoPago;

    @ManyToOne
    @JoinColumn(name = "id_detalle_pedido", nullable = false)
    private DetallePedido detallePedido;
}
