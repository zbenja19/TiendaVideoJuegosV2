package com.tienda.catalogo_videojuegos.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.catalogo_videojuegos.DTO.CategoriaDTO;
import com.tienda.catalogo_videojuegos.model.Categoria;
import com.tienda.catalogo_videojuegos.repository.CategoriaRepository;


import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoriaService {

    
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaValidaciones categoriaValidaciones;

    //METODOS
public List <CategoriaDTO> listarTodos(){
        return categoriaRepository.findAll().stream()
        .map(categoriaValidaciones::convertirADTO)
        .toList();
    }

    public CategoriaDTO guardar(Categoria categoria){
        if(!categoriaValidaciones.validarNullVacio(categoria)){
            throw new RuntimeException("Debes ingresar el nombre de la categoria");
        }
        categoria.setNombre(categoria.getNombre().trim());   
        boolean existeCategoria = categoriaRepository.existsByNombreIgnoreCase(categoria.getNombre());
        
        if (existeCategoria){
            throw new RuntimeException("La categoria " + categoria.getNombre() + " ya se encuentra registrada");
            
        }

        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        
        return categoriaValidaciones.convertirADTO(categoriaGuardada);
    }

    public String eliminar(Integer id){
        Categoria categoria = categoriaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
    
        categoriaRepository.delete(categoria);
        return "Categoria" + categoria.getNombre() + "eliminada exitosamente.";

    }

    public CategoriaDTO buscarPorId(Integer id){
        Categoria categoria = categoriaRepository.findById(id)
           .orElseThrow(() -> new RuntimeException("¡La categoria no esta registrada!"));
        return categoriaValidaciones.convertirADTO(categoria);
    }

    public CategoriaDTO actualizarCategoria(Integer id,Categoria categoriaNva){
        Categoria categoria = categoriaRepository.findById(id)
         .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
         if(categoriaNva.getNombre() != null){
            categoria.setNombre(categoriaNva.getNombre().trim());
         }
         if (categoriaNva.getDescripcion() != null){
            categoria.setDescripcion(categoriaNva.getDescripcion());
         }
         Categoria categoriaActualizada = categoriaRepository.save(categoria);
         return categoriaValidaciones.convertirADTO(categoriaActualizada);
    }   
}
