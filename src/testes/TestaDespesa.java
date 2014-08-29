package testes;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import sun.util.locale.provider.LocaleDataMetaInfo;
import sun.util.resources.LocaleData;
import fonte.Categoria;
import fonte.Despesa;
import fonte.Transacao;

public class TestaDespesa {

	private Categoria categoria;
	private Transacao despesa;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private LocalDate data = LocalDate.parse("21/08/2014", formatter);
	
	@Before
	public void iniciaDespesa() throws Exception {
		categoria = new Categoria("Contas", "Azul");
		despesa = new Despesa("Conta de água", data, 241.6, categoria, "Mensal");
	}
	
	@Test
	public void testaGetValor() throws Exception {
		assertEquals(-241.6, despesa.getValor(), 0.01);
		
		data = LocalDate.parse("26/09/2014", formatter);
		despesa = new Despesa("Conta de energia", data, 186.79, categoria, "Mensal");
		assertEquals(-186.79, despesa.getValor(), 0.01);
	}

}
