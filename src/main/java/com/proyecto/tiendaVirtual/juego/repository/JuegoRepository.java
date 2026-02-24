package com.proyecto.tiendaVirtual.juego.repository;

import com.proyecto.tiendaVirtual.juego.model.Categoria;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface JuegoRepository extends JpaRepository<Juego,Long> {
    Optional<Juego> findByNombre(String nombre);

    Page<Juego> findByCategoria(Categoria categoria, Pageable pageable);


    /**
     * Busca juegos cuyo nombre contenga el texto indicado (ignorando mayúsculas/minúsculas)
     * o cuya desarrolladora tenga un nombre que contenga dicho texto.
     *
     * Equivalente aproximado a:
     *
     * SELECT j
     * FROM Juego j
     * WHERE LOWER(j.nombre) LIKE LOWER('%texto%')
     *    OR LOWER(j.desarrolladora.nombre) LIKE LOWER('%texto%')
     *
     * @param nombreJuego texto a buscar dentro del nombre del juego
     * @param nombreDesarrolladora texto a buscar dentro del nombre de la desarrolladora
     * @param pageable información de paginación y orden
     * @return página de juegos que coinciden con alguno de los criterios
     */
    Page<Juego> findByNombreContainingIgnoreCaseOrDesarrolladora_NombreContainingIgnoreCase(
            String nombreJuego,
            String nombreDesarrolladora,
            Pageable pageable
    );


    /**
     * Busca juegos dentro de una categoría específica, cuyo nombre contenga el texto indicado
     * o cuya desarrolladora tenga un nombre que contenga dicho texto (ignorando mayúsculas/minúsculas).
     *
     * IMPORTANTE:
     * Se recibe la categoría dos veces porque Spring Data no permite agrupar condiciones.
     * Esto asegura que la categoría se aplique a ambos lados del OR.
     *
     * Equivalente aproximado a:
     *
     * SELECT j
     * FROM Juego j
     * WHERE (j.categoria = :categoria
     *        AND LOWER(j.nombre) LIKE LOWER('%texto%'))
     *    OR (j.categoria = :categoria
     *        AND LOWER(j.desarrolladora.nombre) LIKE LOWER('%texto%'))
     *
     * @param categoria1 categoría aplicada a la búsqueda por nombre del juego
     * @param nombreJuego texto a buscar dentro del nombre del juego
     * @param categoria2 categoría aplicada a la búsqueda por nombre de la desarrolladora
     * @param nombreDesarrolladora texto a buscar dentro del nombre de la desarrolladora
     * @param pageable información de paginación y orden
     * @return página de juegos que coinciden con la categoría y alguno de los criterios de búsqueda
     */
    Page<Juego> findByCategoriaAndNombreContainingIgnoreCaseOrCategoriaAndDesarrolladora_NombreContainingIgnoreCase(
            Categoria categoria1,
            String nombreJuego,
            Categoria categoria2,
            String nombreDesarrolladora,
            Pageable pageable
    );



    @Query("""
      SELECT COALESCE(AVG(j.precio), 0)
      FROM Juego j
      WHERE j.desarrolladora.id = :devId
    """)
    double precioPromedioDev(Long devId);

    @Query("""
      SELECT j.precio
      FROM Juego j
      WHERE j.desarrolladora.id = :devId
      ORDER BY j.precio ASC
    """)
    List<Double> preciosOrdenadosDev(Long devId);

    @Query("""
      SELECT j.categoria, COUNT(j)
      FROM Juego j
      WHERE j.desarrolladora.id = :devId
      GROUP BY j.categoria
    """)
    List<Object[]> distribucionCategoriasDev(Long devId);
}
