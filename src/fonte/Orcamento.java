package fonte;

import java.io.Serializable;
import java.time.LocalDate;
import excecao.MoneySaverException;

/**
 * Classe usada para representar um orçamento.
 */
public class Orcamento implements Serializable {

	private static final long serialVersionUID = 1L;

	private final double limite;
	private final int dataDeCriacao;

	/**
	 * Construtor do orçamento.
	 * 
	 * @param limite
	 *            Limite máximo do orçamento.
	 * @throws MoneySaverException
	 *             Lança exceção se o limite for menor ou igual a zero.
	 */
	public Orcamento(double limite) throws MoneySaverException {
		if (limite <= 0)
			throw new MoneySaverException("Valor limite tem que ser positivo.");

		this.limite = limite;
		dataDeCriacao = LocalDate.now().getMonthValue();
	}

	/**
	 * Método que dá acesso ao limite do orçamento.
	 * 
	 * @return O limite do orçamento.
	 */
	public double getLimite() {
		return limite;
	}

	/**
	 * Método que dá acesso ao mês de criação do orçamento, que é um inteiro de
	 * 1 à 12.
	 * 
	 * @return O mês de criação do orçamento.
	 */
	public int getDataDeCriacao() {
		return dataDeCriacao;
	}

	/**
	 * Método toString.
	 */
	@Override
	public String toString() {
		return String.format("Limite: %.1f", limite);
	}

	/**
	 * Método hashCode.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dataDeCriacao;
		long temp;
		temp = Double.doubleToLongBits(limite);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Método equals, dois orçamentos serão iguais se todos os seus atributos
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
		Orcamento other = (Orcamento) obj;
		if (dataDeCriacao != other.dataDeCriacao)
			return false;
		if (Double.doubleToLongBits(limite) != Double
				.doubleToLongBits(other.limite))
			return false;
		return true;
	}
}
