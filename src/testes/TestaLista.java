package testes;

import static org.junit.Assert.assertTrue;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import excecao.MoneySaverException;
import fonte.Categoria;
import fonte.Lista;
import fonte.Modo;
import fonte.Transacao;

public class TestaLista {

	private List<Transacao> listaFiltrada;
	private LocalDate dataDeInsercao;
	private Categoria categoria;
	private Transacao transacao1;
	private Transacao transacao2;
	Modo<?> lista;

	@Before
	public void iniciaLista() throws MoneySaverException {
		listaFiltrada = new ArrayList<>();
		dataDeInsercao = LocalDate.now();
		categoria = new Categoria("Trabalho", "Verde");
		transacao1 = new Transacao("Bolsa", dataDeInsercao, 400.00, categoria,
				"Mensal");
		transacao2 = new Transacao("Material de Trabalho", dataDeInsercao,
				79.85, categoria, "Semanal");
		listaFiltrada.add(transacao1);
		listaFiltrada.add(transacao2);
		lista = new Lista();
	}

	@Test
	public void testaGetTransacoesPreparada() {
		assertTrue(listaFiltrada.equals(lista
				.getTransacoesPreparada(listaFiltrada)));
	}

}
