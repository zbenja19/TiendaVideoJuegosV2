package com.tienda.catalogo_videojuegos.service;

import org.springframework.stereotype.Service;

import com.tienda.catalogo_videojuegos.DTO.VideoJuegoDTO;
import com.tienda.catalogo_videojuegos.model.VideoJuego;

@Service
public class VideoJuegoValidaciones {


    public Boolean validarNullVacio(VideoJuego videoJuego) {
        if (videoJuego.getNombre() == null || videoJuego.getNombre().trim().isEmpty()){
            return false;
        }
        return true;
    }

    public void validarStock(Integer stock){

        if (stock == null){
            throw new RuntimeException("El Stock no puede ser nulo");
        }
        if(stock < 0){
            throw new RuntimeException("El Stock no puede ser negativo");

        }
    }

    public void validaStockMaximo (Integer stock){

        if (stock > 10000){
            throw new RuntimeException("El Stock no puede exceder las 10.000 unidades");
        }
    }

    public void validarPrecioMinimo(Double precio){

        if (precio == null){
            throw new RuntimeException("El precio no puede ser null");
        }
        if (precio <= 0){
            throw new RuntimeException("El precio debe ser mayor a 0");
        }

    }

    public VideoJuegoDTO convertirADTO(VideoJuego videoJuego) {
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
