package tiendavideojuegos.juegos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import tiendavideojuegos.juegos.DTO.OfertaDTO;
import tiendavideojuegos.juegos.model.Oferta;
import tiendavideojuegos.juegos.repository.OfertaRepository;

public class OfertaService {
    @Autowired
    private OfertaRepository ofertaRepository;

    public List<OfertaDTO> obtenerTodos(){
        List<OfertaDTO> ofertas = new ArrayList<>();
        for (Oferta oferta : ofertaRepository.findAll()){
            ofertas.add(convertirAOfertaDTO(oferta));
        }
        return ofertas;
    }

    public OfertaDTO buscarPorId(Integer idOferta){
        Oferta oferta = ofertaRepository.findById(idOferta).orElseThrow(() -> new RuntimeException("Oferta no encontrada"));
        return convertirAOfertaDTO(oferta);
    }

    public OfertaDTO guardar(Oferta oferta){
        Oferta ofertaGuardada = ofertaRepository.save(oferta);
        return convertirAOfertaDTO(ofertaGuardada);
    }

    private OfertaDTO convertirAOfertaDTO(Oferta oferta){
        OfertaDTO dto = new OfertaDTO();
        dto.setIdOferta(oferta.getIdOferta());
        dto.setDescuento(oferta.getDescuento());
        dto.setFechaInicio(oferta.getFechaInicio());
        dto.setFechaTermino(oferta.getFechaTermino());
        
        if (oferta.getVideoJuego() != null){
            dto.setIdVideoJuego(oferta.getVideoJuego().getIdVideoJuego());
        }
        return dto;
    }

}
