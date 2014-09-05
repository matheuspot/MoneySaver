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
		transacao1 = new Provento("Bolsa", 400.00, categoria, "Mensal");
		transacao2 = new Despesa("Feira", 152.32, categoria, "Mensal");
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
		
		assertFalse(conta.adicionaTransacao("Bolsa", "400.0", categoria, 
					"Mensal", "provento"));
		assertEquals(1, conta.getTransacoesExistentes().size());
		
		categoria.setOrcamento(200.00);
		
		assertTrue(conta.adicionaTransacao("Feira", "220.00", categoria, 
					"Mensal", "despesa"));
		assertEquals(2, conta.getTransacoesExistentes().size());
		
		categoria.setOrcamento(600.00);
		
		assertFalse(conta.adicionaTransacao("Feira", "120.00", categoria, 
				"Mensal", "despesa"));
		assertEquals(3, conta.getTransacoesExistentes().size());
	}
	
	@Test
	public void testaAdicionaTransacaoComDescricaoInvalida() {
		try {
			conta.adicionaTransacao(null, "400.0", categoria, 
					"Mensal", "provento");
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Descrição inválida.", e.getMessage());
		}
		
		try {
			conta.adicionaTransacao("", "400.0", categoria, 
					"Mensal", "provento");
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Descrição inválida.", e.getMessage());
		}
		
		try {
			conta.adicionaTransacao("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 
					 "400.0", categoria, 
					"Mensal", "provento");
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Descrição inválida.", e.getMessage());
		}
	}
	
	@Test
	public void testaAdicionaTransacaoComValorInvalido() {
		try {
			conta.adicionaTransacao("Bolsa", "-354.6", categoria, 
					"Mensal", "provento");
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Valor inválido.", e.getMessage());
		}
		
		try {
			conta.adicionaTransacao("Bolsa", "0", categoria, 
					"Mensal", "provento");
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Valor inválido.", e.getMessage());
		}
	}
	
	@Test
	public void testaAdicionaTransacaoComCategoriaInvalida() {
		try {
			conta.adicionaTransacao("Bolsa", "564.8", null, 
					"Mensal", "provento");
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Categoria inválida.", e.getMessage());
		}
	}
	
	@Test
	public void testaAdicionaTransacaoComRecorrenciaInvalida() {
		try {
			conta.adicionaTransacao("Bolsa", "564.8", categoria, 
					"", "provento");
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Recorrência inválida.", e.getMessage());
		}
		
		try {
			conta.adicionaTransacao("Bolsa", "564.8", categoria, 
					null, "provento");
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Recorrência inválida.", e.getMessage());
		}
	}
	
	@Test
	public void testaAdicionaTransacaoComTipoInvalido() {
		try {
			conta.adicionaTransacao("Bolsa", "564.8", categoria, 
					"Mensal", null);
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Tipo de transação inválido.", e.getMessage());
		}
		
		try {
			conta.adicionaTransacao("Bolsa", "564.8", categoria, 
					"Mensal", "");
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Tipo de transação inválido.", e.getMessage());
		}
		
		try {
			conta.adicionaTransacao("Bolsa", "564.8", categoria, 
					"Mensal", "outroTipo");
			fail("Esperava excecao");
		} catch (Exception e) {
			assertEquals("Tipo de transação inválido.", e.getMessage());
		}
	}
	
	@Test
	public void testaGetSaldo() throws Exception {
		conta.adicionaTransacao("Bolsa", "564.8", categoria, 
				"Mensal", "provento");
		assertEquals(564.8, conta.getSaldo(), 1);
		
		conta.adicionaTransacao("Feira", "63.65", categoria, 
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
		conta.adicionaTransacao("Bolsa", "400.00", categoria, 
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
			conta.adicionaTransacao("Bolsa", "400.00", categoria, 
					"Mensal", "provento");
			conta.editaTransacao(transacao1, "Cobranca", "79.8", categoria, 
					"Semanal", "provento");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testaEquals() throws Exception {
		conta.adicionaTransacao("Bolsa", "400.00", categoria, 
				"Mensal", "provento");
		conta2.adicionaTransacao("Bolsa", "400.00", categoria, 
				"Mensal", "provento");
		
		assertTrue(conta.equals(conta2));
	}
	
	@Test
	public void testaCalculaGastoPorCategoria() throws Exception {
		categoria.setOrcamento(200.00);
		
		assertTrue(conta.adicionaTransacao("Feira", "250.00", 
				categoria, "Mensal", "despesa"));
	}
	
	@Test
	public void testaSaldoRecorrenteMensal() throws Exception {
		conta.adicionaTransacao("Bolsa", "200.00", categoria, 
				"Mensal", "provento");
		
		assertEquals(600.00, conta.calculaSaldoRecorrente(), 0);
		
		conta.adicionaTransacao("Feira", "50.00", categoria, 
				"Semanal", "despesa");
		
		assertEquals(400.00, conta.calculaSaldoRecorrente(), 0);
	}
}
