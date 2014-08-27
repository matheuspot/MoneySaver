package fonte;

import java.io.Serializable;
import java.util.List;

/**
 * Classe usada para representar uma categoria.
 */
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nome;
	private String cor;
	public double valorLimite = 0;

	/**
	 * Construtor da classe Categoria.
	 * 
	 * @param nome
	 *            O nome da categoria.
	 * @param cor
	 *            A cor da categoria.
	 * @throws Exception
	 *             Lança exceção se pelo menos um dos parâmetros for inválido.
	 */
	public Categoria(String nome, String cor) throws Exception {
		if (nome == null || nome.trim().length() == 0)
			throw new Exception("Nome inválido.");
		if (cor == null || cor.trim().length() == 0)
			throw new Exception("Cor inválida.");

		this.nome = nome;
		this.cor = cor;
	}

	/**
	 * Método de acesso ao nome da categoria.
	 * 
	 * @return O nome da categoria.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Método de acesso à cor da categoria.
	 * 
	 * @return A cor da categoria.
	 */
	public String getCor() {
		return cor;
	}
	
	/**
	 * Override do método toString da classe Object.
	 */
	@Override
	public String toString() {
		return nome;
	}

	/**
	 * Override do método hashCode da classe Object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cor == null) ? 0 : cor.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	/**
	 * Override do método equals da classe Object. Duas categorias são iguais se
	 * elas tem mesma cor e mesmo nome.
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
		return true;
	}
}
