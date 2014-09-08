package testes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fonte.Categoria;
import fonte.Conta;
import fonte.Usuario;

public class TestaUsuario {

	private Usuario usuario1;
	private Usuario usuario2;
	private Usuario usuario3;

	@Before
	public void inicializaUsuariosParaTestes() throws Exception {
		usuario1 = new Usuario("Usuário1", "meu_usuario@gmail.com", "123456",
				"menor senha", "Bradesco");
		usuario2 = new Usuario("Usuário2", "meu_usuario@gmail.com", "1234567",
				"senha média", "Banco do Brasil");
		usuario3 = new Usuario("Usuário3", "usuario3@hotmail.com", "12345678",
				"maior senha", "Santander");
	}

	@Test
	public void testaCriarUsuarioValido() throws Exception {
		new Usuario("Usuário qualquer", "usuario_qualquer@gmail.com", "123456",
				"qualquer dica", "Bradesco");
		new Usuario("Outro usuário", "outro.usuario@yahoo.com", "12345678",
				"sem dica", "Santander");
	}

	@Test
	public void testaCriarUsuarioNomeInvalido() {
		try {
			new Usuario("", "usuario@gmail.com", "123456", "dica", "Bradesco");
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.",
					"O nome do usuário deve ser informado.", e.getMessage());
		}
	}

	@Test
	public void testaCriarUsuarioEmailInvalido() {
		try {
			new Usuario("usuário", "usuario@<gmail>.com", "123456", "dica", "Bradesco");
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.", "E-mail inválido.",
					e.getMessage());
		}
	}

	@Test
	public void testaCriarUsuarioSenhaInvalida() {
		try {
			new Usuario("usuário", "usuario@gmail.com", "1234", "dica", "Bradesco");
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.",
					"Senha inválida, deve conter de 6 à 8 caracteres.",
					e.getMessage());
		}

		try {
			new Usuario("usuário", "usuario@gmail.com", "123456789010", "dica", "Bradesco");
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.",
					"Senha inválida, deve conter de 6 à 8 caracteres.",
					e.getMessage());
		}
	}

	@Test
	public void testaCriarUsuarioDicaDeSenhaInvalida() {
		try {
			new Usuario("usuário", "usuario@gmail.com", "1234567", "", "Bradesco");
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.", "Dica de senha inválida.",
					e.getMessage());
		}
	}
	
	@Test
	public void testaCriarUsuarioNomeDeContaInvalida() {
		try {
			new Usuario("usuário", "usuario@gmail.com", "1234567", "sequencia", "");
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.", "Nome da conta inválido.",
					e.getMessage());
		}
		
		try {
			new Usuario("usuário", "usuario@gmail.com", "1234567", "sequencia", null);
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.", "Nome da conta inválido.",
					e.getMessage());
		}
	}
	
	@Test
	public void testaToString() {
		assertEquals(
				"ToString errado.",
				"Nome: Usuário1\nE-mail: meu_usuario@gmail.com\nDica de senha: menor senha\nContas: Nome: Bradesco",
				usuario1.toString());
		assertEquals(
				"ToString errado.",
				"Nome: Usuário2\nE-mail: meu_usuario@gmail.com\nDica de senha: senha média\nContas: Nome: Banco do Brasil",
				usuario2.toString());
		assertEquals(
				"ToString errado.",
				"Nome: Usuário3\nE-mail: usuario3@hotmail.com\nDica de senha: maior senha\nContas: Nome: Santander",
				usuario3.toString());
	}

	@Test
	public void testaEquals() {
		assertTrue("Usuários deveriam ser iguais.", usuario1.equals(usuario2));
		assertFalse("Usuários não deveriam ser iguais.",
				usuario1.equals(usuario3));
	}
	
	@Test
	public void testaAdicionaContaValida() throws Exception {
		assertEquals(1, usuario1.getContas().size());
		usuario1.adicionaConta("Santander");
		assertEquals(2, usuario1.getContas().size());
	}
	
	@Test
	public void testaAdicionaContaInvalida() {
		try {
			usuario1.adicionaConta("Bradesco");
			fail("Esperava Excecao!");
		} catch (Exception e) {
			assertEquals("Conta já existe.", e.getMessage());
		}
		
		try {
			usuario1.adicionaConta("");
			fail("Esperava Excecao!");
		} catch (Exception e) {
			assertEquals("Nome da conta inválido.", e.getMessage());
		}
		
		try {
			usuario1.adicionaConta(null);
			fail("Esperava Excecao!");
		} catch (Exception e) {
			assertEquals("Nome da conta inválido.", e.getMessage());
		}
	}
	
	@Test
	public void testaRemoveContaValida() throws Exception {
		Conta conta = new Conta("Santander");
		usuario1.adicionaConta("Santander");
		assertEquals(2, usuario1.getContas().size());
		usuario1.removeConta(conta);
		assertEquals(1, usuario1.getContas().size());
	}
	
	@Test
	public void testaRemoveContaNaoExistente() throws Exception {
		Conta conta = new Conta("Santander");
		usuario1.adicionaConta("Banco do brasil");
		try {
			usuario1.removeConta(conta);
			fail("Esperava Excecao!");
		} catch (Exception e) {
			assertEquals("Conta inexistente.", e.getMessage());
		}
	}
	
	@Test
	public void testaPesquisaContaExistente() throws Exception {
		Conta conta = new Conta("Bradesco");
		assertEquals(conta, usuario1.pesquisaConta("Bradesco"));
	}
	
	@Test
	public void testaPesquisaContaInexistente() throws Exception {
		assertEquals(null, usuario1.pesquisaConta("Santander"));
	}
	
	@Test
	public void testaEditaConta() throws Exception {
		Conta conta = new Conta("Bradesco");
		usuario1.editaConta(conta, "Santander");
		assertEquals(null, usuario1.pesquisaConta("Bradesco"));
		conta = new Conta("Santander");
		assertEquals(conta, usuario1.pesquisaConta("Santander"));
	}
	
	@Test
	public void testaAdicionaCategoriaValida() throws Exception {
		assertEquals(2, usuario1.getCategorias().size());
		usuario1.adicionaCategoria("Feira", "Marrom");
		assertEquals(3, usuario1.getCategorias().size());
	}
	
	@Test
	public void testaAdicionaCategoriaInvalida() {
		try {
			usuario1.adicionaCategoria("", "Marrom");
			fail("Esperava Excecao!");
		} catch (Exception e) {
			assertEquals("Nome de categoria inválido.", e.getMessage());
		}
		
		try {
			usuario1.adicionaCategoria(null, "Marrom");
			fail("Esperava Excecao!");
		} catch (Exception e) {
			assertEquals("Nome de categoria inválido.", e.getMessage());
		}
		
		try {
			usuario1.adicionaCategoria("Feira", "");
			fail("Esperava Excecao!");
		} catch (Exception e) {
			assertEquals("Cor de categoria inválida.", e.getMessage());
		}
		
		try {
			usuario1.adicionaCategoria("Carro", null);
			fail("Esperava Excecao!");
		} catch (Exception e) {
			assertEquals("Cor de categoria inválida.", e.getMessage());
		}
		
		try {
			usuario1.adicionaCategoria("Lazer", "669966");
			fail("Esperava Excecao!");
		} catch (Exception e) {
			assertEquals("Categoria já existe.", e.getMessage());
		}
	}
	
	@Test
	public void testaRemoveCategoriaValida() throws Exception {
		Categoria categoria = new Categoria("Lazer", "669966");
		assertEquals(2, usuario1.getCategorias().size());
		usuario1.removeCategoria(categoria);
		assertEquals(1, usuario1.getCategorias().size());
	}
	
	@Test
	public void testaRemoveCategoriaInexistente() {
		try {
			Categoria categoria = new Categoria("Carro Novo", "Preto");
			usuario1.removeCategoria(categoria);
			fail("Esperava Excecao!");
		} catch (Exception e) {
			assertEquals("Categoria inexistente.", e.getMessage());
		}
	}
	
	@Test
	public void testaEditaCategoriaValida() throws Exception {
		Categoria categoria = new Categoria("Lazer", "669966");
		assertEquals(null, usuario1.pesquisaCategoria("Feira"));
		assertEquals(categoria, usuario1.pesquisaCategoria("Lazer"));
		
		usuario1.editaCategoria(categoria, "Feira", "Verde");
		
		categoria = new Categoria("Feira", "Verde");
		assertEquals(categoria, usuario1.pesquisaCategoria("Feira"));
		assertEquals(null, usuario1.pesquisaCategoria("Lazer"));
	}
}
