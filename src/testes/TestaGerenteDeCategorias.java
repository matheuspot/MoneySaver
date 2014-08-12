package testes;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Before;
import org.junit.Test;
import fonte.Categoria;
import fonte.GerenteDeCategorias;
import fonte.Usuario;

public class TestaGerenteDeCategorias {

	private GerenteDeCategorias gerente;
	private Usuario usuario;
	private String[] categoriasExistentes;

	@Before
	public void inicializaGerenteParaTestes() throws Exception {
		File arquivo = new File("data3.mos");
		arquivo.delete();

		usuario = new Usuario("usuario1", "usuario1@gmail.com", "123456",
				"nenhuma");
		gerente = new GerenteDeCategorias(usuario);

		categoriasExistentes = new String[2];
		categoriasExistentes[0] = "Lazer";
		categoriasExistentes[1] = "Alimentação";
	}

	@Test
	public void testaCategoriasDefault() {
		assertArrayEquals(categoriasExistentes, gerente.listaCategorias());
	}

	@Test
	public void testaAdicionaCategoriaValida() throws Exception {
		gerente.adicionaCategoria("Fds", "Vermelho");
		gerente.adicionaCategoria("Cinema", "Laranja");

		categoriasExistentes = new String[4];
		categoriasExistentes[0] = "Lazer";
		categoriasExistentes[1] = "Alimentação";
		categoriasExistentes[2] = "Fds";
		categoriasExistentes[3] = "Cinema";

		assertArrayEquals(categoriasExistentes, gerente.listaCategorias());
	}

	@Test
	public void testaAdicionaCategoriaInvalida() throws Exception {
		try {
			gerente.adicionaCategoria("", "Verde");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Nome de categoria inválido.", e.getMessage());
		}

		try {
			gerente.adicionaCategoria(null, "Verde");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Nome de categoria inválido.", e.getMessage());
		}

		gerente.adicionaCategoria("Fds", "Vermelho");

		try {
			gerente.adicionaCategoria("Fds", "Vermelho");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Categoria já existe.", e.getMessage());
		}

		categoriasExistentes = new String[3];
		categoriasExistentes[0] = "Lazer";
		categoriasExistentes[1] = "Alimentação";
		categoriasExistentes[2] = "Fds";

		assertArrayEquals(categoriasExistentes, gerente.listaCategorias());
	}

	@Test
	public void testaRemoveCategoriaValida() throws Exception {
		Categoria categoria = new Categoria("Fds", "Vermelho");
		gerente.adicionaCategoria("Fds", "Vermelho");
		gerente.removeCategoria(categoria);

		assertArrayEquals(categoriasExistentes, gerente.listaCategorias());
	}

	@Test
	public void testaRemoveCategoriaInvalida() throws Exception {
		Categoria categoria = new Categoria("Fds", "Vermelho");

		try {
			gerente.removeCategoria(null);
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Categoria inexistente.", e.getMessage());
		}

		try {
			gerente.removeCategoria(categoria);
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Categoria inexistente.", e.getMessage());
		}

		assertArrayEquals(categoriasExistentes, gerente.listaCategorias());
	}

	@Test
	public void testaEditaCategoriaValida() throws Exception {
		Categoria categoria = new Categoria("Fds", "Vermelho");
		gerente.adicionaCategoria("Fds", "Vermelho");

		gerente.editaCategoria(categoria, "FdsEditado", "VermelhoEditado");

		categoriasExistentes = new String[3];
		categoriasExistentes[0] = "Lazer";
		categoriasExistentes[1] = "Alimentação";
		categoriasExistentes[2] = "FdsEditado";

		assertArrayEquals(categoriasExistentes, gerente.listaCategorias());
	}

	@Test
	public void testaEditaCategoriaInvalida() throws Exception {
		Categoria categoria = new Categoria("Fds", "Vermelho");

		try {
			gerente.editaCategoria(null, "Nada", "Nenhuma");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Categoria inexistente.", e.getMessage());
		}

		try {
			gerente.editaCategoria(categoria, "Nada", "Nenhuma");
			fail("Esperava exceção.");
		} catch (Exception e) {
			assertEquals("Categoria inexistente.", e.getMessage());
		}

		assertArrayEquals(categoriasExistentes, gerente.listaCategorias());
	}
}
