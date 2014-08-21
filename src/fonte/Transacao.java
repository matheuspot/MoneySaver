package fonte;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe usada para representar uma transação.
 */

public class Transacao implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final SimpleDateFormat FORMATACAO_DATA = new SimpleDateFormat(
			"dd/MM/yyyy");

	private String descricao;
	private Date dataDeInsercao;
	private double valor;
	private Categoria categoria;
	private String recorrencia;

	/**
	 * Construtor da classe Transacao.
	 * 
	 * @param descricao
	 *            A descrição da transação.
	 * @param dataDeInsercao
	 *            A data de inserção da transação.
	 * @param valor
	 *            O valor da transação.
	 * @param categoria
	 *            A categoria da transação.
	 * @param recorrencia
	 *            A recorrência desse tipo de transação.
	 * @throws Exception
	 *             Lança exceção se pelo menos um dos parâmetros for inválido.
	 */

	public Transacao(String descricao, String dataDeInsercao, double valor,
			Categoria categoria, String recorrencia) throws Exception {
		if (descricao == null)
			throw new Exception("Descrição inválida.");
		if (dataDeInsercao == null)
			throw new Exception("Data de inserção inválida.");
		if (valor <= 0)
			throw new Exception("Valor inválido.");
		if (categoria == null)
			throw new Exception("Categoria inválida.");
		if (recorrencia == null)
			throw new Exception("Recorrência inválida.");

		this.descricao = descricao;
		this.dataDeInsercao = FORMATACAO_DATA.parse(dataDeInsercao);
		this.valor = valor;
		this.categoria = categoria;
		this.recorrencia = recorrencia;
	}

	/**
	 * Método de acesso à descrição da transação.
	 * 
	 * @return A descrição da transação.
	 */

	public String getDescricao() {
		return descricao;
	}

	/**
	 * Método de acesso à data de inserção da transação.
	 * 
	 * @return A data de inserção da transação.
	 */

	public Date getDataDeInsercao() {
		return dataDeInsercao;
	}

	/**
	 * Método de acesso ao valor da transação.
	 * 
	 * @return O valor da transação.
	 */

	public double getValor() {
		return valor;
	}

	/**
	 * Método de acesso à categoria da transação.
	 * 
	 * @return A categoria da transação.
	 */

	public Categoria getCategoria() {
		return categoria;
	}

	/**
	 * Método de acesso à recorrência da transação.
	 * 
	 * @return A recorrência da transação.
	 */

	public String getRecorrencia() {
		return recorrencia;
	}

	/**
	 * Método parecido com o toString mas que retorna apenas a data e o valor da
	 * transação.
	 * 
	 * @return A data e o valor da transação.
	 */

	public String toStringResumido() {
		return FORMATACAO_DATA.format(dataDeInsercao) + " " + valor;
	}

	/**
	 * Override do método toString da classe Object.
	 */

	@Override
	public String toString() {
		return "Descrição: " + descricao + "\nData de Inserção: "
				+ FORMATACAO_DATA.format(dataDeInsercao) + "\nValor: " + valor
				+ "\nCategoria: " + categoria.getNome() + "\nRecorrência: "
				+ recorrencia;
	}

	/**
	 * Override do método hashCode da classe Object.
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result
				+ ((dataDeInsercao == null) ? 0 : dataDeInsercao.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result
				+ ((recorrencia == null) ? 0 : recorrencia.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Override do método equals da classe Object. Duas transações são iguais se
	 * todos os seus atributos são os mesmos.
	 */

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transacao other = (Transacao) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (dataDeInsercao == null) {
			if (other.dataDeInsercao != null)
				return false;
		} else if (!dataDeInsercao.equals(other.dataDeInsercao))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (recorrencia == null) {
			if (other.recorrencia != null)
				return false;
		} else if (!recorrencia.equals(other.recorrencia))
			return false;
		if (Double.doubleToLongBits(valor) != Double
				.doubleToLongBits(other.valor))
			return false;
		return true;
	}
}
