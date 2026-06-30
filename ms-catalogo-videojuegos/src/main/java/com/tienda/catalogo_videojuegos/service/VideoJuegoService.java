package com.tienda.catalogo_videojuegos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tienda.catalogo_videojuegos.DTO.VideoJuegoDTO;
import com.tienda.catalogo_videojuegos.model.VideoJuego;
import com.tienda.catalogo_videojuegos.repository.VideoJuegoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class VideoJuegoService {

    
    @Autowired
    private VideoJuegoValidaciones videoJuegoValidaciones;
    
    @Autowired
    private VideoJuegoRepository videojuegoRepository;

    // Metodos
    public List <VideoJuegoDTO> listarTodos(){
        return videojuegoRepository.findAll().stream()
                        .map(videoJuegoValidaciones::convertirADTO)
                        .toList();
    }

    public VideoJuegoDTO guardar(VideoJuego videoJuego){
        if(!videoJuegoValidaciones.validarNullVacio(videoJuego)){
            throw new RuntimeException("Debes ingresar el nombre del videojuego");
        }
        videoJuego.setNombre(videoJuego.getNombre().trim());   
        boolean existeVideoJuego = videojuegoRepository.existsByNombreIgnoreCase(videoJuego.getNombre());
        
        if (existeVideoJuego){
            throw new RuntimeException("El Videojuego " + videoJuego.getNombre() + " ya de encuentra registrado.");
            
        }
        
        VideoJuego videoJuegoGuardado = videojuegoRepository.save(videoJuego);
        return videoJuegoValidaciones.convertirADTO(videoJuegoGuardado);
    }

    public String eliminar(Integer id){
        VideoJuego videoJ = videojuegoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("VideoJuego no encontrado."));
    
        videojuegoRepository.delete(videoJ);
        return "Videojuego " + videoJ.getNombre() + " eliminado exitosamente.";
    } 

    public VideoJuegoDTO buscarPorId(Integer id){
        VideoJuego videojuego = videojuegoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Videojuego no encontrado."));
        return videoJuegoValidaciones.convertirADTO(videojuego);
    }

    public List<VideoJuego> buscarPornombre(String nombre){
        List<VideoJuego> juegos = videojuegoRepository.findByNombreContainingIgnoreCase(nombre);

        if(juegos.isEmpty()){
            throw new RuntimeException("No se encontraron videosjuego asociados al nombre" + nombre);
        }
        return juegos;
    }

    public VideoJuegoDTO actualizarVideoJuego(Integer id,VideoJuego nvoVideoJuego){
        VideoJuego videojuegoDTO = videojuegoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("¡El Videojuego no esta registrado!"));
       
        if(nvoVideoJuego.getNombre() != null){
            videojuegoDTO.setNombre(nvoVideoJuego.getNombre().trim());
        }
        if(nvoVideoJuego.getDescripcion() != null){
            videojuegoDTO.setDescripcion(nvoVideoJuego.getDescripcion());
        }
        if(nvoVideoJuego.getPrecio() != null){
            videojuegoDTO.setPrecio(nvoVideoJuego.getPrecio());
        }
        if(nvoVideoJuego.getStock() != null){
            videojuegoDTO.setStock(nvoVideoJuego.getStock());
        }
        if(nvoVideoJuego.getGenero() != null){
            videojuegoDTO.setGenero(nvoVideoJuego.getGenero());
        }
        if(nvoVideoJuego.getCategoria() != null){
            videojuegoDTO.setCategoria(nvoVideoJuego.getCategoria());
        }
        if(nvoVideoJuego.getPlataforma() != null){
            videojuegoDTO.setPlataforma(nvoVideoJuego.getPlataforma());
        }
        if(nvoVideoJuego.getProveedor() != null){
            videojuegoDTO.setProveedor(nvoVideoJuego.getProveedor());
        }
        VideoJuego videoJuegoActualizado = videojuegoRepository.save(videojuegoDTO);
        return videoJuegoValidaciones.convertirADTO(videoJuegoActualizado);
    }

}
