package edu.unimib.order.repository;

import edu.unimib.order.model.Ordine;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdineRepository
    extends JpaRepository<Ordine, Long>, JpaSpecificationExecutor<Ordine> {
  List<Ordine> findOrdiniByUtenteId(long utenteId);
}
