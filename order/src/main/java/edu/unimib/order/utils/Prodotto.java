package edu.unimib.order.utils;

import javax.persistence.Entity;
import javax.persistence.Table;

public class Prodotto {
  private long id;

  private String nome;

  private String imgUrl;

  private int prezzo;

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
