package edu.unimib.user.repository;

import edu.unimib.user.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository
    extends JpaRepository<Utente, Long>, JpaSpecificationExecutor<Utente> {
  Utente findUtenteByMail(String mail);
}
