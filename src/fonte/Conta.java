package fonte;

/**
 * Classe usada para representar uma conta.
 */

public class Conta {

	private double saldo;

	/**
	 * Construtor da classe Conta, que não recebe nenhum parâmetro e começa com
	 * saldo igual a zero.
	 */

	public Conta() {
		saldo = 0.0;
	}

	/**
	 * Método usado para adicionar dinheiro na conta.
	 * 
	 * @param dinheiro
	 *            O valor que deseja-se adicionar na conta.
	 * @throws Exception
	 *             Lança exceção se o valor não for maior do que zero.
	 */

	public void adicionaDinheiro(double dinheiro) throws Exception {
		if (dinheiro <= 0) {
			throw new Exception(
					"Valor inválido, entre com um número maior que zero.");
		}
		saldo += dinheiro;
	}

	/**
	 * Método usado para retirar dinheiro da conta.
	 * 
	 * @param dinheiro
	 *            O valor que deseja-se retirar da conta.
	 * @throws Exception
	 *             Lança exceção se o valor não for maior do que zero.
	 */

	public void retiraDinheiro(double dinheiro) throws Exception {
		if (dinheiro <= 0) {
			throw new Exception(
					"Valor inválido, entre com um número maior que zero.");
		}
		saldo -= dinheiro;
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
		return "Saldo: " + saldo;
	}
}
