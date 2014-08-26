package testes;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import fonte.Categoria;
import fonte.Despesa;
import fonte.Provento;
import fonte.Transacao;

public class TestaTransacao {

	private Categoria categoria1;
	private Categoria categoria2;
	private Transacao transacao1;
	private Transacao transacao2;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private LocalDate data1;
	private LocalDate data2;
	
	@Before
	public void iniciaTransacao() throws Exception {
		categoria1 = new Categoria("Trabalho", "verde");
		categoria2 = new Categoria("Universidade", "Roxo");
		data1 = LocalDate.parse("06/08/2014", formatter);
		data2 = LocalDate.parse("07/08/2014", formatter);
		
		transacao1 = new Provento("Bolsa PIBIC", data1, 400.00,
				categoria1, "Mensal");
		transacao2 = new Despesa("Gastos com material", data2, 65.40,
				categoria2, "Semestral");
	}

	@Test
	public void testaGetDescricao() {
		assertEquals("Bolsa PIBIC", transacao1.getDescricao());
		assertEquals("Gastos com material", transacao2.getDescricao());
	}

	@Test
	public void testaGetDataDeInsercao() {
		assertEquals(data1, transacao1.getDataDeInsercao());
		assertEquals(data2, transacao2.getDataDeInsercao());
	}

	@Test
	public void testaGetValor() {
		assertEquals(400.00, transacao1.getValor(), 0.01);
		assertEquals(-65.40, transacao2.getValor(), 0.01);
	}

	@Test
	public void testaValorInvalido() {
		try {
			transacao1 = new Provento("Bolsa PIBIC", data1, 0,
					categoria1, "Mensal");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Valor inválido.", e.getMessage());
		}

		try {
			transacao1 = new Provento("Bolsa PIBIC", data1, -568.9,
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
			transacao1 = new Provento("Bolsa PIBIC", data1, 400.00,
					null, "Mensal");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Categoria inválida.", e.getMessage());
		}

		try {
			transacao2 = new Despesa("Gastos com material", data2,
					65.40, null, "Semestral");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Categoria inválida.", e.getMessage());
		}
	}

	@Test
	public void testaGetRecorrencia() {
		assertEquals("Mensal", transacao1.getRecorrencia());
		assertEquals("Semestral", transacao2.getRecorrencia());
	}

	@Test
	public void testaToString() {
		assertEquals("Descrição: Bolsa PIBIC"
				+ "\nData de Inserção: " + data1 + "\nValor: 400.0"
				+ "\nCategoria: Trabalho\nRecorrência: Mensal",
				transacao1.toString());

		assertEquals("Descrição: Gastos com material"
				+ "\nData de Inserção: " + data2 + "\nValor: 65.4"
				+ "\nCategoria: Universidade\nRecorrência: Semestral",
				transacao2.toString());
	}

	@Test
	public void testaEquals() throws Exception {
		assertFalse(transacao1.equals(transacao2));
		assertFalse(transacao2.equals(transacao1));
		assertTrue(transacao1.equals(transacao1));

		transacao2 = new Provento("Bolsa PIBIC", data1, 400.00,
				categoria1, "Mensal");
		assertTrue(transacao1.equals(transacao2));

		transacao2 = new Despesa("Bolsa PIBIC", data1, 400.00,
				categoria1, "Mensal");
		assertFalse(transacao1.equals(transacao2));
	}
}
