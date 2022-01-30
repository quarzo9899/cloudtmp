package edu.unimib.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity(name = "utente")
@Table
public class Utente {
  @Id
  @GeneratedValue(generator = "utente_generator")
  @SequenceGenerator(
      name = "utente_generator",
      sequenceName = "utente_sequence",
      initialValue = 1000)
  private long id;

  @Column(name = "isAdmin")
  @NotNull
  boolean isAdmin = false;

  @Column(unique = true, name = "mail")
  @NotNull
  private String mail;

  @Column(name = "password")
  @NotNull
  private String password;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
