package testes;

import static org.junit.Assert.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.junit.Test;

public class TestaCriptografia {

	@Test
	public void testaCriptografia() throws GeneralSecurityException,
			IOException {

		String senha = "senha123";
		String senhaEncrypted = auxiliar.Criptografia.encrypt(senha);
		assertNotEquals(senha, senhaEncrypted);
		String senhaDecrypted = auxiliar.Criptografia.decrypt(senhaEncrypted);
		assertEquals(senha, senhaDecrypted);
	}
}
