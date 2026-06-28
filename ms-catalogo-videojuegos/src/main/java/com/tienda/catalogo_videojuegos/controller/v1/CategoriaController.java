package com.tienda.catalogo_videojuegos.controller.v1;

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
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController("categoriaControllerV1")
@RequestMapping("/api/v1/categorias")
@Tag(name = "Categorias",description = "Operaciones para la gestion de categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    @Operation(summary="Obtiene una lista" , description="Retorna una lista de todas las categorias")
    @ApiResponse(responseCode="200", description= "Lista de categorias obtenida correctamente")
    @ApiResponse(responseCode="204", description= "No existen categorias registradas")
    public ResponseEntity <List<CategoriaDTO>> listar(){
        List<CategoriaDTO> categorias = categoriaService.listarTodos();
        if (categorias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categorias, HttpStatus.OK);

    }

    @GetMapping ("/{id}")
    @Operation(summary = "Buscar categoria por id", description = "Retorna una categoria segun su id")
    @ApiResponse(responseCode = "200", description = "Categoria encontrada")
    @ApiResponse(responseCode = "404", description = "Categoria no encontrada")
    public ResponseEntity<?> buscarPorId (@PathVariable Integer id){
        try {
            CategoriaDTO categoriaDTO = categoriaService.buscarPorId(id);
            return new ResponseEntity<>(categoriaDTO,HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear categoria", description = "Crea una nueva categoria")
    @ApiResponse(responseCode = "201", description = "Categoria creada correctamente")
    @ApiResponse(responseCode = "400", description = "Datos invalidos")
    public ResponseEntity<?> guardar(@Valid @RequestBody Categoria categoria){
        try {
            CategoriaDTO categoriaGuardada = categoriaService.guardar(categoria);
            return new ResponseEntity<>(categoriaGuardada,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar:" + e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza categoria", description = "Actualiza la categoria existente")
    @ApiResponse(responseCode = "200", description = "Actualizada exitosamente")
    @ApiResponse(responseCode = "404", description = "Categoria no encontrada")
    public ResponseEntity<?> actualizar (@PathVariable Integer id,@Valid @RequestBody Categoria categoria){
        try {
            CategoriaDTO actualizada = categoriaService.actualizarCategoria(id, categoria);
            return new ResponseEntity<>(actualizada,HttpStatus.OK);
            
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina categoria", description = "Elimina la categoria por id")
    @ApiResponse(responseCode = "200", description = "Categoria eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "Categoria no encontrada")
    public ResponseEntity<?> eliminar (@PathVariable Integer id){
        try {
            String mensajeEliminar = categoriaService.eliminar(id);
            return new ResponseEntity<>(mensajeEliminar,HttpStatus.OK);

        } catch (RuntimeException e) {
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
