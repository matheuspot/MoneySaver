package testes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fonte.Categoria;
import fonte.Despesa;
import fonte.Transacao;

public class TestaDespesa {

	private Categoria categoria;
	private Transacao despesa;
	
	@Before
	public void iniciaProvento() throws Exception {
		categoria = new Categoria("Contas", "Azul");
		despesa = new Despesa("Conta de agua", "09/05/2014", 241.6, categoria, "Mensal");
	}
	
	@Test
	public void testaGetValor() throws Exception {
		assertEquals(-241.6, despesa.getValor(), 1);
		
		despesa = new Despesa("Conta de energia", "29/11/2013", 186.79, categoria, "Mensal");
		assertEquals(-186.79, despesa.getValor(), 2);
	}

}
