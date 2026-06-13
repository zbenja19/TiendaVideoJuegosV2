package com.tiendavideojuegos.tiendavideojuegos.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class Clientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre del cliente debe tener entre 3 y 50 caracteres")
    @Column(name = "cliente", nullable = false)
    private String nombre;

    @NotBlank(message = "El correo electrónico del cliente es obligatorio")
    @Size(min = 10, max = 100, message = "El correo electrónico del cliente debe tener entre 10 y 100 caracteres")
    @Column(name = "correo", nullable = false)
    private String correo;

    @NotBlank(message = "El teléfono del cliente es obligatorio")
    @Size(min = 7, max = 15, message = "El teléfono del cliente debe tener entre 7 y 15 caracteres")
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @NotBlank(message = "La contraseña del cliente es obligatoria")
    @Size(min = 8, max = 15, message = "La contraseña del cliente debe tener entre 8 y 15 caracteres")
    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @OneToMany(mappedBy = "cliente")
    private List<Pedidos> pedidos;
}
