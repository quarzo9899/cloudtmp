import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KeyPairGenerator generator;
		try {

			/**
			generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(756);
			KeyPair pair = generator.generateKeyPair();
 
			
			PrivateKey privateKey = pair.getPrivate();
			PublicKey publicKey = pair.getPublic();
			try (FileOutputStream fos = new FileOutputStream("public.key")) {
			    fos.write(publicKey.getEncoded());
			}
			try (FileOutputStream fos = new FileOutputStream("private.key")) {
			    fos.write(privateKey.getEncoded());
			}**/
			
			File publicKeyFile = new File("public.key");
			byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
			PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);


			
			String secretMessage = "{id:1524,expire:\"23/01/2022-sssssssss12:57\"}";
			Cipher encryptCipher = Cipher.getInstance("RSA");
			encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
			byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
			String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);
			System.out.println(encodedMessage);
			
			String decodeTest = "BAscKCD2m32n2GH7t6RpzRiWgJSM8dinNfUl0g0peP99qSITdvA9gfsgLg/PVamyRE2PQ5lFsNxkxTpj6faiMZwNUdQG8nm88nTTMAOvcDnB/fh37q1xjS7j1+mXdGA=";
			File privateKeyFile = new File("private.key");
			byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
			keyFactory = KeyFactory.getInstance("RSA");
		    PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
			
			Cipher decryptCipher = Cipher.getInstance("RSA");
			decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] decryptedMessageBytes = decryptCipher.doFinal(Base64.getDecoder().decode(decodeTest));
			String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
			System.out.println(decryptedMessage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
