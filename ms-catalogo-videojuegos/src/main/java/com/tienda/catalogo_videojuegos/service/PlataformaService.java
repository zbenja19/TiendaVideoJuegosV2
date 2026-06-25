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
        return plataformaRepository.save(plataforma);
    }

    public String eliminar(Integer id) {
        try {
            Plataforma plataforma = plataformaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("¡Error! La plataforma con ID " + id + " no existe."));
            plataformaRepository.delete(plataforma);
            return "La plataforma '" + plataforma.getNombre() + "' ha sido eliminada.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
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
