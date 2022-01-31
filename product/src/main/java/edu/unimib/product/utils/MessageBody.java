package edu.unimib.product.utils;

public class MessageBody {
  public long id;
  public boolean isAdmin;
  public String expire;

  public MessageBody(long id, boolean isAdmin, String expire) {
    this.id = id;
    this.isAdmin = isAdmin;
    this.expire = expire;
  }

  public long getId() {
    return id;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public String getExpire() {
    return expire;
  }
}
