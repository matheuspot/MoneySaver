package testes;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import excecao.MoneySaverException;
import fonte.Categoria;
import fonte.Despesa;
import fonte.Histograma;
import fonte.Lista;
import fonte.Modo;
import fonte.Provento;
import fonte.Transacao;

public class TestaHistograma {

	private List<Transacao> listaFiltrada;
	private LocalDate dataDeInsercao;
	private Categoria categoria;
	private Transacao transacao1;
	private Transacao transacao2;
	private Histograma histograma;

	@Before
	public void iniciaLista() throws MoneySaverException {
		listaFiltrada = new ArrayList<>();
		dataDeInsercao = LocalDate.now();
		categoria = new Categoria("Trabalho", "Verde");
		transacao1 = new Provento("Bolsa", dataDeInsercao, 400.00, categoria, "Mensal");
		transacao2 = new Despesa("Material de Trabalho", dataDeInsercao, 79.85, categoria, "Mensal");
		listaFiltrada.add(transacao1);
		listaFiltrada.add(transacao2);
		histograma = new Histograma();
	}
	
	@Test
	public void testaGetListaDeTransacoesPreparada() {
		double valorEsperado = histograma.getTransacoesPreparada(listaFiltrada).get(8);
		assertEquals(479.85, valorEsperado, 2);
	}

}
