package fonte;

import java.util.ArrayList;
import java.util.regex.*;
import auxiliar.Arquivador;

public class GerenteDeUsuarios {

	private ArrayList<Usuario> usuariosDoSistema;
	private Arquivador arquivador;

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
			"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

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

	public void adicionaUsuario(String nome, String email, String senha,
			String confirmacaoDeSenha, String dicaDeSenha) throws Exception {

		usuarioValido(nome, email, senha, confirmacaoDeSenha, dicaDeSenha);

		Usuario novoUsuario = new Usuario(nome, email, senha, dicaDeSenha);
		usuariosDoSistema.add(novoUsuario);

		arquivador.escreveUsuarios(usuariosDoSistema);
	}

	public Usuario pesquisaUsuario(String email) {
		for (Usuario usuario : usuariosDoSistema) {
			if (usuario.getEmail().equals(email))
				return usuario;
		}
		return null;
	}

	public void login(String login, String senha) throws Exception {
		Usuario usuario = pesquisaUsuario(login);

		if (usuario == null)
			throw new Exception("Usuário não existe. Cadastre-se primeiro.");

		if (!usuario.checaLogin(senha))
			throw new Exception("Senha incorreta!");

		// Chegou aqui, já está logado. Leva o usuário para outra tela da
		// interface que contém as operações.
	}

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

	private boolean nomeValido(String nome) {
		if (nome == null || nome.trim().length() == 0)
			return false;
		return true;
	}

	private boolean emailValido(String email) {
		if (email == null || email.trim().length() == 0 || emailJaExiste(email))
			return false;

		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}

	private boolean emailJaExiste(String email) {
		if (pesquisaUsuario(email) != null)
			return true;
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
