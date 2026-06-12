package tiendavideojuegos.juegos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tiendavideojuegos.juegos.DTO.DetalleOfertaDTO;
import tiendavideojuegos.juegos.model.DetalleOfertas;
import tiendavideojuegos.juegos.repository.DetalleOfertaRepository;
@Transactional
@Service
public class DetalleOfertaService {
    @Autowired
    private DetalleOfertaRepository detalleOfertaRepository;

    public List<DetalleOfertaDTO> obtenerTodos(){
        List<DetalleOfertaDTO>  detalleOfertas = new ArrayList<>();
        for (DetalleOfertas detalleOferta : detalleOfertaRepository.findAll()){
            detalleOfertas.add(convertirADetalleOfertaDTO(detalleOferta));
        }
        return detalleOfertas;
    }

    public DetalleOfertaDTO buscarPorId(Integer idDetalleOferta){
        DetalleOfertas detalleOferta = detalleOfertaRepository.findById(idDetalleOferta).orElseThrow(() -> new RuntimeException("DetalleOferta no encontrado"));
        return convertirADetalleOfertaDTO(detalleOferta);
    }

    public DetalleOfertaDTO guardar(DetalleOfertas detalleOferta){
        DetalleOfertas detalleOfertaGuardado = detalleOfertaRepository.save(detalleOferta);
        return convertirADetalleOfertaDTO(detalleOfertaGuardado);
    }

    private DetalleOfertaDTO convertirADetalleOfertaDTO(DetalleOfertas detalle) {
        DetalleOfertaDTO dto = new DetalleOfertaDTO();
        
        dto.setIdDetalleOferta(detalle.getIdDetalleOferta());

        
        if (detalle.getOferta() != null) {
            dto.setIdOferta(detalle.getOferta().getIdOferta());
        }
        
        if (detalle.getVideoJuego() != null) {
            dto.setIdVideoJuego(detalle.getVideoJuego().getIdVideoJuego());
        }

        return dto;
    }

}
