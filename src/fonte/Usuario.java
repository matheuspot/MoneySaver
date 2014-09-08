package fonte;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import auxiliar.Criptografia;

/**
 * Classe usada para representar um usuário.
 */
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
			"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	private final String nome;
	private final String email;
	private final String senha;
	private final String dicaSenha;
	private Conta contaAtiva;
	private List<Conta> contas;
	private List<Categoria> categorias;

	/**
	 * Construtor de usuário.
	 * 
	 * @param nome
	 *            O nome.
	 * @param email
	 *            O e-mail.
	 * @param senha
	 *            A senha.
	 * @param dicaSenha
	 *            A dica de senha.
	 * @param nomeDaConta
	 *            O nome da conta.
	 * @throws Exception
	 *             Lança exceção se pelo menos um dos parâmetros for inválido.
	 */
	public Usuario(String nome, String email, String senha, String dicaSenha,
			String nomeDaConta) throws Exception {
		usuarioValido(nome, email, senha, dicaSenha, nomeDaConta);

		this.nome = nome;
		this.email = email;
		this.senha = Criptografia.encrypt(senha);
		this.dicaSenha = dicaSenha;
		contaAtiva = new Conta(nomeDaConta);
		contas = new ArrayList<>();
		categorias = new ArrayList<>();

		contas.add(contaAtiva);
		categorias.add(new Categoria("Alimentação", "334db3"));
		categorias.add(new Categoria("Lazer", "669966"));
	}

	/**
	 * Método que irá adicionar uma conta para o usuário.
	 * 
	 * @param nome
	 *            O nome da conta.
	 * @throws Exception
	 *             Lança exceção se o nome da conta for inválido.
	 */
	public void adicionaConta(String nome) throws Exception {
		if (!validaNome(nome))
			throw new Exception("Nome da conta inválido.");

		for (Conta conta : contas) {
			if (conta.getNome().equals(nome))
				throw new Exception("Conta já existe.");
		}

		contas.add(new Conta(nome));
	}

	/**
	 * Método que irá remover uma conta do usuário.
	 * 
	 * @param conta
	 *            A conta que deseja-se remover.
	 * @throws Exception
	 *             Lança exceção se a conta não existir.
	 */
	public void removeConta(Conta conta) throws Exception {
		if (contas.size() > 1){
			checaContaJaExiste(conta);
			contas.remove(conta);
			if (conta == contaAtiva)
				contaAtiva = contas.get(0);
		} else {
			throw new Exception("Deve existir pelo menos uma conta ativa.");
		}
	}

	/**
	 * Método que irá editar uma conta do usuário.
	 * 
	 * @param contaParaEditar
	 *            A conta que deseja-se editar.
	 * @param nome
	 *            O novo nome da conta.
	 * @throws Exception
	 *             Lança exceção se o nome for inválido ou a conta para editar
	 *             não existir.
	 */
	public void editaConta(Conta contaParaEditar, String nome) throws Exception {
		if (!validaNome(nome))
			throw new Exception("Nome da conta inválido.");

		checaContaJaExiste(contaParaEditar);
		
		this.pesquisaConta(contaParaEditar.getNome()).setNome(nome);
	}

	/**
	 * Método usado para pesquisar uma conta a partir do seu nome.
	 * 
	 * @param nome
	 *            O nome da conta.
	 * @return Retorna a conta se ela existir, e retorna null se a conta com
	 *         esse nome não existir.
	 * @throws Exception
	 *             Lança exceção se o nome for inválido.
	 */
	public Conta pesquisaConta(String nome) {
		for (Conta conta : contas) {
			if (conta.getNome().equals(nome)) {
				contaAtiva = conta;
				return conta;
			}
		} return null;
	}

	/**
	 * Método que checa login do usuário.
	 * 
	 * @param senhaParaChecar
	 *            A senha digitada pelo usuário.
	 * @return Retorna true se a senha digitada for igual a senha salva no
	 *         sistema, e false caso contrário.
	 */
	public boolean checaLogin(String senhaParaChecar) throws Exception {
		if (senhaParaChecar.equals(Criptografia.decrypt(senha)))
			return true;
		return false;
	}

	/**
	 * Método que atualiza o orçamento das categorias; sempre que começar um
	 * novo mês, os orçamentos mais velhos serão removidos.
	 * 
	 * @param mes
	 *            O mês do momento que deseja-se fazer a atualização.
	 */
	public void atualizaOrcamentoDeCategorias(int mes) {
		for (Categoria categoria : categorias) {
			if (categoria.getOrcamento() != null
					&& categoria.getOrcamento().getDataDeCriacao() - mes != 0)
				categoria.removeOrcamento();
		}
	}

	/**
	 * Método que irá adicionar uma categoria.
	 * 
	 * @param nomeCategoria
	 *            O nome.
	 * @param corCategoria
	 *            A cor.
	 * @throws Exception
	 *             Lança exceção se pelo menos um dos parâmetros for inválido.
	 */
	public void adicionaCategoria(String nomeCategoria, String corCategoria)
			throws Exception {
		categoriaValida(nomeCategoria, corCategoria);

		Categoria novaCategoria = new Categoria(nomeCategoria, corCategoria);
		categorias.add(novaCategoria);
		Collections.sort(categorias);
	}

	/**
	 * Método que irá remover uma categoria.
	 * 
	 * @param categoria
	 *            A categoria que deseja-se remover.
	 * @throws Exception
	 *             Lança exceção se a categoria não existir.
	 */
	public void removeCategoria(Categoria categoria) throws Exception {
		checaCategoriaJaExiste(categoria);

		categorias.remove(categoria);
	}

	/**
	 * Método que irá editar uma categoria.
	 * 
	 * @param categoriaParaEditar
	 *            A categoria que deseja-se editar.
	 * @param nomeCategoria
	 *            O novo nome da categoria.
	 * @param corCategoria
	 *            A nova cor da categoria.
	 * @throws Exception
	 *             Lança exceção se pelo menos um dos parâmetros for inválido.
	 */
	public void editaCategoria(Categoria categoriaParaEditar,
			String nomeCategoria, String corCategoria) throws Exception {
		categoriaValida(nomeCategoria, corCategoria);

		checaCategoriaJaExiste(categoriaParaEditar);
		
		Categoria categoria = this.pesquisaCategoria(categoriaParaEditar.getNome());
		categoria.setNome(nomeCategoria);
		categoria.setCor(corCategoria);
	}

	/**
	 * Método que irá listar o nome das categorias em um array de String.
	 * 
	 * @return Retorna um array de String com os nomes das categorias.
	 */
	public String[] listaNomeCategorias() {
		List<String> nomes = new ArrayList<>();

		for (Categoria categoria : categorias) {
			nomes.add(categoria.getNome());
		}

		return nomes.toArray(new String[nomes.size()]);
	}

	/**
	 * Método que irá listar o nome das contas em um array de String.
	 * 
	 * @return Retorna um array de String com os nomes das contas.
	 */
	public String[] listaNomeContas() {
		List<String> nomes = new ArrayList<>();

		for (Conta conta : contas) {
			nomes.add(conta.getNome());
		}

		return nomes.toArray(new String[nomes.size()]);
	}

	/**
	 * Pesquisa uma categoria a partir de seu nome.
	 * 
	 * @param nome
	 *            Um nome para a busca.
	 * @return Retorna a categoria com o nome escolhido, ou null caso não exista
	 *         uma categoria com esse nome.
	 */
	public Categoria pesquisaCategoria(String nome) {
		for (Categoria categoria : categorias) {
			if (categoria.getNome().equals(nome))
				return categoria;
		}
		return null;
	}

	/**
	 * Método que dá acesso ao nome do usuário.
	 * 
	 * @return O nome do usuário.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Método que dá acesso ao e-mail do usuário.
	 * 
	 * @return O e-mail do usuário.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Método que dá acesso à senha do usuário (criptografada).
	 * 
	 * @return A senha do usuário.
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * Método que dá acesso à dica de senha do usuário.
	 * 
	 * @return A dica de senha do usuário.
	 */
	public String getDicaSenha() {
		return dicaSenha;
	}

	/**
	 * Método que dá acesso à conta ativa do usuário.
	 * 
	 * @return A conta ativa do usuário.
	 */
	public Conta getContaAtiva() {
		return contaAtiva;
	}

	/**
	 * Método que dá acesso às contas do usuário.
	 * 
	 * @return As contas do usuário.
	 */
	public List<Conta> getContas() {
		return contas;
	}

	/**
	 * Método que dá acesso às categorias do usuário.
	 * 
	 * @return As categorias do usuário.
	 */
	public List<Categoria> getCategorias() {
		return categorias;
	}

	/**
	 * Método toString.
	 */
	@Override
	public String toString() {
		StringBuilder stringContas = new StringBuilder();

		for (Conta conta : contas) {
			stringContas.append(conta.toString());
		}
		
		return String.format(
				"Nome: %s\nE-mail: %s\nDica de senha: %s\nContas: %s", nome,
				email, dicaSenha, stringContas.toString());
	}

	/**
	 * Método hashCode.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	/**
	 * Método equals. Dois usuários são iguais se eles possuem o mesmo e-mail.
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

	/**
	 * Método que irá veri ficar se um usuário é válido.
	 * 
	 * @param nome
	 *            O nome.
	 * @param email
	 *            O e-mail.
	 * @param senha
	 *            A senha.
	 * @param dicaSenha
	 *            A dica de senha.
	 * @param nomeDaConta
	 *            O nome da conta.
	 * @throws Exception
	 *             Lança exceção se pelo menos um dos parâmetros for inválido.
	 */
	private void usuarioValido(String nome, String email, String senha,
			String dicaSenha, String nomeDaConta) throws Exception {
		if (!validaNome(nome))
			throw new Exception("O nome do usuário deve ser informado.");
		if (!validaSenha(senha))
			throw new Exception(
					"Senha inválida, deve conter de 6 à 8 caracteres.");
		if (!validaEmail(email))
			throw new Exception("E-mail inválido.");
		if (!validaNome(dicaSenha))
			throw new Exception("Dica de senha inválida.");
		if (!validaNome(nomeDaConta))
			throw new Exception("Nome da conta inválido.");
	}

	/**
	 * Método que irá verificar se uma categoria é válida.
	 * 
	 * @param nomeCategoria
	 *            O nome.
	 * @param corCategoria
	 *            A cor.
	 * @throws Exception
	 *             Lança exceção se pelo menos um dos parâmetros for inválido.
	 */
	private void categoriaValida(String nomeCategoria, String corCategoria)
			throws Exception {
		if (!validaNome(nomeCategoria))
			throw new Exception("Nome de categoria inválido.");
		if (!validaNome(corCategoria))
			throw new Exception("Cor de categoria inválida.");

		for (Categoria categoria : categorias) {
			if (categoria.getNome().equals(nomeCategoria)
					&& categoria.getCor().equals(corCategoria))
				throw new Exception("Categoria já existe.");
		}
	}

	/**
	 * Método que irá verificar se um nome é válido ou não.
	 * 
	 * @param nome
	 *            Um nome.
	 * @return Retorna true se for válido, e false caso contrário.
	 */
	private boolean validaNome(String nome) {
		if (nome == null || nome.trim().length() == 0)
			return false;
		return true;
	}

	/**
	 * Método que irá verificar se uma senha é válida ou não.
	 * 
	 * @param senha
	 *            Uma senha.
	 * @return Retorna true se for válida, e false caso contrário.
	 */
	private boolean validaSenha(String senha) {
		if (senha.length() < 6 || senha.length() > 8)
			return false;
		return true;
	}

	/**
	 * Método que irá verificar se um e-mail é válido ou não.
	 * 
	 * @param email
	 *            Um e-mail.
	 * @return Retorna true se for válido, e false caso contrário.
	 */
	private boolean validaEmail(String email) {
		if (email == null || email.trim().length() == 0)
			return false;

		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}

	/**
	 * Método que irá verificar se uma conta já existe no sistema.
	 * 
	 * @param conta
	 *            Uma conta.
	 * @throws Exception
	 *             Lança exceção se a conta não existir.
	 */
	private void checaContaJaExiste(Conta conta) throws Exception {
		if (!contas.contains(conta))
			throw new Exception("Conta inexistente.");
	}

	/**
	 * Método que irá verificar se uma categoria já existe no sistema.
	 * 
	 * @param categoria
	 *            Uma categoria.
	 * @throws Exception
	 *             Lança exceção se a categoria não existir.
	 */
	private void checaCategoriaJaExiste(Categoria categoria) throws Exception {
		if (!categorias.contains(categoria))
			throw new Exception("Categoria inexistente.");
	}
}
