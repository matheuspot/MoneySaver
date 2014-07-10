package testes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import fonte.GerenteDeUsuarios;
import fonte.Usuario;

public class TestaGerenteDeUsuarios {

	private GerenteDeUsuarios gerente;

	@Before
	public void inicializaGerenteParaTestes() {
		gerente = new GerenteDeUsuarios();

		assertEquals(null, gerente.newEmail);
		assertEquals(null, gerente.newNome);
		assertEquals(null, gerente.newSenha);
		assertEquals(null, gerente.newConfirmacaoDeSenha);
		assertEquals(null, gerente.newDicaDeSenha);
	}

	@Test
	public void testAdicionaUsuarioValido() {

		gerente.newEmail = "usuario1@casa.com";
		gerente.newNome = "usuario1";
		gerente.newSenha = "minhasenha123";
		gerente.newSenhaConfirmacao = "minhasenha123";
		gerente.newDicaDeSenha = "senha fácil";

		try {
			Usuario usuario = new Usuario(gerente.newNome, gerente.newEmail,
					gerente.newSenha, gerente.newDicaDeSenha);
		} catch (Exception e) {
			fail("Exceção não deveria ter sido lançada.");
		}
		gerente.adicionaUsuario(usuario);
	}

	@Test
	public void testAdicionaUsuarioInvalido() {

		gerente.newEmail = "usuario1@casa.com";
		gerente.newNome = "usuario1";
		gerente.newSenha = "minhasenha123";
		gerente.newSenhaConfirmacao = "minhasenha123";
		// Dica de senha é null

		try {
			Usuario usuario = new Usuario(gerente.newNome, gerente.newEmail,
					gerente.newSenha, gerente.newDicaDeSenha);
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.",
					"Por favor, preencher o campo com sua dica de senha.",
					e.getMessage());
		}

		gerente.newDicaDeSenha = "   ";
		// Dica de senha é vazia (só espaços)

		try {
			Usuario usuario = new Usuario(gerente.newNome, gerente.newEmail,
					gerente.newSenha, gerente.newDicaDeSenha);
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.",
					"Por favor, preencher o campo com sua dica de senha.",
					e.getMessage());
		}
	}
}
