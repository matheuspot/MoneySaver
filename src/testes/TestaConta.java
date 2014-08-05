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
	public void testaAdicionaDinheiro() {
		try {
			conta.adicionaDinheiro(100);
		} catch (Exception e) {
			fail("Exceção não deveria ter sido lançada.");
		}

		assertEquals("Saldo errado.", 100.0, conta.getSaldo(), 0.0);

		try {
			conta.adicionaDinheiro(-100);
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.",
					"Valor inválido, entre com um número maior que zero.",
					e.getMessage());
		}

		assertEquals("Saldo errado.", 100.0, conta.getSaldo(), 0.0);

		try {
			conta.adicionaDinheiro(0);
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.",
					"Valor inválido, entre com um número maior que zero.",
					e.getMessage());
		}

		assertEquals("Saldo errado.", 100.0, conta.getSaldo(), 0.0);
	}

	@Test
	public void testaRetiraDinheiro() {
		try {
			conta.retiraDinheiro(100);
		} catch (Exception e) {
			fail("Exceção não deveria ter sido lançada.");
		}

		assertEquals("Saldo errado.", -100.0, conta.getSaldo(), 0.0);

		try {
			conta.retiraDinheiro(-100);
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.",
					"Valor inválido, entre com um número maior que zero.",
					e.getMessage());
		}

		assertEquals("Saldo errado.", -100.0, conta.getSaldo(), 0.0);

		try {
			conta.retiraDinheiro(0);
			fail("Exceção deveria ter sido lançada.");
		} catch (Exception e) {
			assertEquals("Mensagem de erro errada.",
					"Valor inválido, entre com um número maior que zero.",
					e.getMessage());
		}

		assertEquals("Saldo errado.", -100.0, conta.getSaldo(), 0.0);
	}

	@Test
	public void testaToString() {
		assertEquals("ToString errado.", "Saldo da conta: 0.0",
				conta.toString());
	}
}
