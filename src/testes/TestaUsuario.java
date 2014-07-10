package testes;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fonte.Usuario;

public class TestaUsuario {
	
<<<<<<< HEAD
	Usuario usuario1;
=======
	private Usuario usuario1, usuario2, usuario3;
	
	@Before
	public void criaUsuario() throws Exception{
		usuario1 = new Usuario("Neymar", "neymar@gmail.com", "123456", "dica1");
		usuario2 = new Usuario("Neymar Junior", "neymar@gmail.com", "12345678", "dica2");
		usuario3 = new Usuario("Neymar Luiz", "neymar@hotmail.com", "1234567", "dica3");
	}
>>>>>>> fbcfefc7f3ae130df26230e379f044ceeb1d9888
	
	@Test
	public void testaCriarUsuario() throws Exception {
		new Usuario("Neymar", "NeYmar@gmail.com", "123456", "dica");
		new Usuario("Neymar Andrade", "neymar@yahoo.com", "12345678", "dica2");
		
		try {
			new Usuario("", "neymar@gmail.com", "123456", "123456");
			fail("Exceção deve ser lançada");
		} catch(Exception e) {
			assertEquals("Mensagem de erro errada.", "O usuário deve ser informado.", e.getMessage());
		}
		
		try {
			new Usuario("Neymar", "NeYmar@<gmail>.com", "123456", "123456");
<<<<<<< HEAD
=======
			fail("Exceção deve ser lançada");
>>>>>>> fbcfefc7f3ae130df26230e379f044ceeb1d9888
		} catch(Exception e) {
			assertEquals("Mensagem de erro errada.", "Email inválido.",	e.getMessage());
		}
		
		try {
			new Usuario("Neymar", "neymar@gmail.com", "1234", "123456");
			fail("Exceção deve ser lançada");
		} catch(Exception e) {
			assertEquals("Mensagem de erro errada.", "Senha inválida, deve conter 6 a 8 caracteres.", e.getMessage());
		}
		
		try {
			new Usuario("Neymar", "neymar@gmail.com", "123456789010", "123456");
			fail("Exceção deve ser lançada");
		} catch(Exception e) {
			assertEquals("Mensagem de erro errada.", "Senha inválida, deve conter 6 a 8 caracteres.", e.getMessage());
		}
		
	}
	
<<<<<<< HEAD
=======
	@Test
	public void testaToString(){
		assertEquals("Nome: Neymar \nE-mail: neymar@gmail.com \nDica de senha: dica1", usuario1.toString());
		assertEquals("Nome: Neymar Junior \nE-mail: neymar@gmail.com \nDica de senha: dica2", usuario2.toString());
		assertEquals("Nome: Neymar Luiz \nE-mail: neymar@hotmail.com \nDica de senha: dica3", usuario3.toString());
	}
	
	@Test
	public void testaEquals(){
		assertTrue(usuario1.equals(usuario2));
		assertFalse(usuario1.equals(usuario3));
	}

>>>>>>> fbcfefc7f3ae130df26230e379f044ceeb1d9888
}
