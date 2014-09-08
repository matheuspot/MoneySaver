package auxiliar;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Classe usada para criptografar uma senha.
 */
public abstract class Criptografia {

	private static final char[] PASSWORD = "enfldsgbnlsngdlksdsgm"
			.toCharArray();
	private static final byte[] SALT = { (byte) 0xde, (byte) 0x33, (byte) 0x10,
			(byte) 0x12, (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12, };

	/**
	 * Método usado para criptografar uma senha.
	 * 
	 * @param property
	 *            Senha que será criptografada.
	 * @return A senha criptografada.
	 * @throws GeneralSecurityException
	 *             Lança exceção se houver problemas com a criptografia da
	 *             senha.
	 * @throws UnsupportedEncodingException
	 *             Lança exceção se houver problemas com a criptografia da
	 *             senha.
	 */
	public static String encrypt(String property)
			throws GeneralSecurityException, UnsupportedEncodingException {
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance("PBEWithMD5AndDES");
		SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
		Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
		pbeCipher
				.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
		return base64Encode(pbeCipher.doFinal(property.getBytes("UTF-8")));
	}

	private static String base64Encode(byte[] bytes) {
		return new BASE64Encoder().encode(bytes);
	}

	/**
	 * Método usado para descriptografar uma senha.
	 * 
	 * @param property
	 *            Senha que será descriptografada.
	 * @return Senha decriptografada.
	 * @throws GeneralSecurityException
	 *             Lança exceção se houver problemas com a criptografia da
	 *             senha.
	 * @throws IOException
	 *             Lança exceção se houver problemas com a criptografia da
	 *             senha.
	 */
	public static String decrypt(String property)
			throws GeneralSecurityException, IOException {
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance("PBEWithMD5AndDES");
		SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
		Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
		pbeCipher
				.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
		return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
	}

	/**
	 * Decodificador.
	 */
	private static byte[] base64Decode(String property) throws IOException {
		return new BASE64Decoder().decodeBuffer(property);
	}
}