package fonte;

import java.util.ArrayList;
import java.util.HashMap;
import auxiliar.ArquivadorCategorias;

/**
 * Classe para gerenciamento de categorias.
 */

public class GerenteDeCategorias {

	private HashMap<Usuario, ArrayList<Categoria>> categoriasDoSistema;
	private ArrayList<Categoria> categoriasExistentes;
	private ArquivadorCategorias arquivador;
	private Usuario usuario;

	/**
	 * Construtor da classe GerenteDeCategorias, que leva um usuário como
	 * parâmetro.
	 * 
	 * @param usuario
	 *            O usuário que está logado.
	 */

	public GerenteDeCategorias(Usuario usuario) {
		try {
			arquivador = new ArquivadorCategorias("data3.mos");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		if (arquivador.leCategorias() == null) {
			categoriasDoSistema = new HashMap<Usuario, ArrayList<Categoria>>();
			categoriasExistentes = new ArrayList<>();
		} else {
			categoriasDoSistema = new HashMap<Usuario, ArrayList<Categoria>>(
					arquivador.leCategorias());
			categoriasExistentes = new ArrayList<>(
					categoriasDoSistema.get(usuario));
		}
		this.usuario = usuario;

		if (categoriasExistentes.isEmpty())
			adicionaCategoriasDefault();
	}

	/**
	 * Método para pesquisar uma categoria pelo seu nome.
	 * 
	 * @param nome
	 *            Nome da categoria.
	 * @return Retorna a categoria, caso ela exista; e retorna null, caso
	 *         contrário.
	 */

	public Categoria pesquisaCategoria(String nome) {
		for (Categoria categoria : categoriasExistentes) {
			if (categoria.getNome().equals(nome))
				return categoria;
		}
		return null;
	}

	/**
	 * Método que adiciona categorias default quando o usuário é novo e não tem
	 * nenhuma categoria ainda.
	 */

	private void adicionaCategoriasDefault() {
		try {
			categoriasExistentes.add(new Categoria("Lazer", "669966"));
			categoriasExistentes.add(new Categoria("Alimentação", "334db3"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		categoriasDoSistema.put(usuario, categoriasExistentes);

		try {
			arquivador.escreveCategorias(categoriasDoSistema);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que retorna um Array de Strings com os nomes das categorias do
	 * usuário.
	 * 
	 * @return Um Array de Strings com os nomes das categorias do usuário.
	 */

	public String[] listaCategorias() {
		int tamanhoDoArray = categoriasExistentes.size();
		String[] nomeDasCategorias = new String[tamanhoDoArray];

		for (int i = 0; i < tamanhoDoArray; i++) {
			nomeDasCategorias[i] = categoriasExistentes.get(i).getNome();
		}

		return nomeDasCategorias;
	}
	
	/**
	 * Método que retorna um ArrayList com as categorias do usuário.
	 * @return Um ArrayList com as categorias do usuário.
	 */
	public ArrayList<Categoria> getCategorias() {
		return categoriasExistentes;
	}

	/**
	 * Método que adiciona uma categoria às categorias que o usuário têm.
	 * 
	 * @param nomeCategoria
	 *            O nome da categoria que será adicionada.
	 * @param corCategoria
	 *            A cor da categoria que será adicionada.
	 * @throws Exception
	 *             Lança exceção se pelo menos um dos parâmetros for inválido.
	 */

	public void adicionaCategoria(String nomeCategoria, String corCategoria)
			throws Exception {

		categoriaValida(nomeCategoria, corCategoria);

		Categoria novaCategoria = new Categoria(nomeCategoria, corCategoria);
		categoriasExistentes.add(novaCategoria);
		categoriasDoSistema.put(usuario, categoriasExistentes);

		arquivador.escreveCategorias(categoriasDoSistema);
	}

	/**
	 * Método que remove uma categoria das categorias que o usuário têm.
	 * 
	 * @param categoria
	 *            A categoria que será removida.
	 * @throws Exception
	 *             Lança exceção se a categoria que deseja-se remover não
	 *             existir.
	 */

	public void removeCategoria(Categoria categoria) throws Exception {
		if (!categoriasExistentes.contains(categoria))
			throw new Exception("Categoria inexistente.");

		categoriasExistentes.remove(categoria);
		categoriasDoSistema.put(usuario, categoriasExistentes);

		arquivador.escreveCategorias(categoriasDoSistema);
	}

	/**
	 * Método que edita uma categoria existente das categorias que o usuário
	 * têm.
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

		if (!categoriasExistentes.contains(categoriaParaEditar))
			throw new Exception("Categoria inexistente.");

		categoriaValida(nomeCategoria, corCategoria);

		Categoria novaCategoria = new Categoria(nomeCategoria, corCategoria);

		categoriasExistentes.remove(categoriaParaEditar);
		categoriasExistentes.add(novaCategoria);

		categoriasDoSistema.put(usuario, categoriasExistentes);

		arquivador.escreveCategorias(categoriasDoSistema);
	}

	/**
	 * Método que irá dizer se uma categoria é válida ou não.
	 * 
	 * @param nomeCategoria
	 *            O nome da categoria.
	 * @param corCategoria
	 *            A cor da categoria.
	 * @throws Exception
	 *             Lança exceção se pelo menos um dos parâmetros for inválido ou
	 *             se a categoria já existe.
	 */

	public void categoriaValida(String nomeCategoria, String corCategoria)
			throws Exception {
		if (!nomeValido(nomeCategoria))
			throw new Exception("Nome de categoria inválido.");
		if (!corValido(corCategoria))
			throw new Exception("Cor inválida.");

		for (Categoria categoria : categoriasExistentes) {
			if (categoria.getNome().equals(nomeCategoria)
					&& categoria.getCor().equals(corCategoria))
				throw new Exception("Categoria já existe.");
		}
	}

	/**
	 * Método que irá verificar se um nome é válido ou não.
	 * 
	 * @param nomeCategoria
	 *            O nome da categoria.
	 * @return Retorna true se o nome for válido e false, caso contrário.
	 */

	private boolean nomeValido(String nomeCategoria) {
		if (nomeCategoria == null || nomeCategoria.length() == 0)
			return false;
		return true;
	}

	/**
	 * Método que irá verificar se a cor da categoria é válida ou não.
	 * 
	 * @param corCategoria
	 *            A cor da categoria.
	 * @return Retorna true se o nome for válido e false, caso contrário.
	 */

	private boolean corValido(String corCategoria) {
		if (corCategoria == null || corCategoria.length() == 0)
			return false;
		return true;
	}
}
