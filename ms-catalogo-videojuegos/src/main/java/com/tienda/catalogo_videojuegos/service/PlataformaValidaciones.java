package com.tienda.catalogo_videojuegos.service;

import org.springframework.stereotype.Service;
import com.tienda.catalogo_videojuegos.model.Plataforma;

@Service
public class PlataformaValidaciones {

    public boolean validarNullVacio (Plataforma plataforma){
        if (plataforma.getNombre() == null || plataforma.getNombre().trim().isEmpty()){
            return false;
        }
        return true;
    }
}

