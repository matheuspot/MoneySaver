package fonte;

import java.io.Serializable;

public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nome;
	private String cor;

	public Categoria(String nome, String cor) {
		this.nome = nome;
		this.cor = cor;
	}

	public String getNome() {
		return nome;
	}
	
	public String getCor() {
		return cor;
	}

	@Override
	public String toString() {
		return "Nome: " + nome + "\nCor: " + cor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

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
		return true;
	}
}
