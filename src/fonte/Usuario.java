package fonte;

import java.util.regex.*;

public class Usuario {

	private String nome, email, senha, dicaSenha;

	public Usuario(String nome, String email, String senha, String dicaSenha)
			throws Exception {
		if ((nome.equals("")) || (nome == null))
			throw new Exception("O usuário deve ser informado.");

		if ((senha.length() < 6) || (senha.length() > 8))
			throw new Exception("Senha inválida, deve conter 6 a 8 caracteres.");

		if (!validaEmail(email))
			throw new Exception("Email inválido.");

		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.dicaSenha = dicaSenha;
	}

	private boolean validaEmail(String email) {
		if (email == null || email.length() < 0)
			return false;

		Pattern p = Pattern
				.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
		Matcher m = p.matcher(email);

		if (m.find())
			return true;

		return false;
	}
	
	public boolean equals(Object objeto){
		if (!(objeto instanceof Usuario))
			return false;
		
		Usuario outro = (Usuario) objeto;
		if (email.equals(outro.getEmail()))
			return true;
		
		return false;
		
	}
	
	public String getNome(){
		return nome;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getDicaSenha(){
		return dicaSenha;
	}
	
	public String toString(){
		return "Nome: " + nome + " \nE-mail: " + email + " \nDica de senha: " + dicaSenha;
	}
}
