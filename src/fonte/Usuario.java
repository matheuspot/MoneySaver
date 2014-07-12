package fonte;

import java.io.Serializable;
import java.util.regex.*;

public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
			"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	private String nome;
	private String email;
	private String senha;
	private String dicaSenha;

	public Usuario(String nome, String email, String senha, String dicaSenha)
			throws Exception {
		if (!validaNome(nome))
			throw new Exception("O usuário deve ser informado.");

		if (!validaSenha(senha))
			throw new Exception("Senha inválida, deve conter 6 a 8 caracteres.");

		if (!validaEmail(email))
			throw new Exception("E-mail inválido.");

		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.dicaSenha = dicaSenha;
	}

	private boolean validaSenha(String senha) {
		if (senha.length() < 6 || senha.length() > 8)
			return false;
		return true;
	}

	private boolean validaNome(String nome) {
		if (nome.trim().length() == 0 || nome == null)
			return false;
		return true;
	}

	private boolean validaEmail(String email) {
		if (email == null || email.trim().length() == 0)
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
		return "Nome: " + nome + "\nE-mail: " + email + "\nDica de senha: "
				+ dicaSenha;
	}

	public boolean checaLogin(String senhaParaChecar) {
		if (senhaParaChecar.equals(senha))
			return true;
		return false;
	}
}
