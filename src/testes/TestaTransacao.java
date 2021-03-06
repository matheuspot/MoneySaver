package testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import excecao.MoneySaverException;
import fonte.Categoria;
import fonte.Despesa;
import fonte.Provento;
import fonte.Transacao;

public class TestaTransacao {

	private Categoria categoria1;
	private Categoria categoria2;
	private Transacao transacao1;
	private Transacao transacao2;
	private LocalDate dataAtual;

	@Before
	public void iniciaTransacao() throws Exception {
		dataAtual = LocalDate.now();
		categoria1 = new Categoria("Trabalho", "verde");
		categoria2 = new Categoria("Universidade", "Roxo");

		transacao1 = new Provento("Bolsa PIBIC", dataAtual, 400.00, categoria1,
				"Mensal");
		transacao2 = new Despesa("Gastos com material", dataAtual, 65.40,
				categoria2, "Mensal");
	}

	@Test
	public void testaGetDescricao() {
		assertEquals("Bolsa PIBIC", transacao1.getDescricao());
		assertEquals("Gastos com material", transacao2.getDescricao());
	}

	@Test
	public void testaGetDataDeInsercao() {
		assertEquals(dataAtual, transacao1.getDataDeInsercao());
		assertEquals(dataAtual, transacao2.getDataDeInsercao());
	}

	@Test
	public void testaGetValor() {
		assertEquals(400.00, transacao1.getValor(), 0.01);
		assertEquals(-65.40, transacao2.getValor(), 0.01);
	}

	@Test
	public void testaValorInvalido() {
		try {
			transacao1 = new Provento("Bolsa PIBIC", dataAtual, 0, categoria1,
					"Mensal");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Valor inválido.", e.getMessage());
		}

		try {
			transacao1 = new Provento("Bolsa PIBIC", dataAtual, -568.9,
					categoria1, "Mensal");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Valor inválido.", e.getMessage());
		}
	}

	@Test
	public void testaGetCategoria() {
		assertEquals(categoria1, transacao1.getCategoria());
		assertEquals(categoria2, transacao2.getCategoria());
	}

	@Test
	public void testaCategoriaInvalida() {
		try {
			transacao1 = new Provento("Bolsa PIBIC", dataAtual, 400.00, null,
					"Mensal");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Categoria inválida.", e.getMessage());
		}

		try {
			transacao2 = new Despesa("Gastos com material", dataAtual, 65.40,
					null, "Mensal");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Categoria inválida.", e.getMessage());
		}
	}

	@Test
	public void testaGetRecorrencia() {
		assertEquals("Mensal", transacao1.getRecorrencia());
		assertEquals("Mensal", transacao2.getRecorrencia());
	}

	@Test
	public void testaToString() {
		assertEquals("Descrição: Bolsa PIBIC" + "\nData de Inserção: "
				+ dataAtual + "\nValor: 400.0"
				+ "\nCategoria: Trabalho\nRecorrência: Mensal",
				transacao1.toString());

		assertEquals("Descrição: Gastos com material" + "\nData de Inserção: "
				+ dataAtual + "\nValor: 65.4"
				+ "\nCategoria: Universidade\nRecorrência: Mensal",
				transacao2.toString());
	}

	@Test
	public void testaEquals() throws Exception {
		assertFalse(transacao1.equals(transacao2));
		assertFalse(transacao2.equals(transacao1));
		assertTrue(transacao1.equals(transacao1));

		transacao2 = new Provento("Bolsa PIBIC", dataAtual, 400.00, categoria1,
				"Mensal");
		assertTrue(transacao1.equals(transacao2));

		transacao2 = new Despesa("Bolsa PIBIC", dataAtual, 400.00, categoria1,
				"Mensal");
		assertFalse(transacao1.equals(transacao2));
	}

	@Test
	public void testaDescricaoInvalida() {
		try {
			transacao1 = new Transacao(null, dataAtual, 400.00, categoria1,
					"Mensal");
			fail("Esperava Excecao!");
		} catch (MoneySaverException e) {
			assertEquals("Descrição inválida.", e.getMessage());
		}
	}

	@Test
	public void testaDataInvalida() {
		try {
			transacao1 = new Transacao("Coisa", null, 400.00, categoria1,
					"Mensal");
			fail("Esperava Excecao!");
		} catch (MoneySaverException e) {
			assertEquals("Data de inserção inválida.", e.getMessage());
		}
	}

	@Test
	public void testaRecorrenciaInvalida() {
		try {
			transacao1 = new Transacao("Coisa", dataAtual, 400.00, categoria1,
					null);
			fail("Esperava Excecao!");
		} catch (MoneySaverException e) {
			assertEquals("Recorrência inválida.", e.getMessage());
		}
	}
}
