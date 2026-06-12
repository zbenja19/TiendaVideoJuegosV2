package tiendavideojuegos.juegos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detalle_ofertas")
public class DetalleOfertas {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idDetalleOferta;

    @ManyToOne
    @JoinColumn(name= "idOferta")
    private Oferta oferta;

}
