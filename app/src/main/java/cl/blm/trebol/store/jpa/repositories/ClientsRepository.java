package cl.blm.trebol.store.jpa.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Predicate;

import cl.blm.trebol.store.jpa.GenericRepository;
import cl.blm.trebol.store.jpa.entities.Client;

/**
 *
 * @author Benjamin La Madrid <bg.lamadrid at gmail.com>
 */
@Repository
public interface ClientsRepository
    extends GenericRepository<Client, Integer> {

  @Query(value = "SELECT c FROM Client c JOIN FETCH c.person", countQuery = "SELECT c FROM Client c")
  Page<Client> deepReadAll(Pageable pageable);

  @Query(value = "SELECT c FROM Client c JOIN FETCH c.person", countQuery = "SELECT c FROM Client c")
  Page<Client> deepReadAll(Predicate filters, Pageable pageable);
}