package tiendavideojuegos.juegos.service;

import org.springframework.stereotype.Service;
import tiendavideojuegos.juegos.model.Biblioteca;

@Service
public class BibliotecaValidaciones {

    public Boolean validarNullVacio(Biblioteca biblioteca) {
        if (biblioteca == null) {
            return false;
        }
        if (biblioteca.getId() == null || biblioteca.getId() <= 0) {
            return false;
        }
        if (biblioteca.getFechaAgregada() == null) {
            return false;
        }
        return true;
    }

}
