package tiendavideojuegos.juegos.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tiendavideojuegos.juegos.model.Carro;


@Repository
public interface CarritoRepository extends JpaRepository<Carro, Integer>{

}
