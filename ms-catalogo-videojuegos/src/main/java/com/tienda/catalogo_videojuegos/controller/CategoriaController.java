package com.tienda.catalogo_videojuegos.controller;

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
import com.tienda.catalogo_videojuegos.DTO.CategoriaDTO;
import com.tienda.catalogo_videojuegos.model.Categoria;
import com.tienda.catalogo_videojuegos.service.CategoriaService;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<CategoriaDTO> listar(){
        return categoriaService.listarTodos();

    }

    @GetMapping ("/{id}")
    public ResponseEntity<?> buscarPorId (@PathVariable Integer id){
        try {
            CategoriaDTO categoriaDTO = categoriaService.buscarPorId(id);
            return new ResponseEntity<>(categoriaDTO,HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Categoria categoria){
        try {
            Categoria categoriaGuardada = categoriaService.guardar(categoria);
            return new ResponseEntity<>(categoriaGuardada,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar:" + e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar (@PathVariable Integer id, @RequestBody Categoria categoria){
        try {
            CategoriaDTO actualizada = categoriaService.actualizarCategoria(id, categoria);
            return new ResponseEntity<>(actualizada,HttpStatus.OK);
            
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>eliminar (@PathVariable Integer id){
        try {
            String mensajeEliminar = categoriaService.eliminar(id);
            return new ResponseEntity<>(mensajeEliminar,HttpStatus.OK);

        } catch (RuntimeException e) {
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
