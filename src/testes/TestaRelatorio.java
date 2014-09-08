package testes;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fonte.Categoria;
import fonte.Conta;
import fonte.Despesa;
import fonte.Provento;
import fonte.Relatorio;
import fonte.Transacao;

public class TestaRelatorio {

	private LocalDate dataDeInsercao;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	Conta conta;
	Categoria categoria1;
	Categoria categoria2;
	Transacao transacao1;
	Transacao transacao2;
	Relatorio relatorio;
	List<Transacao> listaTemp = new ArrayList<>();

	@Before
	public void iniciaRelatorio() throws Exception {
		dataDeInsercao = LocalDate.parse("06/09/2014", formatter);
		
		conta = new Conta("Bradesco");
		categoria1 = new Categoria("Trabalho", "Marrom");
		categoria2 = new Categoria("Compras", "Verde");
		transacao1 = new Provento("bolsa", dataDeInsercao, 400.00, categoria1, "Mensal");
		transacao2 = new Despesa("teclado", dataDeInsercao, 231.00, categoria2, "Mensal");
		conta.adicionaTransacao("bolsa", dataDeInsercao, "400.00", categoria1, "Mensal", "provento");
		conta.adicionaTransacao("teclado", dataDeInsercao, "231.00", categoria2, "Mensal", "despesa");
		
		relatorio = new Relatorio(conta);
	}
	
	@Test
	public void testaFiltraPorCategoria() {
		relatorio.filtraPorCategoria(categoria1);
		assertTrue(relatorio.getListaFiltrada().get(0).equals(transacao1));
	}
	
	@Test
	public void testaFiltraPorDespesa() {
		relatorio.filtraPorTipo("despesa");
		assertTrue(relatorio.getListaFiltrada().get(0).equals(transacao2));
	}
	
	@Test
	public void testaFiltraPorProvento() {
		relatorio.filtraPorTipo("provento");
		assertTrue(relatorio.getListaFiltrada().get(0).equals(transacao1));
	}
	
	@Test
	public void testaFiltraPorData() {
		relatorio.filtraPorData(1, 3);
		assertEquals(null, relatorio.getListaFiltrada().get(0));
	}

}
