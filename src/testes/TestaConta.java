package testes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import fonte.Conta;

public class TestaConta {

	private Conta conta;

	@Before
	public void inicializaContaParaTestes() {
		conta = new Conta();
		assertEquals("Saldo inicial errado.", 0.0, conta.getSaldo(), 0.0);
	}

	@Test
	public void testaMoveDinheiroNaConta() {
		assertEquals(0.0, conta.getSaldo(), 0.0);
		conta.moveDinheiroNaConta(5);
		assertEquals(5.0, conta.getSaldo(), 0.0);
		conta.moveDinheiroNaConta(10);
		assertEquals(15.0, conta.getSaldo(), 0.0);
		conta.moveDinheiroNaConta(-15);
		assertEquals(0.0, conta.getSaldo(), 0.0);
		conta.moveDinheiroNaConta(-15);
		assertEquals(-15.0, conta.getSaldo(), 0.0);
	}

	@Test
	public void testaToString() {
		assertEquals("ToString errado.", "R$ 0,00", conta.toString());
	}
}
