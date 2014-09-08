package testes;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import fonte.Categoria;
import fonte.Provento;
import fonte.Transacao;

public class TestaProvento {

	private Transacao provento;
	private Categoria categoria;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private LocalDate data = LocalDate.parse("01/04/2014", formatter);
	
	@Before
	public void iniciaProvento() throws Exception {
		categoria = new Categoria("Trabalho", "Vermelho");
		provento = new Provento("Salário", 4863.50, categoria, "Mensal");
	}
	
	@Test
	public void testaGetValor() throws Exception {
		assertEquals(4863.5, provento.getValor(), 0.01);
		
		data = LocalDate.parse("12/11/2014", formatter);
		provento = new Provento("Bolsa", 468.90, categoria, "Bimestral");
		assertEquals(468.9, provento.getValor(), 0.01);
	}
}
