package fonte;

import java.io.Serializable;

/**
 * Classe usada para representar uma conta.
 */
public class Conta implements Serializable {

	private static final long serialVersionUID = 1L;
	private double saldo;

	/**
	 * Construtor da classe Conta, que não recebe nenhum parâmetro e começa com
	 * saldo igual a zero.
	 */
	public Conta() {
		saldo = 0.0;
	}

	/**
	 * Método para mover dinheiro na conta. Pode ser para adicionar ou retirar.
	 * 
	 * @param dinheiro
	 *            O valor que será adicionado ou retirado da conta.
	 */
	public void moveDinheiroNaConta(double dinheiro) {
		saldo += dinheiro;
	}

	/**
	 * Método para pegar o saldo da conta.
	 * 
	 * @return O saldo da conta.
	 */
	public double getSaldo() {
		return saldo;
	}

	/**
	 * Override do método toString da classe Object
	 */
	@Override
	public String toString() {
		return String.format("R$ %.2f", saldo);
	}

	/**
	 * Override do método hashCode da classe Object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(saldo);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Override do método equals da classe Object.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conta other = (Conta) obj;
		if (Double.doubleToLongBits(saldo) != Double
				.doubleToLongBits(other.saldo))
			return false;
		return true;
	}
}
