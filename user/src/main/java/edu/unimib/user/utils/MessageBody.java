package edu.unimib.user.utils;

public class MessageBody {
  public long id;
  public boolean isAdmin;
  public String expire;

  public MessageBody(long id, boolean isAdmin, String expire) {
    this.id = id;
    this.isAdmin = isAdmin;
    this.expire = expire;
  }
}
