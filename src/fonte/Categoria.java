package fonte;

import java.io.Serializable;
import excecao.MoneySaverException;

/**
 * Classe usada para representar uma categoria.
 */
public class Categoria implements Serializable, Comparable<Categoria> {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String cor;
	private Orcamento orcamento;

	/**
	 * Construtor de categoria.
	 * 
	 * @param nome
	 *            O nome da categoria.
	 * @param cor
	 *            A cor da categoria.
	 * @throws MoneySaverException
	 *             Lança exceção se pelo menos um dos parâmetros for inválido.
	 */
	public Categoria(String nome, String cor) throws MoneySaverException {
		if (!validaNome(nome))
			throw new MoneySaverException("Nome inválido.");
		if (!validaNome(cor))
			throw new MoneySaverException("Cor inválida.");

		this.nome = nome;
		this.cor = cor;
	}

	/**
	 * Método usado para adicionar um orçamento à categoria.
	 * 
	 * @param limite
	 *            O limite do orçamento.
	 * @throws MoneySaverException
	 *             Lança exceção se o limite for menor ou igual a zero.
	 */
	public void setOrcamento(double limite) throws MoneySaverException {
		if (limite <= 0)
			throw new MoneySaverException("Valor limite tem que ser positivo.");

		orcamento = new Orcamento(limite);
	}

	/**
	 * Método que irá remover o orçamento da categoria atribuindo-o como null.
	 */
	public void removeOrcamento() {
		orcamento = null;
	}

	/**
	 * Método para modificar o nome da categoria.
	 * 
	 * @param nome
	 *            O novo nome da categoria.
	 * @throws MoneySaverException
	 *             Lança exceção se o nome for inválido.
	 */
	public void setNome(String nome) throws MoneySaverException {
		if (!validaNome(nome))
			throw new MoneySaverException("Nome inválido.");

		this.nome = nome;
	}

	/**
	 * Método para modificar a cor da categoria.
	 * 
	 * @param cor
	 *            A cor da categoria.
	 * @throws MoneySaverException
	 *             Lança exceção se a cor for inválida.
	 */
	public void setCor(String cor) throws MoneySaverException {
		if (!validaNome(cor))
			throw new MoneySaverException("Cor inválida.");

		this.cor = cor;
	}

	/**
	 * Método que dá acesso ao nome da categoria.
	 * 
	 * @return O nome da categoria.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Método que dá acesso à cor da categoria.
	 * 
	 * @return A cor da categoria.
	 */
	public String getCor() {
		return cor;
	}

	/**
	 * Método que dá acesso ao orçamento da categoria.
	 * 
	 * @return O orçamento da categoria.
	 */
	public Orcamento getOrcamento() {
		return orcamento;
	}

	/**
	 * Método toString.
	 */
	@Override
	public String toString() {
		return nome;
	}

	/**
	 * Método hashCode.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cor == null) ? 0 : cor.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((orcamento == null) ? 0 : orcamento.hashCode());
		return result;
	}

	/**
	 * Método equals, duas categorias serão iguais se todos os seus atributos
	 * forem iguais.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (cor == null) {
			if (other.cor != null)
				return false;
		} else if (!cor.equals(other.cor))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (orcamento == null) {
			if (other.orcamento != null)
				return false;
		} else if (!orcamento.equals(other.orcamento))
			return false;
		return true;
	}

	/**
	 * Método compareTo irá comparar duas categorias baseado em seus nomes.
	 * 
	 * @param outraCategoria
	 *            A outra categoria que será comparada.
	 * @return Retorna um inteiro negativo, zero ou um inteiro positivo,
	 *         dependendo da ordem dos nomes.
	 */
	@Override
	public int compareTo(Categoria outraCategoria) {
		return nome.compareTo(outraCategoria.getNome());
	}

	/**
	 * Método que irá verificar se um nome é válido ou não.
	 * 
	 * @param nome
	 *            Um nome.
	 * @return Retorna true se for válido, e false caso contrário.
	 */
	private boolean validaNome(String nome) {
		if (nome == null || nome.trim().isEmpty())
			return false;
		return true;
	}
}
