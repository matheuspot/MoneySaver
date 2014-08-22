package testes;

import static org.junit.Assert.*;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.After;
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
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private LocalDate data;
	
	@Before
	public void inicializaGerenteParaTestes() throws Exception {
		File arquivo = new File("data2.mos");
		arquivo.delete();
		
		data = LocalDate.parse("12/05/2014", formatter);
		usuario = new Usuario("usuario1", "usuario1@gmail.com", "123456",
				"nenhuma");
		gerente = new GerenteDeTransacoes(usuario);
		categoria1 = new Categoria("Fds", "Vermelho");
	}
	
	@After
	public void limpaArquivos() {
		File arquivo = new File("data2.mos");
		arquivo.delete();
	}

	@Test
	public void testaAdicionaTransacaoInvalida() {
		// Descrição inválida
		try {
			gerente.adicionaTransacao(null, data, "100.0", categoria1,
					"Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Descrição inválida.", e.getMessage());
		}

		try {
			gerente.adicionaTransacao("", data, "100.0", categoria1,
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

		// Valor inválido
		try {
			gerente.adicionaTransacao("Nada", data, null, categoria1,
					"Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Valor inválido.", e.getMessage());
		}

		try {
			gerente.adicionaTransacao("Nada", data, "", categoria1,
					"Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Valor inválido.", e.getMessage());
		}

		try {
			gerente.adicionaTransacao("Nada", data, "-20", categoria1,
					"Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Valor inválido.", e.getMessage());
		}

		// Categoria inválida
		try {
			gerente.adicionaTransacao("Nada", data, "100.0", null,
					"Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Categoria inválida.", e.getMessage());
		}

		// Recorrência inválida
		try {
			gerente.adicionaTransacao("Nada", data, "100.0",
					categoria1, null, "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Recorrência inválida.", e.getMessage());
		}

		try {
			gerente.adicionaTransacao("Nada", data, "100.0",
					categoria1, "", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Recorrência inválida.", e.getMessage());
		}

		// Tipo de transação inválido
		try {
			gerente.adicionaTransacao("Nada", data, "100.0",
					categoria1, "Semanal", null);
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Tipo de transação inválido.", e.getMessage());
		}
	}

	@Test
	public void testaRemoveTransacaoInvalida() throws Exception {
		Transacao transacao = new Despesa("Nada", data, 100.0,
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
	public void testaEditaTransacaoInvalida() throws Exception {
		Transacao transacao = new Despesa("Nada", data, 100.0,
				categoria1, "Semanal");
		// Transação para editar inválida ou inexistente
		try {
			gerente.editaTransacao(null, "Nada", data, "100.0",
					categoria1, "Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Transação inexistente.", e.getMessage());
		}

		try {
			gerente.editaTransacao(transacao, "Nada", data, "100.0",
					categoria1, "Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Transação inexistente.", e.getMessage());
		}

		gerente.adicionaTransacao("Nada", data, "100.0", categoria1,
				"Semanal", "despesa");

		// Descrição inválida
		try {
			gerente.editaTransacao(transacao, null, data, "100.0",
					categoria1, "Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Descrição inválida.", e.getMessage());
		}

		try {
			gerente.editaTransacao(transacao, "", data, "100.0",
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

		// Valor inválido
		try {
			gerente.editaTransacao(transacao, "Nada", data, null,
					categoria1, "Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Valor inválido.", e.getMessage());
		}

		try {
			gerente.editaTransacao(transacao, "Nada", data, "",
					categoria1, "Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Valor inválido.", e.getMessage());
		}

		try {
			gerente.editaTransacao(transacao, "Nada", data, "-20",
					categoria1, "Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Valor inválido.", e.getMessage());
		}

		// Categoria inválida
		try {
			gerente.editaTransacao(transacao, "Nada", data, "100.0",
					null, "Semanal", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Categoria inválida.", e.getMessage());
		}

		// Recorrência inválida
		try {
			gerente.editaTransacao(transacao, "Nada", data, "100.0",
					categoria1, null, "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Recorrência inválida.", e.getMessage());
		}

		try {
			gerente.editaTransacao(transacao, "Nada", data, "100.0",
					categoria1, "", "despesa");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Recorrência inválida.", e.getMessage());
		}

		// Tipo de transação inválido
		try {
			gerente.editaTransacao(transacao, "Nada", data, "100.0",
					categoria1, "Semanal", null);
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Tipo de transação inválido.", e.getMessage());
		}
	}
}
