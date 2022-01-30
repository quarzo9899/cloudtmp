package edu.unimib.user.utils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

public class Crypt {
  public static String crypt(String msg) {
    try {
      // File publicKeyFile = new File("C:\\public.key");
      File publicKeyFile = new File("/etc/keys/public.key");
      byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
      PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

      Cipher encryptCipher = Cipher.getInstance("RSA");
      encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
      byte[] secretMessageBytes = msg.getBytes(StandardCharsets.UTF_8);
      byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
      return Base64.getEncoder().encodeToString(encryptedMessageBytes);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }
}
