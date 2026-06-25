package tiendavideojuegos.juegos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tiendavideojuegos.juegos.model.DetalleOfertas;
@Repository
public interface DetalleOfertaRepository extends JpaRepository<DetalleOfertas, Integer> {
   @Query("SELECT d FROM DetalleOfertas d WHERE d.oferta.id = :idOferta ")
   List<DetalleOfertas> buscarOfertas(@Param("idOferta") Integer  idOferta);
}      