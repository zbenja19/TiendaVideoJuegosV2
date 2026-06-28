package com.tiendavideojuegos.tiendavideojuegos.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pedidos")
public class Pedidos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "La fecha del pedido es obligatoria")
    @Column(name = "fecha_agregada", nullable = false)
    private LocalDate fechaAgregada;

    @NotNull(message = "El monto total del pedido es obligatorio")
    @Min(value = 0, message = "El monto total del pedido debe ser mayor a 0")
    @Max(value = 1000000)
    @Column(name = "monto_total", nullable = false)
    private Double montoTotal;

    @NotNull(message = "El estado es obligatorio")
    @Column(nullable = false)
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Clientes cliente;

    @OneToMany(mappedBy = "pedido")
    private List<DetallePedido> detallesPedido;

}
