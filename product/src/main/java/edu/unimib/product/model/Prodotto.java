package edu.unimib.product.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity(name = "prodotto")
@Table
public class Prodotto {
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

  @Column(name = "imgUrl")
  @NotNull
  private String imgUrl;

  @ManyToMany
  @JoinColumn(name = "categorie_id")
  private Set<Categoria> categorie = new HashSet<Categoria>();

  @Column(name = "prezzo")
  @NotNull
  private int prezzo;

  @Column(name = "quantit�")
  @NotNull
  private int quantita;

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

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public Set<Categoria> getCategorie() {
    return categorie;
  }

  public void setCategorie(Set<Categoria> categorie) {
    this.categorie = categorie;
  }

  public int getPrezzo() {
    return prezzo;
  }

  public void setPrezzo(int prezzo) {
    this.prezzo = prezzo;
  }

  public int getQuantita() {
    return quantita;
  }

  public void setQuantita(int quantita) {
    this.quantita = quantita;
  }
}
