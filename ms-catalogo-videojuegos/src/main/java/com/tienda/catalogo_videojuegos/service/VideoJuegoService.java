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
    private VideoJuegoRepository videojuegoRepository;

    // Metodos
    public List <VideoJuegoDTO> listarTodos(){
        return videojuegoRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public VideoJuego guardar(VideoJuego videoJuego){
        return videojuegoRepository.save(videoJuego);       
    }

    public String eliminar(Integer id){
        VideoJuego videoJ = videojuegoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("VideoJuego no encontrado"));
    
        videojuegoRepository.delete(videoJ);
        return "Videojuego"+  videoJ.getNombre()+"Eliminado exitosamente";
    } 

    public VideoJuegoDTO buscarPorId(Integer id){
        VideoJuego videojuego = videojuegoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Videojuego no encontrado"));
        return convertirADTO(videojuego);
    }

    public List<VideoJuego> buscarPornombre(String nombre){
        List<VideoJuego> juegos = videojuegoRepository.findByNombreContaining(nombre);

        if(juegos.isEmpty()){
            throw new RuntimeException("No se encontraron videosjuego asociados al nombre"+ nombre);
        }
        return juegos;
    }

    public VideoJuegoDTO actualizarVideoJuego(Integer id,VideoJuego nvoVideoJuego){
        VideoJuego videojuegoDTO = videojuegoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("¡El Videojuego no esta registrado!"));
       
        if(nvoVideoJuego.getNombre() != null){
            videojuegoDTO.setNombre(nvoVideoJuego.getNombre());
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
        return convertirADTO(videoJuegoActualizado);
    }

    private VideoJuegoDTO convertirADTO(VideoJuego videoJuego) {
        VideoJuegoDTO videoJuegoDTO = new VideoJuegoDTO();
        
        videoJuegoDTO.setIdVideoJuego(videoJuego.getIdVideoJuego());
        videoJuegoDTO.setNombre(videoJuego.getNombre());
        videoJuegoDTO.setDescripcion(videoJuego.getDescripcion());
        videoJuegoDTO.setPrecio(videoJuego.getPrecio());
        videoJuegoDTO.setStock(videoJuego.getStock());
        videoJuegoDTO.setGenero(videoJuego.getGenero());
        

        if (videoJuego.getCategoria() != null) {
            videoJuegoDTO.setNombreCategoria(videoJuego.getCategoria().getNombre());
        } else {
            videoJuegoDTO.setNombreCategoria(null);
        }
        if(videoJuego.getPlataforma() != null){
            videoJuegoDTO.setNombrePlataforma(videoJuego.getPlataforma().getNombre());
        }
        if(videoJuego.getProveedor() != null){
            videoJuegoDTO.setNombreProveedor(videoJuego.getProveedor().getNombre());
        }
        return videoJuegoDTO;
    }

}
