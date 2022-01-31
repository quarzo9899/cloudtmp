package edu.unimib.product.utils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

public class Decrypt {
  public static String Decrypt(String msg) {
    try {
      File privateKeyFile = new File("/etc/keys/private.key");
      byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
      PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

      Cipher decryptCipher = Cipher.getInstance("RSA");
      decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
      byte[] decryptedMessageBytes = decryptCipher.doFinal(Base64.getDecoder().decode(msg));
      String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
      return decryptedMessage;
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }
}
