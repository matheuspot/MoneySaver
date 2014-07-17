package fonte;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import auxiliar.Arquivador;

public class GerenteDeUsuarios {

	private ArrayList<Usuario> usuariosDoSistema;
	private Arquivador arquivador;

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
			"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	/**
	 * Construtor da classe GerenteDeUsuarios
	 */
	public GerenteDeUsuarios() {
		try {
			arquivador = new Arquivador("usuarios");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		if (arquivador.leUsuarios() == null) {
			usuariosDoSistema = new ArrayList<>();
		} else {
			usuariosDoSistema = new ArrayList<>(arquivador.leUsuarios());
		}
	}

	/**
	 * Adiciona um usuario na lista de usuarios
	 * @param nome
	 * @param email
	 * @param senha
	 * @param confirmacaoDeSenha
	 * @param dicaDeSenha
	 * @throws Exception
	 */
	public void adicionaUsuario(String nome, String email, String senha,
			String confirmacaoDeSenha, String dicaDeSenha) throws Exception {

		usuarioValido(nome, email, senha, confirmacaoDeSenha, dicaDeSenha);

		Usuario novoUsuario = new Usuario(nome, email, senha, dicaDeSenha);
		usuariosDoSistema.add(novoUsuario);

		arquivador.escreveUsuarios(usuariosDoSistema);
	}

	/**
	 * Retorna objeto de tipo Usuario pela string do email
	 * @param email
	 * @return object
	 */
	public Usuario pesquisaUsuario(String email) {
		for (Usuario usuario : usuariosDoSistema) {
			if (usuario.getEmail().equals(email))
				return usuario;
		}
		return null;
	}

	/**
	 * Faz login de usuario
	 * @param login
	 * @param senha
	 * @throws Exception
	 */
	public void login(String login, String senha) throws Exception {
		Usuario usuario = pesquisaUsuario(login);

		if (usuario == null)
			throw new Exception("Usuário não existe. Cadastre-se primeiro.");

		if (!usuario.checaLogin(senha))
			throw new Exception("Senha incorreta!");

		// Chegou aqui, já está logado. Leva o usuário para outra tela da
		// interface que contém as operações.
	}

	/**
	 * Checa se usuario cadastrado é valido
	 * @param nome
	 * @param email
	 * @param senha
	 * @param confirmacaoDeSenha
	 * @param dicaDeSenha
	 * @throws Exception
	 */
	private void usuarioValido(String nome, String email, String senha,
			String confirmacaoDeSenha, String dicaDeSenha) throws Exception {

		if (!nomeValido(nome))
			throw new Exception("Nome inválido.");
		if (!emailValido(email))
			throw new Exception("E-mail inválido ou já existe.");
		if (!senhaValida(senha, confirmacaoDeSenha))
			throw new Exception(
					"Senha inválida ou não confere com confirmação.");
		if (!dicaDeSenhaValida(dicaDeSenha))
			throw new Exception("Dica de senha inválida. Tente de novo.");
	}

	/**
	 * Verifica se o nome do usuario é valido
	 * @param nome
	 * @return boolean
	 */
	private boolean nomeValido(String nome) {
		if (nome == null || nome.trim().length() == 0)
			return false;
		return true;
	}

	/**
	 * Verifica se o email do usuario é valido
	 * @param email
	 * @return boolean
	 */
	private boolean emailValido(String email) {
		if (email == null || email.trim().length() == 0 || emailJaExiste(email))
			return false;

		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}

	/**
	 * Verifica se o email já está cadastrado
	 * @param email
	 * @return boolean
	 */
	private boolean emailJaExiste(String email) {
		if (pesquisaUsuario(email) != null)
			return true;
		return false;
	}

	/**
	 * Verifica se a senha é valida
	 * @param senha
	 * @param confirmacaoDeSenha
	 * @return boolean
	 */
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
