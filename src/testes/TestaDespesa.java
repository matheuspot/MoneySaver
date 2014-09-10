package testes;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import fonte.Categoria;
import fonte.Despesa;
import fonte.Transacao;

public class TestaDespesa {

	private Categoria categoria;
	private Transacao despesa;
	private LocalDate dataDeInsercao = LocalDate.now();

	@Before
	public void iniciaDespesa() {
		try {
			categoria = new Categoria("Contas", "Azul");
			despesa = new Despesa("Conta de água", dataDeInsercao, 241.6,
					categoria, "Mensal");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@Test
	public void testaGetValor() throws Exception {
		assertEquals(-241.6, despesa.getValor(), 0.01);
		despesa = new Despesa("Conta de energia", dataDeInsercao, 186.79,
				categoria, "Mensal");
		assertEquals(-186.79, despesa.getValor(), 0.01);
	}

}
