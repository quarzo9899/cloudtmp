package edu.unimib.user.utils;

import com.google.gson.Gson;
import java.io.IOException;
import org.apache.http.client.fluent.*;

public class MailCheckerAPI {
  public static MailCheckerAPIResponse makeAbstractRequest(String email) {
    MailCheckerAPIResponse response = null;
    try {

      Content content =
          Request.Get(
                  "https://emailvalidation.abstractapi.com/v1/?api_key=e1aead156f2849a7afd278b5455f3de3&email="
                      + email)
              .execute()
              .returnContent();
      response = new Gson().fromJson(content.asString(), MailCheckerAPIResponse.class);
    } catch (IOException error) {
      System.out.println(error);
    }
    return response;
  }
}
