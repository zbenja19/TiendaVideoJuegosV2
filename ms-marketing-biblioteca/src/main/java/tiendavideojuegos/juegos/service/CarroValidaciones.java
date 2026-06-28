package tiendavideojuegos.juegos.service;

import org.springframework.stereotype.Service;

import tiendavideojuegos.juegos.model.Carro;
@Service
public class CarroValidaciones {

    public Boolean validarNullVacio(Carro carro) {
        if (carro == null) {
            return false;
        }
        if (carro.getId() == null || carro.getId() <= 0) {
            return false;
        }
        if (carro.getCantidad() == null || carro.getCantidad() <= 1) {
            return false;
        }
        if (carro.getFechaAgregada() == null) {
            return false;
        }
        return true;
    }

}
