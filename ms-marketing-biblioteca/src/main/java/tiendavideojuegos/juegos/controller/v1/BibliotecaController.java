package tiendavideojuegos.juegos.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tiendavideojuegos.juegos.model.Biblioteca;
import tiendavideojuegos.juegos.service.BibliotecaService;

@RestController("bibliotecaControllerV1")
@RequestMapping("/api/v1/bibliotecas")
public class BibliotecaController {
    @Autowired
    private BibliotecaService bibliotecaService;

    @GetMapping
    public ResponseEntity<List<Biblioteca>> obtenerTodas() {
        List<Biblioteca> bibliotecas = bibliotecaService.obtenerTodas();
        if (bibliotecas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(bibliotecas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Biblioteca> buscarPorId(@PathVariable Integer id) {
        try {
            Biblioteca biblio = bibliotecaService.buscarPorId(id);
            return new ResponseEntity<>(biblio, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Biblioteca> guardar(@Valid@RequestBody Biblioteca biblioteca) {
        try {
            Biblioteca guardada = bibliotecaService.guardar(biblioteca);
            return new ResponseEntity<>(guardada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Biblioteca> actualizar(@PathVariable Integer id, @RequestBody Biblioteca datosNuevos) {
        try {
            Biblioteca actualizada = bibliotecaService.actualizar(id, datosNuevos);
            return new ResponseEntity<>(actualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        String resultado = bibliotecaService.eliminar(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.BAD_REQUEST);
        }
    }
}

