package edu.unimib.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import edu.unimib.product.model.Prodotto;

@Repository
public interface ProdottoRepository 
 extends JpaRepository<Prodotto, Long>, JpaSpecificationExecutor<Prodotto> {
}
