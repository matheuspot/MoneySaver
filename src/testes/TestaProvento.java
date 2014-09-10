package testes;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import fonte.Categoria;
import fonte.Provento;
import fonte.Transacao;

public class TestaProvento {

	private Transacao provento;
	private Categoria categoria;
	private LocalDate dataDeInsercao = LocalDate.now();

	@Before
	public void iniciaProvento() throws Exception {
		try {
			categoria = new Categoria("Trabalho", "Vermelho");
			provento = new Provento("Salário", dataDeInsercao, 4863.50,
					categoria, "Mensal");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@Test
	public void testaGetValor() throws Exception {
		assertEquals(4863.5, provento.getValor(), 0.01);

		provento = new Provento("Bolsa", dataDeInsercao, 468.90, categoria,
				"Bimestral");
		assertEquals(468.9, provento.getValor(), 0.01);
	}
}
