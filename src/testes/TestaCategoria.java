package testes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import fonte.Categoria;

public class TestaCategoria {

	private Categoria categoria1;
	private Categoria categoria2;

	@Before
	public void iniciaCategoria() throws Exception {
		categoria1 = new Categoria("contas", "amarelo");
		categoria2 = new Categoria("eletronicos", "verde");
	}

	@Test
	public void testaGetNomeValido() throws Exception {
		assertEquals("contas", categoria1.getNome());
		assertEquals("eletronicos", categoria2.getNome());

		categoria1 = new Categoria("restaurante", "amarelo");
		assertEquals("restaurante", categoria1.getNome());

		categoria2 = new Categoria("lazer", "verde");
		assertEquals("lazer", categoria2.getNome());
	}

	@Test
	public void testaGetNomeInvalido() {
		try {
			categoria1 = new Categoria("", "Roxo");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Nome inválido.", e.getMessage());
		}

		try {
			categoria1 = new Categoria(null, "Marrom");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Nome inválido.", e.getMessage());
		}
	}

	@Test
	public void testaGetCorValida() throws Exception {
		assertEquals("amarelo", categoria1.getCor());
		assertEquals("verde", categoria2.getCor());

		categoria1 = new Categoria("restaurante", "vermelho");
		assertEquals("vermelho", categoria1.getCor());

		categoria2 = new Categoria("lazer", "preto");
		assertEquals("preto", categoria2.getCor());
	}

	@Test
	public void testaGetCorInvalida() {
		try {
			categoria1 = new Categoria("Lazer", "");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Cor inválida.", e.getMessage());
		}

		try {
			categoria1 = new Categoria("Restaurante", null);
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Cor inválida.", e.getMessage());
		}
	}

	@Test
	public void testaToString() throws Exception {
		assertEquals("Nome: contas\nCor: amarelo", categoria1.toString());
		assertEquals("Nome: eletronicos\nCor: verde", categoria2.toString());

		categoria1 = new Categoria("restaurante", "vermelho");
		assertEquals("Nome: restaurante\nCor: vermelho", categoria1.toString());

		categoria2 = new Categoria("lazer", "preto");
		assertEquals("Nome: lazer\nCor: preto", categoria2.toString());
	}

	@Test
	public void testaEquals() throws Exception {
		assertFalse(categoria1.equals(categoria2));
		assertFalse(categoria2.equals(categoria1));
		assertTrue(categoria1.equals(categoria1));

		categoria1 = new Categoria("eletronicos", "amarelo");
		assertFalse(categoria1.equals(categoria2));

		categoria1 = new Categoria("lazer", "verde");
		assertFalse(categoria1.equals(categoria2));

		categoria2 = new Categoria("lazer", "verde");
		assertTrue(categoria1.equals(categoria2));
	}
}
