package tiendavideojuegos.juegos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tiendavideojuegos.juegos.model.Oferta;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Integer> {
    @Query("SELECT o FROM Oferta o WHERE o.idOferta = :idOferta")
    Oferta findByIdOferta(@Param("idOferta") Integer idOferta);
}
