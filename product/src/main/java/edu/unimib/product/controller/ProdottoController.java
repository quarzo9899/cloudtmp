package edu.unimib.product.controller;

import com.google.gson.Gson;
import edu.unimib.product.model.Prodotto;
import edu.unimib.product.repository.ProdottoRepository;
import edu.unimib.product.utils.Decrypt;
import edu.unimib.product.utils.MessageBody;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
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
public class ProdottoController {
  @Autowired private ProdottoRepository prodottoRepository;

  public boolean isTokenValid(String tokenExpireDateString) {
    LocalDateTime tokenExpireDate = LocalDateTime.parse(tokenExpireDateString);
    return tokenExpireDate.isAfter(LocalDateTime.now());
  }

  /**
   * @GetMapping("/prodotti") public List<Prodotto> getProdotti(@RequestHeader("Token") String
   * Token) { String tokenDecrypted = Decrypt.Decrypt(Token); Gson gson = new Gson(); MessageBody
   * msg = gson.fromJson(tokenDecrypted, MessageBody.class); if(!isTokenValid(msg.expire)) { throw
   * new RuntimeException("Token non valido"); } return prodottoRepository.findAll(); }
   */
  @GetMapping("/prodotti")
  public List<Prodotto> getProdotti() {
    return prodottoRepository.findAll();
  }

  @PostMapping("/prodotti")
  public Prodotto createProdotto(
      @Valid @RequestBody Prodotto prodottoRequest, @RequestHeader("Token") String Token) {
    String tokenDecrypted = Decrypt.Decrypt(Token);
    Gson gson = new Gson();
    MessageBody msg = gson.fromJson(tokenDecrypted, MessageBody.class);
    if (!isTokenValid(msg.expire)) throw new RuntimeException("Token non valido");
    if (!msg.isAdmin()) throw new RuntimeException("Utente non admin");
    return prodottoRepository.save(prodottoRequest);
  }

  /**
   * @GetMapping("/prodotti/{prodottoId}") public Prodotto getProdotto(@PathVariable long
   * prodottoId, @RequestHeader("Token") String Token) { String tokenDecrypted =
   * Decrypt.Decrypt(Token); Gson gson = new Gson(); MessageBody msg = gson.fromJson(tokenDecrypted,
   * MessageBody.class); if(!isTokenValid(msg.expire)) throw new RuntimeException("Token non
   * valido");
   *
   * <p>return prodottoRepository.findById(prodottoId).orElseThrow( () -> new
   * RuntimeException("Prodotto non trovato"));
   *
   * <p>}
   */
  @GetMapping("/prodotti/{prodottoId}")
  public Prodotto getProdotto(@PathVariable long prodottoId) {
    return prodottoRepository
        .findById(prodottoId)
        .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));
  }

  @PostMapping("/prodotti/{prodottoId}")
  public Prodotto updateProdotto(
      @PathVariable Long prodottoId,
      @Valid @RequestBody Prodotto prodottoRequest,
      @RequestHeader("Token") String Token) {
    String tokenDecrypted = Decrypt.Decrypt(Token);
    Gson gson = new Gson();
    MessageBody msg = gson.fromJson(tokenDecrypted, MessageBody.class);
    if (!isTokenValid(msg.expire)) throw new RuntimeException("Token non valido");
    if (!msg.isAdmin()) throw new RuntimeException("Utente non admin");

    return prodottoRepository
        .findById(prodottoId)
        .map(
            prodotto -> {
              prodotto.setNome(prodottoRequest.getNome());
              prodotto.setPrezzo(prodottoRequest.getPrezzo());
              prodotto.setQuantita(prodottoRequest.getQuantita());
              prodotto.setImgUrl(prodottoRequest.getImgUrl());
              prodotto.setCategorie(prodottoRequest.getCategorie());
              return prodottoRepository.save(prodotto);
            })
        .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));
  }

  @DeleteMapping("/prodotti/{prodottoId}")
  public ResponseEntity<?> deleteProdotto(
      @PathVariable long prodottoId, @RequestHeader("Token") String Token) {
    String tokenDecrypted = Decrypt.Decrypt(Token);
    Gson gson = new Gson();
    MessageBody msg = gson.fromJson(tokenDecrypted, MessageBody.class);
    if (!isTokenValid(msg.expire)) throw new RuntimeException("Token non valido");
    if (!msg.isAdmin()) throw new RuntimeException("Utente non admin");

    return prodottoRepository
        .findById(prodottoId)
        .map(
            prodotto -> {
              prodottoRepository.delete(prodotto);
              return ResponseEntity.ok().build();
            })
        .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));
  }
}
