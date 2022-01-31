package edu.unimib.product.controller;

import com.google.gson.Gson;
import edu.unimib.product.model.Categoria;
import edu.unimib.product.model.Prodotto;
import edu.unimib.product.repository.CategoriaRepository;
import edu.unimib.product.utils.Decrypt;
import edu.unimib.product.utils.MessageBody;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
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
public class CategoriaController {
  @Autowired private CategoriaRepository categoriaRepository;

  public boolean isTokenValid(String tokenExpireDateString) {
    LocalDateTime tokenExpireDate = LocalDateTime.parse(tokenExpireDateString);
    return tokenExpireDate.isAfter(LocalDateTime.now());
  }

  /**
   * @GetMapping("/categorie") public String getCategorie(@RequestHeader("Token") String Token) {
   * String tokenDecrypted = Decrypt.Decrypt(Token); Gson gson = new Gson(); MessageBody msg =
   * gson.fromJson(tokenDecrypted, MessageBody.class); if(!isTokenValid(msg.expire)) { throw new
   * RuntimeException("Token non valido"); } List<Categoria> categorie =
   * categoriaRepository.findAll(); List<String> categorieNome = new ArrayList<String>();
   * for(Categoria categoria:categorie) { categorieNome.add(categoria.getNome()); } return
   * gson.toJson(categorieNome); }*
   */

  /**
   * @GetMapping("/categorie") public List<Categoria> getCategorie() { String tokenDecrypted =
   * Decrypt.Decrypt(Token); Gson gson = new Gson(); MessageBody msg = gson.fromJson(tokenDecrypted,
   * MessageBody.class); if(!isTokenValid(msg.expire)) { throw new RuntimeException("Token non
   * valido"); } return categoriaRepository.findAll(); }
   */
  @GetMapping("/categorie")
  public List<Categoria> getCategorie() {
    return categoriaRepository.findAll();
  }

  @PostMapping("/categorie")
  public Categoria createCategorie(
      @Valid @RequestBody Categoria categoriaRequest, @RequestHeader("Token") String Token) {
    String tokenDecrypted = Decrypt.Decrypt(Token);
    Gson gson = new Gson();
    MessageBody msg = gson.fromJson(tokenDecrypted, MessageBody.class);
    if (!isTokenValid(msg.expire)) {
      throw new RuntimeException("Token non valido");
    }
    if (!msg.isAdmin()) throw new RuntimeException("Utente non admin");
    return categoriaRepository.save(categoriaRequest);
  }

  /**
   * @GetMapping("/categorie/{categoriaName}") public Set<Prodotto>
   * getProdottiByCategoria(@PathVariable String categoriaName, @RequestHeader("Token") String
   * Token) { String tokenDecrypted = Decrypt.Decrypt(Token); Gson gson = new Gson(); MessageBody
   * msg = gson.fromJson(tokenDecrypted, MessageBody.class); if(!isTokenValid(msg.expire)) throw new
   * RuntimeException("Token non valido");
   *
   * <p>Categoria categoria = categoriaRepository.findByNome(categoriaName); if(categoria == null)
   * throw new RuntimeException("Categoria non trovata"); return categoria.getProdotti(); }
   */
  @GetMapping("/categorie/{categoriaName}")
  public Set<Prodotto> getProdottiByCategoria(@PathVariable String categoriaName) {
    Categoria categoria = categoriaRepository.findByNome(categoriaName);
    if (categoria == null) throw new RuntimeException("Categoria non trovata");
    return categoria.getProdotti();
  }

  @PostMapping("/categorie/{categoriaName}")
  public Categoria updateCategoria(
      @PathVariable String categoriaName,
      @RequestHeader("Token") String Token,
      @Valid @RequestBody Categoria categoriaRequest) {
    String tokenDecrypted = Decrypt.Decrypt(Token);
    Gson gson = new Gson();
    MessageBody msg = gson.fromJson(tokenDecrypted, MessageBody.class);
    if (!isTokenValid(msg.expire)) throw new RuntimeException("Token non valido");
    if (!msg.isAdmin()) throw new RuntimeException("Utente non admin");

    Categoria categoria = categoriaRepository.findByNome(categoriaName);
    if (categoria == null) throw new RuntimeException("Categoria non trovata");
    categoria.setNome(categoriaRequest.getNome());
    categoria.setProdotti(categoriaRequest.getProdotti());
    return categoriaRepository.save(categoria);
  }

  @DeleteMapping("/categorie/{categoriaName}")
  public ResponseEntity<?> deleteCategoria(
      @PathVariable String categoriaName,
      @RequestHeader("Token") String Token,
      @Valid @RequestBody Categoria categoriaRequest) {
    String tokenDecrypted = Decrypt.Decrypt(Token);
    Gson gson = new Gson();
    MessageBody msg = gson.fromJson(tokenDecrypted, MessageBody.class);
    if (!isTokenValid(msg.expire)) throw new RuntimeException("Token non valido");
    if (!msg.isAdmin()) throw new RuntimeException("Utente non admin");
    Categoria categoria = categoriaRepository.findByNome(categoriaName);
    if (categoria == null) throw new RuntimeException("Categoria non trovata");
    categoriaRepository.delete(categoria);
    return ResponseEntity.ok().build();
  }
}
