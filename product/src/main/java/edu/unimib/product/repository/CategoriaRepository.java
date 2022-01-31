package edu.unimib.product.repository;

import edu.unimib.product.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository
    extends JpaRepository<Categoria, Long>, JpaSpecificationExecutor<Categoria> {
  Categoria findByNome(String nome);
}
