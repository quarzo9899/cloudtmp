package edu.unimib.user.controller;

import com.google.gson.Gson;
import edu.unimib.user.model.Utente;
import edu.unimib.user.repository.UtenteRepository;
import edu.unimib.user.utils.Crypt;
import edu.unimib.user.utils.Hash;
import edu.unimib.user.utils.MessageBody;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtenteController {

  @Autowired private UtenteRepository utenteRepository;

  @PostMapping("/register")
  public String createUtente(@Valid @RequestBody Utente utenteRequest) {
    utenteRequest.setPassword(Hash.sha256(utenteRequest.getPassword()));
    if (utenteRequest.isAdmin()) utenteRequest.setAdmin(false);

    /**
     * MailCheckerAPIResponse response =
     * MailCheckerAPI.makeAbstractRequest(utenteRequest.getMail());
     * if(!response.is_valid_format.value || response.is_disposable_email.value) throw new
     * RuntimeException("Error mail not valid");
     */
    Utente utente = utenteRepository.save(utenteRequest);

    Instant expireValue = Instant.now().plus(8, ChronoUnit.HOURS);
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

    MessageBody msg = new MessageBody(utente.getId(), utente.isAdmin(), now.toString());
    Gson gson = new Gson();
    String json = gson.toJson(msg);
    return Crypt.crypt(json);
  }

  @PostMapping("/login")
  public String Login(@Valid @RequestBody Utente utenteRequest) {
    Utente utente = utenteRepository.findUtenteByMail(utenteRequest.getMail());
    if (utente == null || !utente.getPassword().equals(Hash.sha256(utenteRequest.getPassword())))
      throw new RuntimeException("Wrong password or email!!");

    LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

    MessageBody msg = new MessageBody(utente.getId(), utente.isAdmin(), now.toString());
    Gson gson = new Gson();
    String json = gson.toJson(msg);
    return Crypt.crypt(json);
  }

  /**
   * DA CANCELLARE NON MANDARE IN 'PRODUZIONE' @GetMapping("/login") public List<Utente> getUtenti()
   * { return utenteRepository.findAll(); }
   */
}
