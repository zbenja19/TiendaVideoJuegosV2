package com.tienda.catalogo_videojuegos.model;

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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idCategoria;

    @NotBlank(message="El nombre de la categoria es obligatoria")
    @Size(min=2,max=50)
    @Column(nullable=false,length=60)
    private String nombre;

    @Column(length=200)
    private String descripcion;

    @OneToMany(mappedBy="categoria")// Cree esta para que una categoria pueda tener muchos juegos
    private List<VideoJuego> videojuegos;

}
