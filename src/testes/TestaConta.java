package testes;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import fonte.Categoria;
import fonte.Conta;
import fonte.Despesa;
import fonte.Provento;
import fonte.Transacao;

public class TestaConta {

	private Conta conta;
	private Categoria categoria;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private LocalDate dataDeInsercao;
	private Transacao transacao1;
	private Conta conta2;
	private Despesa transacao2;

	@Before
	public void inicializaContaParaTestes() throws Exception {
		conta = new Conta("Bradesco");
		conta2 = new Conta("Bradesco");
		assertEquals("Saldo inicial errado.", 0.0, conta.getSaldo(), 0.0);
		categoria = new Categoria("Trabalho", "Marrom");
		dataDeInsercao = LocalDate.parse("06/08/2014", formatter);
		transacao1 = new Provento("Bolsa", dataDeInsercao, 400.00, categoria, "Mensal");
		transacao2 = new Despesa("Feira", dataDeInsercao, 152.32, categoria, "Mensal");
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
		assertEquals("ToString errado.", "Nome: Bradesco; Saldo: R$ 0,00", conta.toString());
	}
	
	@Test
	public void testaCriaContaInvalida() {
		try {
			conta = new Conta(null);
			fail("Esperava Excecao!");
		} catch (Exception e) {
			assertEquals("Nome da conta inválido.", e.getMessage());
		}
		
		try {
			conta = new Conta("");
			fail("Esperava Excecao!");
		} catch (Exception e) {
			assertEquals("Nome da conta inválido.", e.getMessage());
		}
	}
	
	@Test
	public void testaAdicionaTransacaoValida() throws Exception {
		
		assertFalse(conta.adicionaTransacao("Bolsa", dataDeInsercao, "400.0", categoria, 
					"Mensal", "provento"));
		assertEquals(1, conta.getTransacoesExistentes().size());
		
		assertFalse(conta.adicionaTransacao("Feira", dataDeInsercao, "152.32", categoria, 
					"Mensal", "despesa"));
		assertEquals(2, conta.getTransacoesExistentes().size());
	}
	
	@Test
	public void testaAdicionaTransacaoComDescricaoInvalida() {
		try {
			conta.adicionaTransacao(null, dataDeInsercao, "400.0", categoria, 
					"Mensal", "provento");
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Descrição inválida.", e.getMessage());
		}
		
		try {
			conta.adicionaTransacao("", dataDeInsercao, "400.0", categoria, 
					"Mensal", "provento");
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Descrição inválida.", e.getMessage());
		}
		
		try {
			conta.adicionaTransacao("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 
					dataDeInsercao, "400.0", categoria, 
					"Mensal", "provento");
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Descrição inválida.", e.getMessage());
		}
	}
	
	@Test
	public void testaAdicionaTransacaoComDataInvalida() {
		try {
			conta.adicionaTransacao("Bolsa", null, "400.0", categoria, 
					"Mensal", "provento");
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Data de inserção inválida.", e.getMessage());
		}
	}
	
	@Test
	public void testaAdicionaTransacaoComValorInvalido() {
		try {
			conta.adicionaTransacao("Bolsa", dataDeInsercao, "-354.6", categoria, 
					"Mensal", "provento");
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Valor inválido.", e.getMessage());
		}
		
		try {
			conta.adicionaTransacao("Bolsa", dataDeInsercao, "0", categoria, 
					"Mensal", "provento");
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Valor inválido.", e.getMessage());
		}
	}
	
	@Test
	public void testaAdicionaTransacaoComCategoriaInvalida() {
		try {
			conta.adicionaTransacao("Bolsa", dataDeInsercao, "564.8", null, 
					"Mensal", "provento");
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Categoria inválida.", e.getMessage());
		}
	}
	
	@Test
	public void testaAdicionaTransacaoComRecorrenciaInvalida() {
		try {
			conta.adicionaTransacao("Bolsa", dataDeInsercao, "564.8", categoria, 
					"", "provento");
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Recorrência inválida.", e.getMessage());
		}
		
		try {
			conta.adicionaTransacao("Bolsa", dataDeInsercao, "564.8", categoria, 
					null, "provento");
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Recorrência inválida.", e.getMessage());
		}
	}
	
	@Test
	public void testaAdicionaTransacaoComTipoInvalido() {
		try {
			conta.adicionaTransacao("Bolsa", dataDeInsercao, "564.8", categoria, 
					"Mensal", null);
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Tipo de transação inválido.", e.getMessage());
		}
		
		try {
			conta.adicionaTransacao("Bolsa", dataDeInsercao, "564.8", categoria, 
					"Mensal", "");
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Tipo de transação inválido.", e.getMessage());
		}
		
		try {
			conta.adicionaTransacao("Bolsa", dataDeInsercao, "564.8", categoria, 
					"Mensal", "outroTipo");
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Tipo de transação inválido.", e.getMessage());
		}
	}
	
	@Test
	public void testaGetSaldo() throws Exception {
		conta.adicionaTransacao("Bolsa", dataDeInsercao, "564.8", categoria, 
				"Mensal", "provento");
		assertEquals(564.8, conta.getSaldo(), 1);
		
		conta.adicionaTransacao("Feira", dataDeInsercao, "63.65", categoria, 
				"Mensal", "despesa");
		assertEquals(501.15, conta.getSaldo(), 2);
	}
	
	@Test
	public void testaGetNome() {
		assertEquals("Bradesco", conta.getNome());
	}
	
	@Test
	public void testaSetNomeValido() throws Exception {
		conta.setNome("Santander");
		assertEquals("Santander", conta.getNome());
		
		conta.setNome("Banco Do Brasil");
		assertEquals("Banco Do Brasil", conta.getNome());
	}
	
	@Test
	public void testaSetNomeInvalido() {
		try {
			conta.setNome("");
			fail("Esperava excecao!");
		} catch (Exception e) {
			assertEquals("Nome da conta inválido.", e.getMessage());
		}
		
		try {
			conta.setNome(null);
			fail("Esperava excecao!");
		} catch (Exception e) {
			assertEquals("Nome da conta inválido.", e.getMessage());
		}
	}
	
	@Test
	public void removeTransacaoValida() throws Exception {
		conta.adicionaTransacao("Bolsa", dataDeInsercao, "400.00", categoria, 
				"Mensal", "provento");
		assertEquals(1, conta.getTransacoesExistentes().size());
		
		conta.removeTransacao(transacao1);
		assertEquals(0, conta.getTransacoesExistentes().size());
	}
	
	@Test
	public void removeTransacaoInvalida() {
		try {
			conta.removeTransacao(transacao1);
			fail("Esperava excecao!");
		} catch (Exception e) {
			assertEquals("Transação inexistente.", e.getMessage());
		}
	}
	
	@Test
	public void testaEditaTransacao() {
		try {
			conta.adicionaTransacao("Bolsa", dataDeInsercao, "400.00", categoria, 
					"Mensal", "provento");
			conta.editaTransacao(transacao1, "Cobranca", dataDeInsercao, "79.8", categoria, 
					"Semanal", "provento");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testaEquals() throws Exception {
		conta.adicionaTransacao("Bolsa", dataDeInsercao, "400.00", categoria, 
				"Mensal", "provento");
		conta2.adicionaTransacao("Bolsa", dataDeInsercao, "400.00", categoria, 
				"Mensal", "provento");
		
		assertTrue(conta.equals(conta2));
	}
}
