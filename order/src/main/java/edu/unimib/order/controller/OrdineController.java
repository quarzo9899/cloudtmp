package edu.unimib.order.controller;

import com.google.gson.Gson;
import edu.unimib.order.model.Ordine;
import edu.unimib.order.repository.OrdineRepository;
import edu.unimib.order.utils.Decrypt;
import edu.unimib.order.utils.MessageBody;
import edu.unimib.order.utils.Prodotto;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdineController {
  @Autowired private OrdineRepository ordineRepository;

  public boolean isTokenValid(String tokenExpireDateString) {
    LocalDateTime tokenExpireDate = LocalDateTime.parse(tokenExpireDateString);
    return tokenExpireDate.isAfter(LocalDateTime.now());
  }

  @GetMapping("/ordini")
  public List<Ordine> getOrdini(@RequestHeader("Token") String Token) {
    String tokenDecrypted = Decrypt.Decrypt(Token);
    Gson gson = new Gson();
    MessageBody msg = gson.fromJson(tokenDecrypted, MessageBody.class);
    if (!isTokenValid(msg.expire)) {
      throw new RuntimeException("Token non valido");
    }
    if (msg.isAdmin()) return ordineRepository.findAll();

    return ordineRepository.findOrdiniByUtenteId(msg.getId());
  }

  @PostMapping("/ordini")
  public List<Ordine> postOrdine(
      @Valid @RequestBody Ordine ordineRequest, @RequestHeader("Token") String Token) {
    String tokenDecrypted = Decrypt.Decrypt(Token);
    Gson gson = new Gson();
    MessageBody msg = gson.fromJson(tokenDecrypted, MessageBody.class);
    if (!isTokenValid(msg.expire)) throw new RuntimeException("Token non valido");

    if (ordineRequest.getProdottiId().size() == 0)
      throw new RuntimeException("Nessun prodotto richiesto!!!");

    if (!isProdottiAvailables(ordineRequest.getProdottiId()))
      throw new RuntimeException("Uno o pi� prodotti non sono validi!!!");

    ordineRequest.setId(msg.getId());
    ordineRequest.setDataOrdine(new Date());
    ordineRequest.setStato("Ordine in elaborazione");

    return ordineRepository.findAll();
  }

  private boolean isProdottiAvailables(List<Integer> prodottiId) {
    Set<Integer> uniqueProdottiId = new HashSet<Integer>(prodottiId);
    for (Integer prodottoId : uniqueProdottiId) {
      int occurrences = Collections.frequency(prodottiId, prodottoId);
      if (!isProdottoAvailable(prodottoId, occurrences)) return false;
    }
    return true;
  }

  private boolean isProdottoAvailable(int prodottoId, int quantita) {
    try {

      String productServiceUrl = System.getenv("product_service_url");

      HttpClient client = HttpClients.custom().build();
      HttpUriRequest request =
          RequestBuilder.get().setUri(productServiceUrl + "/prodotti/" + prodottoId).build();
      HttpResponse response = client.execute(request);
      String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
      System.out.println("Response body: " + responseBody);
      Prodotto prod = new Gson().fromJson(responseBody, Prodotto.class);
      if (prod.getQuantita() < quantita) return false;
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
    return true;
  }

  @GetMapping("/ordini/{ordineId}")
  public Ordine getOrdine(@PathVariable long ordineId, @RequestHeader("Token") String Token) {
    String tokenDecrypted = Decrypt.Decrypt(Token);
    Gson gson = new Gson();
    MessageBody msg = gson.fromJson(tokenDecrypted, MessageBody.class);
    if (!isTokenValid(msg.expire)) throw new RuntimeException("Token non valido");

    Ordine ordine = ordineRepository.findById(ordineId).get();

    if (ordine == null) throw new RuntimeException("Ordine non valido");

    if (!msg.isAdmin() && msg.getId() != ordine.getUtenteId())
      throw new RuntimeException("Utente non autorizzato");

    return ordine;
  }

  @PostMapping("/ordini/{ordineId}")
  public Ordine updateOrdine(
      @PathVariable long ordineId,
      @Valid @RequestBody Ordine ordineRequest,
      @RequestHeader("Token") String Token) {
    String tokenDecrypted = Decrypt.Decrypt(Token);
    Gson gson = new Gson();
    MessageBody msg = gson.fromJson(tokenDecrypted, MessageBody.class);
    if (!isTokenValid(msg.expire)) throw new RuntimeException("Token non valido");
    if (!msg.isAdmin()) throw new RuntimeException("Utente non autorizzato");

    return ordineRepository
        .findById(ordineId)
        .map(
            ordine -> {
              ordine.setCosto(ordineRequest.getCosto());
              ordine.setDataOrdine(ordineRequest.getDataOrdine());
              ordine.setPagamento(ordineRequest.getPagamento());
              ordine.setProdottiId(ordineRequest.getProdottiId());
              ordine.setStato(ordineRequest.getStato());
              ordine.setUtenteId(ordineRequest.getUtenteId());
              ordine.setIndirizzo(ordineRequest.getIndirizzo());
              return ordineRepository.save(ordine);
            })
        .orElseThrow(() -> new RuntimeException("Ordine non trovato"));
  }

  @DeleteMapping("/ordini/{ordineId}")
  public ResponseEntity<?> deleteOrdine(
      @PathVariable long ordineId, @RequestHeader("Token") String Token) {
    String tokenDecrypted = Decrypt.Decrypt(Token);
    Gson gson = new Gson();
    MessageBody msg = gson.fromJson(tokenDecrypted, MessageBody.class);
    if (!isTokenValid(msg.expire)) throw new RuntimeException("Token non valido");
    if (!msg.isAdmin()) throw new RuntimeException("Utente non autorizzato");

    return ordineRepository
        .findById(ordineId)
        .map(
            ordine -> {
              ordineRepository.delete(ordine);
              return ResponseEntity.ok().build();
            })
        .orElseThrow(() -> new RuntimeException("Ordine non trovato"));
  }
}
