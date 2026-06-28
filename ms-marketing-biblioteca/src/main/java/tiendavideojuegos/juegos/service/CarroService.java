package tiendavideojuegos.juegos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tiendavideojuegos.juegos.model.Carro;
import tiendavideojuegos.juegos.repository.CarritoRepository;

@Service
@Transactional
public class CarroService {
    @Autowired
    private CarritoRepository carroRepository;

    public List<Carro> obtenerTodo() {
        return carroRepository.findAll();
    }

    public Carro buscarPorId(Integer id) {
        return carroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El elemento del carrito con ID " + id + " no existe."));
    }

    public Carro guardar(Carro carro) {
        return carroRepository.save(carro);
    }

    public Carro actualizar(Integer id, Carro carroDetalles) {
        Carro carroExistente = carroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el elemento para actualizar"));
        
        if (carroDetalles.getCantidad() != null) {
            carroExistente.setCantidad(carroDetalles.getCantidad());
        }
        
        if (carroDetalles.getFechaAgregada() != null) {
            carroExistente.setFechaAgregada(carroDetalles.getFechaAgregada());
        }
        
        return carroRepository.save(carroExistente);
    }

    public String eliminar(Integer id) {
        try {
            Carro carro = carroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar: el ID " + id + " no existe."));
            carroRepository.delete(carro);
            return "Producto eliminado del carrito exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public void vaciarCarrito() {
        carroRepository.deleteAll();

    }

}
