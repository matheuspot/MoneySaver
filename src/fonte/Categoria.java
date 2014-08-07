package fonte;

import java.io.Serializable;

/**
 * Classe para representar uma Categoria
 */
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nome;
	private String cor;
	
	/**
	 * Construtor da Classe
	 * 
	 * @param nome
	 * 		nome da categoria
	 * @param cor
	 * 		cor da categoria
	 */
	public Categoria(String nome, String cor) throws Exception {
		if (nome == null || nome.equals(""))
			throw new Exception("Diga o nome da Categoria!");
		if (cor == null || cor.equals(""))
			throw new Exception("Diga a cor desejada!");
			
		
		this.nome = nome;
		this.cor = cor;
	}
	
	/**
	 * Metodo de acesso ao nome da categoria
	 * 
	 * @return
	 * 		nome da categoria
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * Metodo de acesso a cor da categoria
	 * 
	 * @return
	 * 		cor da categoria
	 */
	public String getCor() {
		return cor;
	}
	
	/**
	 * Reescreve o metodo toString da classe String
	 * Retorna uma String com as caracteristicas da categoria(nome e cor)
	 */
	@Override
	public String toString() {
		return "Nome: " + nome + "\nCor: " + cor;
	}
	
	/**
	 * Hash code
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}
	
	/**
	 * Checa se duas categorias sao iguais,
	 * e retorna true caso seja ou false, 
	 * caso contrario.
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
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (!cor.equals(other.getCor()))
			return false;
		return true;
	}
}
