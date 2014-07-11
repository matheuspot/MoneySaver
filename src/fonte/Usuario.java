package fonte;

import java.util.regex.*;

public class Usuario {

	private final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
			"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	private String nome, email, senha, dicaSenha;

	public Usuario(String nome, String email, String senha, String dicaSenha)
			throws Exception {
		if ((nome.equals("")) || (nome == null))
			throw new Exception("O usuário deve ser informado.");

		if ((senha.length() < 6) || (senha.length() > 8))
			throw new Exception("Senha inválida, deve conter 6 a 8 caracteres.");

		if (!validaEmail(email))
			throw new Exception("E-mail inválido.");

		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.dicaSenha = dicaSenha;
	}

	private boolean validaEmail(String email) {
		if (email == null || email.length() < 0)
			return false;

		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();

	}

	public boolean equals(Object objeto) {
		if (!(objeto instanceof Usuario))
			return false;

		Usuario outro = (Usuario) objeto;
		if (email.equals(outro.getEmail()))
			return true;

		return false;

	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getDicaSenha() {
		return dicaSenha;
	}

	public String toString() {
		return "Nome: " + nome + " \nE-mail: " + email + " \nDica de senha: "
				+ dicaSenha;
	}
}
