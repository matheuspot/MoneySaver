package fonte;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import excecao.MoneySaverException;
import auxiliar.ArquivadorUsuarios;

/**
 * Classe para gerenciamento de usuários.
 */
public class GerenteDeUsuarios {

	private List<Usuario> usuariosDoSistema;
	private ArquivadorUsuarios arquivador;

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
			"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	/**
	 * Construtor da classe GerenteDeUsuarios, que não tem parâmetros.
	 */
	public GerenteDeUsuarios() {
		try {
			arquivador = new ArquivadorUsuarios("data.mos");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (arquivador.leUsuarios() == null) {
			usuariosDoSistema = new ArrayList<>();
		} else {
			usuariosDoSistema = new ArrayList<>(arquivador.leUsuarios());
		}
	}

	/**
	 * Método para adicionar um usuário ao sistema.
	 * 
	 * @param nome
	 *            Nome do usuário.
	 * @param email
	 *            E-mail do usuário.
	 * @param senha
	 *            Senha desejada do usuário.
	 * @param confirmacaoDeSenha
	 *            Confirmação da senha inserida pelo usuário.
	 * @param dicaDeSenha
	 *            Dica de senha do usuário.
	 * @param nomeDaConta
	 *            Nome da conta desejada.
	 * @throws MoneySaverException
	 *             Lança exceção caso algum parâmetro seja inválido ou se o
	 *             usuário já estiver cadastrado no sistema.
	 * @throws GeneralSecurityException
	 *             Lança exceção se houver problemas com a criptografia da
	 *             senha.
	 * @throws UnsupportedEncodingException
	 *             Lança exceção se houver problemas com a criptografia da
	 *             senha.
	 */
	public void adicionaUsuario(String nome, String email, String senha,
			String confirmacaoDeSenha, String dicaDeSenha, String nomeDaConta)
			throws MoneySaverException, UnsupportedEncodingException,
			GeneralSecurityException {

		usuarioValido(nome, email, senha, confirmacaoDeSenha, dicaDeSenha,
				nomeDaConta);

		Usuario novoUsuario = new Usuario(nome, email, senha, dicaDeSenha,
				nomeDaConta);
		usuariosDoSistema.add(novoUsuario);

		arquivador.escreveUsuarios(usuariosDoSistema);
	}

	/**
	 * Método para pesquisar um usuário no sistema.
	 * 
	 * @param email
	 *            E-mail do usuário a ser procurado.
	 * @return Retorna o usuário caso ele exista; caso contrário, retorna null.
	 */
	public Usuario pesquisaUsuario(String email) {
		for (Usuario usuario : usuariosDoSistema) {
			if (usuario.getEmail().equals(email))
				return usuario;
		}
		return null;
	}

	/**
	 * Método para fazer login do usuário.
	 * 
	 * @param login
	 *            E-mail do usuário.
	 * @param senha
	 *            Senha do usuário.
	 * @return Retorna o usuário que está logando.
	 * @throws MoneySaverException
	 *             Lança exceção caso o usuário não esteja cadastrado, ou a
	 *             senha estiver errada.
	 * @throws IOException
	 *             Lança exceção se houver problemas com a criptografia da
	 *             senha.
	 * @throws GeneralSecurityException
	 *             Lança exceção se houver problemas com a criptografia da
	 *             senha.
	 */
	public Usuario login(String login, String senha)
			throws MoneySaverException, GeneralSecurityException, IOException {
		Usuario usuario = pesquisaUsuario(login);

		if (usuario == null)
			throw new MoneySaverException(
					"Usuário não existe. Cadastre-se primeiro.");

		if (!usuario.checaLogin(senha))
			throw new MoneySaverException("Senha incorreta!");

		usuario.atualizaOrcamentoDeCategorias(LocalDate.now().getMonthValue());
		return usuario;
	}

	/**
	 * Método que serve para atualizar a movimentação de um usuário em um
	 * arquivo.
	 * 
	 * @param usuario
	 *            O usuário que estava movimentando a conta.
	 * @throws MoneySaverException
	 *             Lança exceção se houver problema com o arquivador.
	 */
	public void atualizaSistema(Usuario usuario) throws MoneySaverException {
		usuariosDoSistema.remove(usuario);
		usuariosDoSistema.add(usuario);
		arquivador.escreveUsuarios(usuariosDoSistema);
	}

	/**
	 * Método para checar se o usuário é um usuário válido.
	 * 
	 * @param nome
	 *            Nome do usuário.
	 * @param email
	 *            E-mail do usuário.
	 * @param senha
	 *            Senha do usuário.
	 * @param confirmacaoDeSenha
	 *            Confirmação de senha do usuário.
	 * @param dicaDeSenha
	 *            Dica de senha do usuário.
	 * @param nomeDaConta
	 *            Nome da conta desejada.
	 * @throws MoneySaverException
	 *             Lança exceção se o usuário não for valido, ou seja, pelo
	 *             menos um dos parâmetros estiver incorreto ou usuário já
	 *             existir.
	 */
	private void usuarioValido(String nome, String email, String senha,
			String confirmacaoDeSenha, String dicaDeSenha, String nomeDaConta)
			throws MoneySaverException {

		if (!nomeValido(nome))
			throw new MoneySaverException("Nome inválido.");
		if (!emailValido(email))
			throw new MoneySaverException("E-mail inválido ou já existe.");
		if (!senhaValida(senha, confirmacaoDeSenha))
			throw new MoneySaverException(
					"Senha inválida ou não confere com confirmação.");
		if (!dicaDeSenhaValida(dicaDeSenha))
			throw new MoneySaverException("Dica de senha inválida.");
		if (!nomeValido(nomeDaConta))
			throw new MoneySaverException("Nome da conta inválido.");
	}

	/**
	 * Método para verificar se um nome é válido.
	 * 
	 * @param nome
	 *            Um nome.
	 * @return Retorna true se for válido, e false caso contrário.
	 */
	private boolean nomeValido(String nome) {
		if (nome == null || nome.trim().isEmpty())
			return false;
		return true;
	}

	/**
	 * Método para verificar se o e-mail do usuário é válido.
	 * 
	 * @param email
	 *            E-mail do usuário.
	 * @return Retorna true se for válido, e false caso contrário.
	 */
	private boolean emailValido(String email) {
		if (email == null || email.trim().isEmpty() || emailJaExiste(email))
			return false;

		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}

	/**
	 * Método para verificar se o e-mail do usuário já existe.
	 * 
	 * @param email
	 *            E-mail do usuário.
	 * @return Retorna true se o e-mail já existe, e false caso contrário.
	 */
	private boolean emailJaExiste(String email) {
		if (pesquisaUsuario(email) != null)
			return true;
		return false;
	}

	/**
	 * Método para verificar se a senha do usuário é igual a confirmação de
	 * senha.
	 * 
	 * @param senha
	 *            Senha do usuário.
	 * @param confirmacaoDeSenha
	 *            Confirmação de senha do usuário.
	 * @return Retorna true se a senha for igual a confirmação de senha, e false
	 *         caso contrário.
	 */
	private boolean senhaValida(String senha, String confirmacaoDeSenha) {
		if (senha == null || senha.trim().isEmpty())
			return false;
		if (!(senha.equals(confirmacaoDeSenha)))
			return false;
		return true;
	}

	/**
	 * Método para verificar se a dica de senha do usuário é valida.
	 * 
	 * @param dicaDeSenha
	 *            Dica de senha do usuário.
	 * @return Retorna true se a dica de senha for válida, e false caso
	 *         contrário.
	 */
	private boolean dicaDeSenhaValida(String dicaDeSenha) {
		if (dicaDeSenha == null || dicaDeSenha.trim().isEmpty())
			return false;
		return true;
	}
}
