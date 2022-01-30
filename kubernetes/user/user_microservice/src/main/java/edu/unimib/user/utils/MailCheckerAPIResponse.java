package edu.unimib.user.utils;

public class MailCheckerAPIResponse {
  public String email;
  public String autocorrect;
  public String deliverability;
  public double quality_score;
  public IsValidFormat is_valid_format;
  public IsFreeEmail is_free_email;
  public IsDisposableEmail is_disposable_email;
  public IsRoleEmail is_role_email;
  public IsCatchallEmail is_catchall_email;
  public IsMxFound is_mx_found;
  public IsSmtpValid is_smtp_valid;

  public class IsValidFormat {
    public boolean value;
    public String text;
  }

  public class IsFreeEmail {
    public boolean value;
    public String text;
  }

  public class IsDisposableEmail {
    public boolean value;
    public String text;
  }

  public class IsRoleEmail {
    public boolean value;
    public String text;
  }

  public class IsCatchallEmail {
    public boolean value;
    public String text;
  }

  public class IsMxFound {
    public boolean value;
    public String text;
  }

  public class IsSmtpValid {
    public boolean value;
    public String text;
  }
}
