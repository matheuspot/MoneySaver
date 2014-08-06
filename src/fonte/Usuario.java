package fonte;

import java.io.Serializable;
import java.util.regex.*;

/**
 * Classe usada para representar um usuário.
 */

public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
			"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	private String nome;
	private String email;
	private String senha;
	private String dicaSenha;
	private Conta conta;

	/**
	 * Construtor da classe Usuario, que começa com uma conta de saldo zero.
	 * 
	 * @param nome
	 *            Nome do usuário.
	 * @param email
	 *            E-mail do usuário.
	 * @param senha
	 *            Senha do usuário.
	 * @param dicaSenha
	 *            Dica de senha do usuário.
	 * @throws Exception
	 *             Lança exceção se pelo menos um dos parâmetros for inválido.
	 */

	public Usuario(String nome, String email, String senha, String dicaSenha)
			throws Exception {
		if (!validaNome(nome))
			throw new Exception("O nome do usuário deve ser informado.");

		if (!validaSenha(senha))
			throw new Exception(
					"Senha inválida, deve conter de 6 a 8 caracteres.");

		if (!validaEmail(email))
			throw new Exception("E-mail inválido.");

		if (!validaDicaSenha(dicaSenha))
			throw new Exception("Dica de senha inválida.");

		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.dicaSenha = dicaSenha;
		conta = new Conta();
	}

	/**
	 * Método para verificar se a dica de senha do usuário é válida.
	 * 
	 * @param dicaSenha
	 *            Dica de senha do usuário.
	 * @return Retorna true se a dica de senha do usuário for válida, e false
	 *         caso contrário.
	 */

	private boolean validaDicaSenha(String dicaSenha) {
		if (dicaSenha.trim().length() == 0 || dicaSenha == null)
			return false;
		return true;
	}

	/**
	 * Método para verificar se o tamanho da senha está entre 6 e 8 caracteres.
	 * 
	 * @param senha
	 *            Senha do usuário.
	 * @return Retorna true se a senha do usuário for válida, e false caso
	 *         contrário.
	 */

	private boolean validaSenha(String senha) {
		if (senha.length() < 6 || senha.length() > 8)
			return false;
		return true;
	}

	/**
	 * Método para verificar se o nome do usuário é válido.
	 * 
	 * @param nome
	 *            Nome do usuário.
	 * @return Retorna true se o nome do usuário for válido, e false caso
	 *         contrário.
	 */

	private boolean validaNome(String nome) {
		if (nome.trim().length() == 0 || nome == null)
			return false;
		return true;
	}

	/**
	 * Método para verificar se o e-mail do usuário é válido.
	 * 
	 * @param email
	 *            E-mail do usuário.
	 * @return Retorna true se o e-mail do usuário for válido, e false caso
	 *         contrário.
	 */

	private boolean validaEmail(String email) {
		if (email == null || email.trim().length() == 0)
			return false;

		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}

	/**
	 * Método para pegar o nome do usuário.
	 * 
	 * @return O nome do usuário.
	 */

	public String getNome() {
		return nome;
	}

	/**
	 * Método para pegar o e-mail do usuário.
	 * 
	 * @return O e-mail do usuário.
	 */

	public String getEmail() {
		return email;
	}

	/**
	 * Método para pegar a dica de senha do usuário.
	 * 
	 * @return A dica de senha do usuário.
	 */

	public String getDicaSenha() {
		return dicaSenha;
	}

	/**
	 * Método para pegar a conta do usuário.
	 * 
	 * @return A conta do usuário.
	 */

	public Conta getConta() {
		return conta;
	}

	/**
	 * Override do método toString da classe Object.
	 */

	@Override
	public String toString() {
		return "Nome: " + nome + "\nE-mail: " + email + "\nDica de senha: "
				+ dicaSenha + "\n" + conta.toString();
	}

	/**
	 * Método para checagem do login. Irá verificar se a senha informada é igual
	 * a senha cadastrada no sistema.
	 * 
	 * @param senhaParaChecar
	 *            Senha que o usuário informou na hora do login.
	 * @return Retorna true se a senha for igual a senha cadastrada no sistema,
	 *         e false caso contrário.
	 */

	public boolean checaLogin(String senhaParaChecar) {
		if (senhaParaChecar.equals(senha))
			return true;
		return false;
	}

	/**
	 * Override do método hashCode da classe Object.
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	/**
	 * Override do método equals da classe Object. Dois usuários são iguais se
	 * eles tem o mesmo e-mail.
	 */

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
}
