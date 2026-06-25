package com.tienda.catalogo_videojuegos.service;

import org.springframework.stereotype.Service;

import com.tienda.catalogo_videojuegos.DTO.CategoriaDTO;
import com.tienda.catalogo_videojuegos.model.Categoria;



@Service
public class CategoriaValidaciones {

    public Boolean validarNullVacio (Categoria categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()){
            return false;
        }
        return true;
    }

    public CategoriaDTO convertirADTO (Categoria categoria) {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        
        categoriaDTO.setIdCategoria(categoria.getIdCategoria());
        categoriaDTO.setNombre(categoria.getNombre());
        categoriaDTO.setDescripcion(categoria.getDescripcion());

        return categoriaDTO;
    }

}
