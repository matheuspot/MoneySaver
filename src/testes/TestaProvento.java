package testes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import fonte.Categoria;
import fonte.Provento;
import fonte.Transacao;

public class TestaProvento {

	private Transacao provento;
	private Categoria categoria;

	@Before
	public void iniciaProvento() throws Exception {
		categoria = new Categoria("Trabalho", "Vermelho");
		provento = new Provento("Salário", "15/09/2013", 4863.50, categoria, "Mensal");
	}
	
	@Test
	public void testaGetValor() throws Exception {
		assertEquals(4863.5, provento.getValor(), 0.01);
		
		provento = new Provento("Bolsa", "25/09/2013", 468.90, categoria, "Bimestral");
		assertEquals(468.9, provento.getValor(), 0.01);
	}
}
