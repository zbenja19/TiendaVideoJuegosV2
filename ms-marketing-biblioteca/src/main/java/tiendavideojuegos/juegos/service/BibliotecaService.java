package tiendavideojuegos.juegos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tiendavideojuegos.juegos.model.Biblioteca;
import tiendavideojuegos.juegos.repository.BibliotecaRepository;

@Service
@Transactional
public class BibliotecaService {
    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    public List<Biblioteca> obtenerTodas() {
        return bibliotecaRepository.findAll();
    }

    public Biblioteca buscarPorId(Integer id) {
        return bibliotecaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("La biblioteca con el Id " + id + " no existe mi amigo"));
    }

    public Biblioteca guardar(Biblioteca biblioteca) {
        return bibliotecaRepository.save(biblioteca);
    }

    public String eliminar(Integer id) {
        try {
            Biblioteca biblio = bibliotecaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("La biblioteca con el Id " + id + " no existe mi amigo."));
            
            if (biblio.getVideojuegos() != null && !biblio.getVideojuegos().isEmpty()) {
                return "No se puede eliminar la biblioteca Id " + id + " porque tiene juegos asociados :).";
            }

            bibliotecaRepository.delete(biblio);
            return "Biblioteca eliminada exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public Biblioteca actualizar(Integer id, Biblioteca datosNuevos) {
        Biblioteca biblioExistente = bibliotecaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Biblioteca no encontrada para actualizar."));

        if (datosNuevos.getFechaAgregada() != null) {
            biblioExistente.setFechaAgregada(datosNuevos.getFechaAgregada());
        }

        return bibliotecaRepository.save(biblioExistente);
    }
}


