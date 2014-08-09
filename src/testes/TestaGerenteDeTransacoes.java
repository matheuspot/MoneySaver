package testes;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Test;
import fonte.GerenteDeTransacoes;
import fonte.Usuario;

public class TestaGerenteDeTransacoes {

	private GerenteDeTransacoes gerente;
	private Usuario usuario;

	@Test
	public void inicializaGerenteParaTestes() throws Exception {
		File arquivo = new File("data2.mos");
		arquivo.delete();

		usuario = new Usuario("usuario1", "usuario1@gmail.com", "123456",
				"nenhuma");
		gerente = new GerenteDeTransacoes(usuario);
	}
}
