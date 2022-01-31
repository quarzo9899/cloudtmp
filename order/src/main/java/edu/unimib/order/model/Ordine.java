package edu.unimib.order.model;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity(name = "ordine")
@Table
public class Ordine {
  @Id
  @GeneratedValue(generator = "ordine_generator")
  @SequenceGenerator(
      name = "ordine_generator",
      sequenceName = "ordine_sequence",
      initialValue = 1000)
  private long id;

  @Column(name = "dataOrdine")
  @NotNull
  private Date dataOrdine;

  @Column(name = "utenteId")
  @NotNull
  private long utenteId;

  @Column(name = "prodottiId")
  @ElementCollection(targetClass=Integer.class)
  @NotNull
  private List<Integer> prodottiId;

  @Column(name = "costo")
  @NotNull
  private int costo;

  @Column(name = "pagamento")
  @NotNull
  private String pagamento;

  @Column(name = "stato")
  @NotNull
  private String stato;

  @Column(name = "indirizzo")
  @NotNull
  private String indirizzo;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Date getDataOrdine() {
    return dataOrdine;
  }

  public void setDataOrdine(Date dataOrdine) {
    this.dataOrdine = dataOrdine;
  }

  public long getUtenteId() {
    return utenteId;
  }

  public void setUtenteId(long utenteId) {
    this.utenteId = utenteId;
  }

  public List<Integer> getProdottiId() {
    return prodottiId;
  }

  public void setProdottiId(List<Integer> prodottiId) {
    this.prodottiId = prodottiId;
  }

  public int getCosto() {
    return costo;
  }

  public void setCosto(int costo) {
    this.costo = costo;
  }

  public String getPagamento() {
    return pagamento;
  }

  public void setPagamento(String pagamento) {
    this.pagamento = pagamento;
  }

  public String getStato() {
    return stato;
  }

  public void setStato(String stato) {
    this.stato = stato;
  }

  public String getIndirizzo() {
    return indirizzo;
  }

  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }
}
