package edu.unimib.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity(name = "categoria")
@Table
public class Categoria {

  @Id
  @GeneratedValue(generator = "prodotto_generator")
  @SequenceGenerator(
      name = "prodotto_generator",
      sequenceName = "prodotto_sequence",
      initialValue = 1000)
  private long id;

  @Column(name = "nome")
  @NotNull
  private String nome;

  @ManyToMany(mappedBy = "categorie", cascade = CascadeType.DETACH)
  @JsonIgnore
  private Set<Prodotto> prodotti = new HashSet<Prodotto>();

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Set<Prodotto> getProdotti() {
    return prodotti;
  }

  public void setProdotti(Set<Prodotto> prodotti) {
    this.prodotti = prodotti;
  }
}
