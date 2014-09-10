package testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import excecao.MoneySaverException;
import fonte.Orcamento;

public class TestaOrcamento {

	private Orcamento orcamento;

	@Before
	public void iniciaOrcamento() throws MoneySaverException {
		orcamento = new Orcamento(50);
	}

	@Test
	public void testaOrcamentoInvalido() {
		try {
			orcamento = new Orcamento(-99);
			fail("Esperava excecao");
		} catch (MoneySaverException e) {
			assertEquals("Valor limite tem que ser positivo.", e.getMessage());
		}
	}

	@Test
	public void testaGetDataDeCriacao() {
		assertEquals(9, orcamento.getDataDeCriacao());
	}

	@Test
	public void testaToString() {
		assertEquals("Limite: 50,0", orcamento.toString());
	}

	@Test
	public void testaEquals() throws MoneySaverException {
		Orcamento orcamento2 = new Orcamento(60);
		Orcamento orcamento3 = new Orcamento(50);

		assertTrue(orcamento.equals(orcamento3));
		assertFalse(orcamento.equals(orcamento2));
	}

}
