package com.tienda.catalogo_videojuegos.service;

import org.springframework.stereotype.Service;

import com.tienda.catalogo_videojuegos.DTO.PlataformaDTO;
import com.tienda.catalogo_videojuegos.model.Plataforma;

@Service
public class PlataformaValidaciones {

    public boolean validarNullVacio (Plataforma plataforma){
        if (plataforma.getNombre() == null || plataforma.getNombre().trim().isEmpty()){
            return false;
        }
        return true;
    }

    public PlataformaDTO convertirADTO (Plataforma plataforma) {
        PlataformaDTO plataformaDTO = new PlataformaDTO();
        
        plataformaDTO.setIdPlataforma(plataforma.getId());
        plataformaDTO.setNombre(plataforma.getNombre());

        return plataformaDTO;
    }

}
