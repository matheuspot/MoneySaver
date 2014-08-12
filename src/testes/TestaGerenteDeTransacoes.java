package testes;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Before;
import org.junit.Test;
import fonte.Categoria;
import fonte.Despesa;
import fonte.GerenteDeTransacoes;
import fonte.Transacao;
import fonte.Usuario;

public class TestaGerenteDeTransacoes {

	private GerenteDeTransacoes gerente;
	private Usuario usuario;
	private Categoria categoria1;

	@Before
	public void inicializaGerenteParaTestes() throws Exception {
		File arquivo = new File("data2.mos");
		arquivo.delete();

		usuario = new Usuario("usuario1", "usuario1@gmail.com", "123456",
				"nenhuma");
		gerente = new GerenteDeTransacoes(usuario);
		categoria1 = new Categoria("Fds", "Vermelho");

		assertEquals(0, gerente.listaTransacoesResumidas().length);
	}

	@Test
	public void testaAdicionaTransacaoValida() throws Exception {
		gerente.adicionaTransacao("Nada", "12/05/2014", "150.5", categoria1,
				"Semanal", "despesa");
		assertEquals(1, gerente.listaTransacoesResumidas().length);
	}

	@Test
	public void testaAdicionaTransacaoInvalida() {
		// Descrição inválida
		try {
			gerente.adicionaTransacao(null, "12/05/2014", "100.0", categoria1,
					"Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Descrição inválida.", e.getMessage());
		}

		try {
			gerente.adicionaTransacao("", "12/05/2014", "100.0", categoria1,
					"Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Descrição inválida.", e.getMessage());
		}

		// Data de inserção inválida
		try {
			gerente.adicionaTransacao("Nada", null, "100.0", categoria1,
					"Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Data de inserção inválida.", e.getMessage());
		}

		try {
			gerente.adicionaTransacao("Nada", "", "100.0", categoria1,
					"Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Data de inserção inválida.", e.getMessage());
		}

		// Valor inválido
		try {
			gerente.adicionaTransacao("Nada", "12/05/2014", null, categoria1,
					"Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Valor inválido.", e.getMessage());
		}

		try {
			gerente.adicionaTransacao("Nada", "12/05/2014", "", categoria1,
					"Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Valor inválido.", e.getMessage());
		}

		try {
			gerente.adicionaTransacao("Nada", "12/05/2014", "-20", categoria1,
					"Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Valor inválido.", e.getMessage());
		}

		// Categoria inválida
		try {
			gerente.adicionaTransacao("Nada", "12/05/2014", "100.0", null,
					"Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Categoria inválida.", e.getMessage());
		}

		// Recorrência inválida
		try {
			gerente.adicionaTransacao("Nada", "12/05/2014", "100.0",
					categoria1, null, "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Recorrência inválida.", e.getMessage());
		}

		try {
			gerente.adicionaTransacao("Nada", "12/05/2014", "100.0",
					categoria1, "", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Recorrência inválida.", e.getMessage());
		}

		// Tipo de transação inválido
		try {
			gerente.adicionaTransacao("Nada", "12/05/2014", "100.0",
					categoria1, "Semanal", null);
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Tipo de transação inválido.", e.getMessage());
		}

		try {
			gerente.adicionaTransacao("Nada", "12/05/2014", "100.0",
					categoria1, "Semanal", "");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Tipo de transação inválido.", e.getMessage());
		}

		assertEquals(0, gerente.listaTransacoesResumidas().length);
	}

	@Test
	public void testaRemoveTransacaoValida() throws Exception {
		Transacao transacao = new Despesa("Nada", "12/05/2014", 100.0,
				categoria1, "Semanal");
		gerente.adicionaTransacao("Nada", "12/05/2014", "100.0", categoria1,
				"Semanal", "despesa");
		assertEquals(1, gerente.listaTransacoesResumidas().length);
		gerente.removeTransacao(transacao);
		assertEquals(0, gerente.listaTransacoesResumidas().length);
	}

	@Test
	public void testaRemoveTransacaoInvalida() throws Exception {
		Transacao transacao = new Despesa("Nada", "12/05/2014", 100.0,
				categoria1, "Semanal");

		try {
			gerente.removeTransacao(null);
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Transação inexistente.", e.getMessage());
		}

		try {
			gerente.removeTransacao(transacao);
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Transação inexistente.", e.getMessage());
		}
	}

	@Test
	public void testaEditaTransacaoValida() throws Exception {
		Transacao transacao = new Despesa("Nada", "12/05/2014", 100.0,
				categoria1, "Semanal");
		gerente.adicionaTransacao("Nada", "12/05/2014", "100.0", categoria1,
				"Semanal", "despesa");
		assertEquals("12/05/2014 100.0", gerente.listaTransacoesResumidas()[0]);

		gerente.editaTransacao(transacao, "Edit", "28/12/2014", "50.0",
				categoria1, "Semanal", "provento");
		assertEquals("28/12/2014 50.0", gerente.listaTransacoesResumidas()[0]);
	}

	@Test
	public void testaEditaTransacaoInvalida() throws Exception {
		Transacao transacao = new Despesa("Nada", "12/05/2014", 100.0,
				categoria1, "Semanal");
		// Transação para editar inválida ou inexistente
		try {
			gerente.editaTransacao(null, "Nada", "12/05/2014", "100.0",
					categoria1, "Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Transação inexistente.", e.getMessage());
		}

		try {
			gerente.editaTransacao(transacao, "Nada", "12/05/2014", "100.0",
					categoria1, "Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Transação inexistente.", e.getMessage());
		}

		gerente.adicionaTransacao("Nada", "12/05/2014", "100.0", categoria1,
				"Semanal", "despesa");

		// Descrição inválida
		try {
			gerente.editaTransacao(transacao, null, "12/05/2014", "100.0",
					categoria1, "Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Descrição inválida.", e.getMessage());
		}

		try {
			gerente.editaTransacao(transacao, "", "12/05/2014", "100.0",
					categoria1, "Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Descrição inválida.", e.getMessage());
		}

		// Data de inserção inválida
		try {
			gerente.editaTransacao(transacao, "Nada", null, "100.0",
					categoria1, "Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Data de inserção inválida.", e.getMessage());
		}

		try {
			gerente.editaTransacao(transacao, "Nada", "", "100.0", categoria1,
					"Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Data de inserção inválida.", e.getMessage());
		}

		// Valor inválido
		try {
			gerente.editaTransacao(transacao, "Nada", "12/05/2014", null,
					categoria1, "Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Valor inválido.", e.getMessage());
		}

		try {
			gerente.editaTransacao(transacao, "Nada", "12/05/2014", "",
					categoria1, "Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Valor inválido.", e.getMessage());
		}

		try {
			gerente.editaTransacao(transacao, "Nada", "12/05/2014", "-20",
					categoria1, "Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Valor inválido.", e.getMessage());
		}

		// Categoria inválida
		try {
			gerente.editaTransacao(transacao, "Nada", "12/05/2014", "100.0",
					null, "Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Categoria inválida.", e.getMessage());
		}

		// Recorrência inválida
		try {
			gerente.editaTransacao(transacao, "Nada", "12/05/2014", "100.0",
					categoria1, null, "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Recorrência inválida.", e.getMessage());
		}

		try {
			gerente.editaTransacao(transacao, "Nada", "12/05/2014", "100.0",
					categoria1, "", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Recorrência inválida.", e.getMessage());
		}

		// Tipo de transação inválido
		try {
			gerente.editaTransacao(transacao, "Nada", "12/05/2014", "100.0",
					categoria1, "Semanal", null);
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Tipo de transação inválido.", e.getMessage());
		}

		try {
			gerente.editaTransacao(transacao, "Nada", "12/05/2014", "100.0",
					categoria1, "Semanal", "");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Tipo de transação inválido.", e.getMessage());
		}

		assertEquals(1, gerente.listaTransacoesResumidas().length);
	}

	@Test
	public void testaListaTransacoesResumidasAndDetalhadas() throws Exception {
		gerente.adicionaTransacao("Nada", "12/05/2014", "100.0", categoria1,
				"Semanal", "despesa");

		assertEquals("12/05/2014 100.0", gerente.listaTransacoesResumidas()[0]);
		assertEquals("Descrição: Nada\n" + "Data de Inserção: 12/05/2014\n"
				+ "Valor: 100.0\n" + "Categoria: Fds\n"
				+ "Recorrência: Semanal",
				gerente.listaTransacoesDetalhadas()[0]);
	}
}
