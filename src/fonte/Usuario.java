package fonte;

import java.util.regex.*;

public class Usuario {

	private String nome, email, senha, dicaSenha;

	public Usuario(String nome, String email, String senha, String dicaSenha)
			throws Exception {
		if ((nome.equals("")) || (nome == null))
			throw new Exception("Nome não definido.");

		if ((senha.length() < 6) || (senha.length() > 8))
			throw new Exception("Senha com tamanho inválido.");

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
}
