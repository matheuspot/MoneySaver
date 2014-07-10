package testes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fonte.Usuario;

public class TestaUsuario {
	
	Usuario usuario1;
	
	@Test
	public void testCriaUsuario() {
		try {
			new Usuario("", "neymar@gmail.com", "123456", "123456");
		} catch(Exception e) {
			e.getMessage();
		}
		
		try {
			new Usuario("Neymar", "NeYmar@<gmail>.com", "123456", "123456");
		} catch(Exception e) {
			e.getMessage();
		}
		
		try {
			new Usuario("Neymar", "neymar@gmail.com", "1234", "123456");
		} catch(Exception e) {
			e.getMessage();
		}
		
		try {
			new Usuario("Neymar", "neymar@gmail.com", "123456789010", "123456");
		} catch(Exception e) {
			e.getMessage();
		}
		
	}
	
}
