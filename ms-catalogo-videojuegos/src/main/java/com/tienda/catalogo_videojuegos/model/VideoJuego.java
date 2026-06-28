package com.tienda.catalogo_videojuegos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="videojuego")
public class VideoJuego {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idVideoJuego;

    @NotBlank (message="El nombre del VideoJuego es obligatorio")
    @Size (min= 3, max= 100, message= "El nombre debe tener entre 3 y 100 caracteres")
    @Column (name= "nombreVideoJuego", nullable= false, length= 100)
    private String nombre;
    
    @Column(name= "descripcion")
    private String descripcion;

    @NotNull(message= "El precio no puede quedar vacio")
    @Min(value= 1, message= "El precio no puede ser menor a 1")
    @Column(name= "precio", nullable=false)
    private Double precio;

    @NotNull(message="El stock no puede quedar vacio")
    @Min(value= 0, message= "El stock no puede ser negativo")
    @Column(name="stock", nullable=false)
    private Integer stock;

    @Column(name = "genero" )
    private String genero;
    
    @ManyToOne // se explica como muchos juegos pertenecen a una categoria y una categoria pertenece a muchos juegos
    @JoinColumn(name="idCategoria") //Asignas el nombre a la columna
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name="idPlataforma")
    private Plataforma plataforma;

    @ManyToOne
    @JoinColumn(name="idProveedor")
    private Proveedor proveedor;

    /*@ManyToOne
    @JoinColumn(name="idBiblioteca")
    private Biblioteca biblioteca;
    */

}
