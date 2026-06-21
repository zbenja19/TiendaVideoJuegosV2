package com.tienda.catalogo_videojuegos.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tienda.catalogo_videojuegos.model.Plataforma;
import com.tienda.catalogo_videojuegos.repository.PlataformaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PlataformaService {

    @Autowired
    private PlataformaRepository plataformaRepository;

    public List<Plataforma> obtenerTodas() {
        return plataformaRepository.findAll();
    }

    public Plataforma buscarPorId(Integer id) {
        return plataformaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("¡Plataforma no encontrada!"));
    }

    public Plataforma guardar(Plataforma plataforma) {
        plataforma.setNombre(plataforma.getNombre().trim());   
        boolean existePlataforma = plataformaRepository.existsByNombreIgnoreCase(plataforma.getNombre());
        
        if (existePlataforma){
            throw new RuntimeException("La Plataforma" + plataforma.getNombre() + "ya se encuentra registrada");
            
        }return plataformaRepository.save(plataforma);
    }

    public String eliminar(Integer id) {
        Plataforma plataforma = plataformaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plataforma no encontrado."));
    
        plataformaRepository.delete(plataforma);
        return "Plataforma" + plataforma.getNombre() + "Eliminada exitosamente.";
    }

    public Plataforma actualizarPlataforma(Integer id,Plataforma nvaPlataforma){
        Plataforma plataforma = plataformaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("¡La plataforma no esta registrada!"));
        if(nvaPlataforma.getNombre() != null){
            plataforma.setNombre(nvaPlataforma.getNombre());
        }
        return plataformaRepository.save(plataforma);
    }
}
