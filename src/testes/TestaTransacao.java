package testes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fonte.Categoria;
import fonte.Transacao;

public class TestaTransacao {
	
	private Categoria categoria1;
	private Transacao transacao1;
	private Transacao transacao2;
	private Categoria categoria2;
	
	@Before
	public void iniciaTransacao() throws Exception {
		categoria1 = new Categoria("Trabalho", "verde");
		categoria2 = new Categoria("Universidade", "Roxo");
		transacao1 = new Transacao("Provento", "Bolsa PIBIC", "06/08/2014", 400.00, categoria1, "Mensal");
		transacao2 = new Transacao("Despesa", "Gastos com material", "07/08/2014", 65.40, categoria2, "Semestral");
	}
	
	@Test
	public void testGetTipoDeTransacao() {
		assertEquals("Provento", transacao1.getTipoDeTransacao());
		assertEquals("Despesa", transacao2.getTipoDeTransacao());
	}
	
	@Test
	public void testTipoDeTransacaoInvalida() {
		try {
			transacao1 = new Transacao("", "Bosa Cnpq", "06/08/2014", 400.00, categoria1, "Mensal");
			fail("Esperava Excecao!");
		} catch (Exception e) {
			assertEquals("Tipo de transacao vazio!", e.getMessage());
		}
		
		try {
			transacao1 = new Transacao(null, "Bosa Cnpq", "06/08/2014", 400.00, categoria1, "Mensal");
			fail("Esperava Excecao!");
		} catch (Exception e) {
			assertEquals("Tipo de transacao vazio!", e.getMessage());
		}
	}
	
	@Test
	public void testGetDescricao() {
		assertEquals("Bolsa PIBIC", transacao1.getDescricao());
		assertEquals("Gastos com material", transacao2.getDescricao());
	}
	
	@Test
	public void testGetDataDeInsercao() {
		assertEquals("06/08/2014", transacao1.getDataDeInsercao());
		assertEquals("07/08/2014", transacao2.getDataDeInsercao());
	}
	
	@Test
	public void testGetValor() {
		assertEquals(400.00, transacao1.getValor(), 1);
		assertEquals(65.40, transacao2.getValor(), 1);
	}
	
	@Test
	public void testValorInvalido() {
		try {
			transacao1 = new Transacao("Provento", "Bolsa PIBIC", "06/08/2014", 0, categoria1, "Mensal");
			fail("Esperava Excecao!");
		} catch (Exception e) {
			assertEquals("Valor da transacao nao pode ser menor ou igual zero!", e.getMessage());
		}
		
		try {
			transacao1 = new Transacao("Provento", "Bolsa PIBIC", "06/08/2014", -568.9, categoria1, "Mensal");
			fail("Esperava Excecao!");
		} catch (Exception e) {
			assertEquals("Valor da transacao nao pode ser menor ou igual zero!", e.getMessage());
		}
	}
	
	@Test
	public void testGetCategoria() {
		assertEquals(categoria1, transacao1.getCategoria());
		assertEquals(categoria2, transacao2.getCategoria());
	}
	
	@Test
	public void testCategoriaInvalida() {
		try {
			transacao1 = new Transacao("Provento", "Bolsa PIBIC", "06/08/2014", 400.00, null, "Mensal");
			fail("Esperava Excecao!");
		} catch (Exception e) {
			assertEquals("Indique a categoria da transacao!", e.getMessage());
		}
		
		try {
			transacao2 = new Transacao("Despesa", "Gastos com material", "07/08/2014", 65.40, null, "Semestral");
			fail("Esperava Excecao!");
		} catch (Exception e) {
			assertEquals("Indique a categoria da transacao!", e.getMessage());
		}
	}
	
	@Test
	public void testGetRecorrencia() {
		assertEquals("Mensal", transacao1.getRecorrencia());
		assertEquals("Semestral", transacao2.getRecorrencia());
	}
	
	@Test
	public void testToString() {
		assertEquals("Tipo de transação: Provento\nDescrição: Bolsa PIBIC"
				+ "\nData de Inserção: 06/08/2014\nValor: 400.0"
				+ "\nCategoria: Trabalho\nRecorrência: Mensal", transacao1.toString());
		
		assertEquals("Tipo de transação: Despesa\nDescrição: Gastos com material"
				+ "\nData de Inserção: 07/08/2014\nValor: 65.4"
				+ "\nCategoria: Universidade\nRecorrência: Semestral", transacao2.toString());
	}
	
	@Test
	public void testEquals() throws Exception {
		assertFalse(transacao1.equals(transacao2));
		assertFalse(transacao2.equals(transacao1));
		assertTrue(transacao1.equals(transacao1));
		
		transacao2 = new Transacao("Provento", "Bolsa PIBIC", "06/08/2014", 400.00, categoria1, "Mensal");
		assertTrue(transacao1.equals(transacao2));
	}

}
