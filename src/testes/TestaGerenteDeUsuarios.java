package testes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import fonte.GerenteDeUsuarios;

public class TestaGerenteDeUsuarios {

	private GerenteDeUsuarios gerente;

	@Before
	public void inicializaGerenteParaTestes() {
		gerente = new GerenteDeUsuarios();
	}

	@Test
	public void testAdicionaUsuarioValido() {
		fail("Not yet implemented");
	}

}
