package tiendavideojuegos.juegos.service;

import org.springframework.stereotype.Service;
import tiendavideojuegos.juegos.DTO.OfertaDTO;
import tiendavideojuegos.juegos.model.Oferta;

@Service
public class OfertaValidaciones {

    public Boolean validarNullVacio(Oferta oferta) {
        if (oferta == null) {
            return false;
        }
        if (oferta.getIdOferta() == null || oferta.getIdOferta() <= 0) {
            return false;
        }
        if (oferta.getDescuento() == null || oferta.getDescuento() < 0) {
            return false;
        }
        if (oferta.getFechaInicio() == null) {
            return false;
        }
        if (oferta.getFechaTermino() == null) {
            return false;
        }
        return true;
    }

    public OfertaDTO convertirADTO(Oferta oferta) {
        if (oferta == null) {
            return null;
        }

        OfertaDTO dto = new OfertaDTO();
        dto.setIdOferta(oferta.getIdOferta());
        dto.setDescuento(oferta.getDescuento());
        dto.setFechaInicio(oferta.getFechaInicio());
        dto.setFechaTermino(oferta.getFechaTermino());

        return dto;
    }
}