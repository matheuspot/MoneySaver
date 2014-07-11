package fonte;

import java.util.regex.*;

public class GerenteDeUsuarios {

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
			"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public void adicionaUsuario(String nome, String email, String senha,
			String confirmacaoDeSenha, String dicaDeSenha) throws Exception {

		usuarioValido(nome, email, senha, confirmacaoDeSenha, dicaDeSenha);

		Usuario novoUsuario = new Usuario(nome, email, senha, dicaDeSenha);

		// Pega novoUsuario e adiciona ao arquivo do sistema
	}

	public Usuario pesquisaUsuario(String email) {
		// TODO: Pesquisar usuário pelo e-mail, acessando o arquivo
		return null;
	}

	public void login(String login, String senha) {
		// TODO: Implementar o login
	}

	private void usuarioValido(String nome, String email, String senha,
			String confirmacaoDeSenha, String dicaDeSenha) throws Exception {

		if (!nomeValido(nome))
			throw new Exception("Nome inválido ou já existe.");
		if (!emailValido(email))
			throw new Exception("E-mail inválido ou já existe.");
		if (!senhaValida(senha, confirmacaoDeSenha))
			throw new Exception("Senha inválida ou não confere com confirmação");
		if (!dicaDeSenhaValida(dicaDeSenha))
			throw new Exception("Dica de senha inválida. Tente de novo.");
	}

	private boolean nomeValido(String nome) {
		if (nome == null || nome.trim().length() == 0)
			return false;
		return true;
	}

	private boolean emailValido(String email) {
		if (email == null || email.trim().length() == 0 || emailJaExiste())
			return false;

		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}

	private boolean emailJaExiste() {
		// Abrir arquivo do sistema para checar se usuário já existe
		return false;
	}

	private boolean senhaValida(String senha, String confirmacaoDeSenha) {
		if (senha == null || senha.trim().length() == 0)
			return false;
		return true;
	}

	private boolean dicaDeSenhaValida(String dicaDeSenha) {
		if (dicaDeSenha == null || dicaDeSenha.trim().length() == 0)
			return false;
		return true;
	}
}
