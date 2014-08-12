package testes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import fonte.Usuario;

public class TestaUsuario {

	private Usuario usuario1;
	private Usuario usuario2;
	private Usuario usuario3;

	@Before
	public void inicializaUsuariosParaTestes() throws Exception {
		usuario1 = new Usuario("Usuário1", "meu_usuario@gmail.com", "123456",
				"menor senha");
		usuario2 = new Usuario("Usuário2", "meu_usuario@gmail.com", "1234567",
				"senha média");
		usuario3 = new Usuario("Usuário3", "usuario3@hotmail.com", "12345678",
				"maior senha");
	}

	@Test
	public void testaCriarUsuarioValido() throws Exception {
		new Usuario("Usuário qualquer", "usuario_qualquer@gmail.com", "123456",
				"qualquer dica");
		new Usuario("Outro usuário", "outro.usuario@yahoo.com", "12345678",
				"sem dica");
	}

	@Test
	public void testaCriarUsuarioNomeInvalido() {
		try {
			new Usuario("", "usuario@gmail.com", "123456", "dica");
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.",
					"O nome do usuário deve ser informado.", e.getMessage());
		}
	}

	@Test
	public void testaCriarUsuarioEmailInvalido() {
		try {
			new Usuario("usuário", "usuario@<gmail>.com", "123456", "dica");
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.", "E-mail inválido.",
					e.getMessage());
		}
	}

	@Test
	public void testaCriarUsuarioSenhaInvalida() {
		try {
			new Usuario("usuário", "usuario@gmail.com", "1234", "dica");
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.",
					"Senha inválida, deve conter de 6 a 8 caracteres.",
					e.getMessage());
		}

		try {
			new Usuario("usuário", "usuario@gmail.com", "123456789010", "dica");
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.",
					"Senha inválida, deve conter de 6 a 8 caracteres.",
					e.getMessage());
		}
	}

	@Test
	public void testaCriarUsuarioDicaDeSenhaInvalida() {
		try {
			new Usuario("usuário", "usuario@gmail.com", "1234567", "");
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.", "Dica de senha inválida.",
					e.getMessage());
		}
	}

	@Test
	public void testaToString() {
		assertEquals(
				"ToString errado.",
				"Nome: Usuário1\nE-mail: meu_usuario@gmail.com\nDica de senha: menor senha\nSaldo da conta: 0.0",
				usuario1.toString());
		assertEquals(
				"ToString errado.",
				"Nome: Usuário2\nE-mail: meu_usuario@gmail.com\nDica de senha: senha média\nSaldo da conta: 0.0",
				usuario2.toString());
		assertEquals(
				"ToString errado.",
				"Nome: Usuário3\nE-mail: usuario3@hotmail.com\nDica de senha: maior senha\nSaldo da conta: 0.0",
				usuario3.toString());
	}

	@Test
	public void testaEquals() {
		assertTrue("Usuários deveriam ser iguais.", usuario1.equals(usuario2));
		assertFalse("Usuários não deveriam ser iguais.",
				usuario1.equals(usuario3));
	}
}
