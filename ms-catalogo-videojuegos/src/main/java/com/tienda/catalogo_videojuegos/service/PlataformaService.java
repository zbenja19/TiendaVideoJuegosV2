package com.tienda.catalogo_videojuegos.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tienda.catalogo_videojuegos.DTO.PlataformaDTO;
import com.tienda.catalogo_videojuegos.model.Categoria;
import com.tienda.catalogo_videojuegos.model.Plataforma;
import com.tienda.catalogo_videojuegos.repository.PlataformaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PlataformaService {

    @Autowired
    private PlataformaRepository plataformaRepository;

    @Autowired
    private PlataformaValidaciones plataformaValidaciones;

    public List<PlataformaDTO> obtenerTodas() {
        return plataformaRepository.findAll().stream()
        .map(plataformaValidaciones::convertirADTO)
        .toList();
    }

    public PlataformaDTO buscarPorId(Integer id) {
        Plataforma plataforma = plataformaRepository.findById(id)
           .orElseThrow(() -> new RuntimeException("Plataforma no encontrada"));

        return plataformaValidaciones.convertirADTO(plataforma);
    }

    public PlataformaDTO guardar(Plataforma plataforma) {
         if(!plataformaValidaciones.validarNullVacio(plataforma)){
            throw new RuntimeException("Debes ingresar el nombre de la categoria");
        }
        plataforma.setNombre(plataforma.getNombre().trim());   
        boolean existePlataforma = plataformaRepository.existsByNombreIgnoreCase(plataforma.getNombre());
        
        if (existePlataforma){
            throw new RuntimeException("La Plataforma " + plataforma.getNombre() + " ya se encuentra registrada");
            
        }
        Plataforma plataformaGuardada = plataformaRepository.save(plataforma);

        return plataformaValidaciones.convertirADTO(plataformaGuardada);
    }

    public String eliminar(Integer id) {
        Plataforma plataforma = plataformaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plataforma no encontrado."));
    
        plataformaRepository.delete(plataforma);
        return "La plataforma " + plataforma.getNombre() + " ha sido eliminada.";
    }

    public PlataformaDTO actualizarPlataforma(Integer idPlataforma,Plataforma nvaPlataforma){
        Plataforma plataforma = plataformaRepository.findById(idPlataforma)
        .orElseThrow(() -> new RuntimeException("Plataforma no encontrada"));
        if(nvaPlataforma.getNombre() != null){
            plataforma.setNombre(nvaPlataforma.getNombre().trim());
        }
        Plataforma plataformaActualizada = plataformaRepository.save(plataforma);
        return plataformaValidaciones.convertirADTO(plataformaActualizada);
    }

    
}

    

