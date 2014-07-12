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
	public void testAdicionaUsuarioValido() throws Exception {
		gerente.adicionaUsuario("Usuário1", "usuario1@gmail.com", "123456",
				"123456", "senha fácil");
		gerente.adicionaUsuario("Usuário200", "usuario200@yahoo.com",
				"1234567", "1234567", "senha diferente");
	}

	@Test
	public void testAdicionaUsuarioExistente() throws Exception {
		gerente.adicionaUsuario("Usuário1", "usuario1@gmail.com", "123456",
				"123456", "senha fácil");

		try {
			gerente.adicionaUsuario("Usuário1", "usuario1@gmail.com", "123456",
					"123456", "senha fácil");
			fail("Deveria ter lançado exceção, usuário já existe.");
		} catch (Exception e) {
			assertEquals("Mensagem de exceção errada.",
					"E-mail inválido ou já existe.", e.getMessage());
		}
	}

	@Test
	public void testPesquisaUsuario() throws Exception {
		gerente.adicionaUsuario("Usuário1", "usuario1@gmail.com", "123456",
				"123456", "senha fácil");

		assertEquals(
				"Retornou usuário errado.",
				"Nome: Usuário1\nE-mail: usuario1@gmail.com\nDica de senha: senha fácil",
				gerente.pesquisaUsuario("usuario1@gmail.com").toString());
	}

	@Test
	public void testLogin() throws Exception {
		gerente.adicionaUsuario("Usuário1", "usuario1@gmail.com", "123456",
				"123456", "senha fácil");

		gerente.login("usuario1@gmail.com", "123456");
	}

	@Test
	public void testLoginUsuarioInexistente() {
		try {
			gerente.login("usuario1@gmail.com", "123456");
			fail("Deveria ter lançado exceção, usuário não existe.");
		} catch (Exception e) {
			assertEquals("Mensagem de exceção errada.",
					"Usuário não existe. Cadastre-se primeiro.", e.getMessage());
		}
	}

	@Test
	public void testLoginUsuarioSenhaIncorreta() throws Exception {
		gerente.adicionaUsuario("Usuário1", "usuario1@gmail.com", "123456",
				"123456", "senha fácil");

		try {
			gerente.login("usuario1@gmail.com", "1234567");
			fail("Deveria ter lançado exceção, senha está errada.");
		} catch (Exception e) {
			assertEquals("Mensagem de exceção errada.", "Senha incorreta!",
					e.getMessage());
		}
	}
}
