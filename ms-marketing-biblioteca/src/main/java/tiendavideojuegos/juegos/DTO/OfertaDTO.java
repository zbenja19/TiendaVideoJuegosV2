package tiendavideojuegos.juegos.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class OfertaDTO {
    
    private Integer idOferta;
    private Double descuento;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
    private Integer idVideoJuego;

}
