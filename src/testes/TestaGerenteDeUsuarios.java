package testes;

import static org.junit.Assert.*;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import auxiliar.Criptografia;
import fonte.Categoria;
import fonte.GerenteDeUsuarios;
import fonte.Usuario;

public class TestaGerenteDeUsuarios {

	private GerenteDeUsuarios gerente;
	private LocalDate dataDeInsercao;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private Categoria categoria;
	
	@Before
	public void inicializaGerenteParaTestes() throws Exception {
		File arquivo = new File("data.mos");
		arquivo.delete();
		gerente = new GerenteDeUsuarios();
		dataDeInsercao = LocalDate.parse("06/08/2014", formatter);
		categoria = new Categoria("Feira", "Verde");
	}

	@After
	public void limpaArquivos() {
		File arquivo = new File("data.mos");
		arquivo.delete();
	}

	@Test
	public void testaAdicionaUsuarioValido() throws Exception {
		gerente.adicionaUsuario("Usuário1", "usuario1@gmail.com", "123456",
				"123456", "senha fácil", "Bradesco");
		gerente.adicionaUsuario("Usuário200", "usuario200@yahoo.com",
				"1234567", "1234567", "senha diferente", "Bradesco");
	}

	@Test
	public void testaAdicionaUsuarioNomeInvalido() {
		try {
			gerente.adicionaUsuario("", "usuario@gmail.com", "123456",
					"123456", "dica", "Bradesco");
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
					"123456", "dica", "Bradesco");
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
					"1234567", "dica", "Bradesco");
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
					"123456", "", "Bradesco");
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.", "Dica de senha inválida.",
					e.getMessage());
		}
	}

	@Test
	public void testaAdicionaUsuarioExistente() throws Exception {
		gerente.adicionaUsuario("Usuário1", "usuario1@gmail.com", "123456",
				"123456", "senha fácil", "Bradesco");

		try {
			gerente.adicionaUsuario("Usuário1", "usuario1@gmail.com", "123456",
					"123456", "senha fácil", "Bradesco");
			fail("Deveria ter lançado exceção, usuário já existe.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.",
					"E-mail inválido ou já existe.", e.getMessage());
		}
	}

	@Test
	public void testaPesquisaUsuario() throws Exception {
		gerente.adicionaUsuario("Usuário1", "usuario1@gmail.com", "123456",
				"123456", "senha fácil", "Bradesco");

		assertEquals("Retornou usuário errado.", "usuario1@gmail.com", gerente
				.pesquisaUsuario("usuario1@gmail.com").getEmail());
	}

	@Test
	public void testaLogin() throws Exception {
		gerente.adicionaUsuario("Usuário1", "usuario1@gmail.com", "123456",
				"123456", "senha fácil", "Bradesco");

		assertEquals("Usuário logado errado.", "usuario1@gmail.com", gerente
				.login("usuario1@gmail.com", "123456").getEmail());
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
				"123456", "senha fácil", "Bradesco");

		try {
			gerente.login("usuario1@gmail.com", "1234567");
			fail("Deveria ter lançado exceção, senha está errada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.", "Senha incorreta!",
					e.getMessage());
		}
	}
}
