package tiendavideojuegos.juegos.service;

import org.springframework.stereotype.Service;
import tiendavideojuegos.juegos.DTO.DetalleOfertaDTO;
import tiendavideojuegos.juegos.model.DetalleOfertas;

@Service
public class DetalleOfertaValidaciones {

    public Boolean validarNullVacio(DetalleOfertas detalleOferta) {
        if (detalleOferta == null) {
            return false;
        }
        if (detalleOferta.getIdDetalleOferta() == null || detalleOferta.getIdDetalleOferta() <= 0) {
            return false;
        }
        if (detalleOferta.getOferta() == null || detalleOferta.getOferta().getIdOferta() == null) {
            return false;
        }
        return true;
    }

    public DetalleOfertaDTO convertirADTO(DetalleOfertas detalle) {
        if (detalle == null) {
            return null;
        }
        
        DetalleOfertaDTO dto = new DetalleOfertaDTO();
        dto.setIdDetalleOferta(detalle.getIdDetalleOferta());

        if (detalle.getOferta() != null) {
            dto.setIdOferta(detalle.getOferta().getIdOferta());
        }

        return dto;
    }
}
