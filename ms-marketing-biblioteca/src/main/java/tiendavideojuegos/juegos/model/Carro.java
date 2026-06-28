package tiendavideojuegos.juegos.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "carro")
public class Carro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //reemplace @Size por @Min y @Max
    //porque Size no funciona con Integer.
    @NotNull
    @Min(value = 1, message = "La cantidad minima es 1 unid")
    @Max(value = 100, message = "La cantidad no puede exceder 100 unid")
    private Integer cantidad;

    @NotNull(message = "La fecha es obligatoria")
    @Column(nullable=false,length=10)
    private LocalDate fechaAgregada;



}
