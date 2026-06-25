package com.tienda.catalogo_videojuegos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idProveedor;

    @NotBlank(message="El nombre del proveedor es obligatorio")
    @Size(min=2,max=50)
    @Column(name="proveedor", nullable=false)
    private String nombre;

    @NotBlank(message="El email es obligatoria")
    @Size(min=2,max=50)
    @Column(nullable=false,length=60)
    private String email;

    @Column(length=20)
    private String telefono;

}
