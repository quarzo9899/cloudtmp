package edu.unimib.product.repository;

import edu.unimib.product.model.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdottoRepository
    extends JpaRepository<Prodotto, Long>, JpaSpecificationExecutor<Prodotto> {}
