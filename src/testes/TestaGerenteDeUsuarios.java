package testes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import fonte.GerenteDeUsuarios;
import fonte.Usuario;

public class TestaGerenteDeUsuarios {

	private GerenteDeUsuarios gerente;

	@Before
	public void inicializaGerenteParaTestes() {
		gerente = new GerenteDeUsuarios();
	}

	@Test
	public void testAdicionaUsuarioValido() {
		fail();
	}
}
