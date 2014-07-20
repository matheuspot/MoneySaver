package testes;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Before;
import org.junit.Test;

import fonte.GerenteDeUsuarios;

public class TestaGerenteDeUsuarios {

	private GerenteDeUsuarios gerente;

	@Before
	public void inicializaGerenteParaTestes() {
		File arquivo = new File("usuarios");
		arquivo.delete();
		gerente = new GerenteDeUsuarios();
	}

	@Test
	public void testaAdicionaUsuarioValido() throws Exception {
		gerente.adicionaUsuario("Usuário1", "usuario1@gmail.com", "123456",
				"123456", "senha fácil");
		gerente.adicionaUsuario("Usuário200", "usuario200@yahoo.com",
				"1234567", "1234567", "senha diferente");
	}

	@Test
	public void testaAdicionaUsuarioNomeInvalido() {
		try {
			gerente.adicionaUsuario("", "usuario@gmail.com", "123456",
					"123456", "dica");
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensage de erro errada.", "Nome inválido.",
					e.getMessage());
		}
	}

	@Test
	public void testaAdicionaUsuarioEmailInvalido() {
		try {
			gerente.adicionaUsuario("usuario", "usuario@<gmail>.com", "123456",
					"123456", "dica");
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.",
					"E-mail inválido ou já existe.", e.getMessage());
		}
	}

	@Test
	public void testaAdicionaUsuarioConfirmacaoDeSenhaNaoConfere() {
		try {
			gerente.adicionaUsuario("usuario", "usuario@gmail.com", "123456",
					"1234567", "dica");
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.",
					"Senha inválida ou não confere com confirmação.",
					e.getMessage());
		}
	}

	@Test
	public void testaAdicionaUsuarioDicaDeSenhaInvalida() {
		try {
			gerente.adicionaUsuario("usuario", "usuario@gmail.com", "123456",
					"123456", "");
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.", "Dica de senha inválida.",
					e.getMessage());
		}
	}

	@Test
	public void testaAdicionaUsuarioExistente() throws Exception {
		gerente.adicionaUsuario("Usuário1", "usuario1@gmail.com", "123456",
				"123456", "senha fácil");

		try {
			gerente.adicionaUsuario("Usuário1", "usuario1@gmail.com", "123456",
					"123456", "senha fácil");
			fail("Deveria ter lançado exceção, usuário já existe.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.",
					"E-mail inválido ou já existe.", e.getMessage());
		}
	}

	@Test
	public void testaPesquisaUsuario() throws Exception {
		gerente.adicionaUsuario("Usuário1", "usuario1@gmail.com", "123456",
				"123456", "senha fácil");

		assertEquals(
				"Retornou usuário errado.",
				"Nome: Usuário1\nE-mail: usuario1@gmail.com\nDica de senha: senha fácil",
				gerente.pesquisaUsuario("usuario1@gmail.com").toString());
	}

	@Test
	public void testaLogin() throws Exception {
		gerente.adicionaUsuario("Usuário1", "usuario1@gmail.com", "123456",
				"123456", "senha fácil");

		gerente.login("usuario1@gmail.com", "123456");
	}

	@Test
	public void testaLoginUsuarioInexistente() {
		try {
			gerente.login("usuario1@gmail.com", "123456");
			fail("Deveria ter lançado exceção, usuário não existe.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.",
					"Usuário não existe. Cadastre-se primeiro.", e.getMessage());
		}
	}

	@Test
	public void testaLoginUsuarioSenhaIncorreta() throws Exception {
		gerente.adicionaUsuario("Usuário1", "usuario1@gmail.com", "123456",
				"123456", "senha fácil");

		try {
			gerente.login("usuario1@gmail.com", "1234567");
			fail("Deveria ter lançado exceção, senha está errada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.", "Senha incorreta!",
					e.getMessage());
		}
	}
}
