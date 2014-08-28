package fonte;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe usada para representar uma conta.
 */
public class Conta implements Serializable {

	private static final long serialVersionUID = 1L;
	private double saldo;
	private List<Transacao> transacoesExistentes;

	/**
	 * Construtor da classe Conta, que não recebe nenhum parâmetro e começa com
	 * saldo igual a zero.
	 */
	public Conta() {
		saldo = 0.0;
		transacoesExistentes = new ArrayList<>();
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
	
	/**
	 * Método que irá adicionar uma transação às transações que o usuário têm.
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
	 *            A recorrência da transação.
	 * @param tipoDeTransacao
	 *            O tipo de transação.
	 * @throws Exception
	 *             Lança exceção se pelo menos um dos parâmetros for inválido.
	 */
	public void adicionaTransacao(String descricao, LocalDate dataDeInsercao,
			String valor, Categoria categoria, String recorrencia,
			String tipoDeTransacao) throws Exception {
		
		Transacao transacaoQueSeraAdicionada = null;
		
		transacaoValida(descricao, dataDeInsercao, valor, categoria,
				recorrencia, tipoDeTransacao);
		Double valorNovo = Double.parseDouble(valor);

		if (tipoDeTransacao.equals("despesa")) {
			transacaoQueSeraAdicionada = new Despesa(descricao, dataDeInsercao,
					valorNovo, categoria, recorrencia);
		} else if (tipoDeTransacao.equals("provento")) {
			transacaoQueSeraAdicionada = new Provento(descricao,
					dataDeInsercao, valorNovo, categoria, recorrencia);
		}

		transacoesExistentes.add(transacaoQueSeraAdicionada);
		Collections.sort(transacoesExistentes);
		
		this.moveDinheiroNaConta(
				transacaoQueSeraAdicionada.getValor());
	}
	
	/**
	 * Método que verifica se uma transação é válida.
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
	 *            A recorrência da transação.
	 * @param tipoDeTransacao
	 *            O tipo de transação.
	 * @throws Exception
	 *             Lança exceção se pelo menos um dos parâmetros for inválido.
	 */
	private void transacaoValida(String descricao, LocalDate dataDeInsercao,
			String valor, Categoria categoria, String recorrencia,
			String tipoDeTransacao) throws Exception {
		
		if (!descricaoValida(descricao)) {
			throw new Exception("Descrição inválida.");
		}
		if (!dataDeInsercaoValida(dataDeInsercao)) {
			throw new Exception("Data de inserção inválida.");
		}
		if (!valorValido(valor)) {
			throw new Exception("Valor inválido.");
		}
		if (!categoriaValida(categoria)) {
			throw new Exception("Categoria inválida.");
		}
		if (!recorrenciaValida(recorrencia)) {
			throw new Exception("Recorrência inválida.");
		}
		if (!tipoDeTransacaoValido(tipoDeTransacao)) {
			throw new Exception("Tipo de transação inválido.");
		}
	}
	
	/**
	 * Método que checa se o tipo de transação é válido.
	 * 
	 * @param tipoDeTransacao
	 *            O tipo de transação.
	 * @return Retorna true se for válido, e false caso contrário.
	 */
	private boolean tipoDeTransacaoValido(String tipoDeTransacao) {
		if (tipoDeTransacao == null
				|| (!tipoDeTransacao.equals("despesa") && !tipoDeTransacao
						.equals("provento")))
			return false;
		return true;
	}
	
	/**
	 * Método que checa se a recorrência da transação é válida.
	 * 
	 * @param recorrencia
	 *            A recorrência da transação.
	 * @return Retorna true se for válida, e false caso contrário.
	 */
	private boolean recorrenciaValida(String recorrencia) {
		if (recorrencia == null || recorrencia.trim().length() == 0)
			return false;
		return true;
	}
	
	/**
	 * Método que verifica se a categoria da transação é válida.
	 * 
	 * @param categoria
	 *            A categoria da transação.
	 * @return Retorna true se for válida, e false caso contrário.
	 */
	private boolean categoriaValida(Categoria categoria) {
		if (categoria == null)
			return false;
		return true;
	}
	
	/**
	 * Método que verifica se o valor da transação é válido.
	 * 
	 * @param valor
	 *            O valor da transação.
	 * @return Retorna true se for válido, e false caso contrário.
	 */
	private boolean valorValido(String valor) {
		try {
			Double valorNovo = Double.parseDouble(valor);
			if (valorNovo <= 0)
				return false;
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Método que verifica se a data de inserção da transação é válida.
	 * 
	 * @param dataDeInsercao
	 *            A data de inserção da transação.
	 * @return Retorna true se for válida, e false caso contrário.
	 */
	private boolean dataDeInsercaoValida(LocalDate dataDeInsercao) {
		if (dataDeInsercao == null || dataDeInsercao.isAfter(LocalDate.now()))
			return false;
		return true;
	}
	
	/**
	 * Método que verifica se a descrição da transação é válida.
	 * 
	 * @param descricao
	 *            A descrição da transação.
	 * @return Retorna true se for válida, e false caso contrário.
	 */
	private boolean descricaoValida(String descricao) {
		if (descricao == null || descricao.trim().length() == 0
				|| descricao.length() > 25)
			return false;
		return true;
	}
	
	/**
	 * Método que irá remover uma transação das transações que o usuário têm.
	 * 
	 * @param transacao
	 *            A transação que deseja-se remover.
	 * @throws Exception
	 *             Lança exceção se a transação não existir.
	 */
	public void removeTransacao(Transacao transacao) throws Exception {
		if (!transacoesExistentes.contains(transacao))
			throw new Exception("Transação inexistente.");
		
		this.moveDinheiroNaConta(-transacao.getValor());
		transacoesExistentes.remove(transacao);
	}
	
	/**
	 * Método que irá editar uma transação das transações que o usuário têm.
	 * 
	 * @param transacaoParaEditar
	 *            A transação que deseja-se editar.
	 * @param descricao
	 *            A descrição da transação nova.
	 * @param dataDeInsercao
	 *            A data de inserção da transação nova.
	 * @param valor
	 *            O valor da transação nova.
	 * @param categoria
	 *            A categoria da transação nova.
	 * @param recorrencia
	 *            A recorrência da transação nova.
	 * @param tipoDeTransacao
	 *            O tipo de transação nova.
	 * @throws Exception
	 *             Lança exceção se pelo menos um dos parâmetros for inválido.
	 */
	public void editaTransacao(Transacao transacaoParaEditar, String descricao,
			LocalDate dataDeInsercao, String valor, Categoria categoria,
			String recorrencia, String tipoDeTransacao) throws Exception {
		
		Transacao transacaoQueSeraAdicionada = null;
		this.moveDinheiroNaConta(-transacaoParaEditar.getValor());
		
		if (transacaoParaEditar == null
				|| !transacoesExistentes.contains(transacaoParaEditar))
			throw new Exception("Transação inexistente.");

		transacaoValida(descricao, dataDeInsercao, valor, categoria,
				recorrencia, tipoDeTransacao);
		Double novoValor = Double.parseDouble(valor);
		
		if (tipoDeTransacao.equals("despesa")) {
			transacaoQueSeraAdicionada = new Despesa(descricao, dataDeInsercao,
					novoValor, categoria, recorrencia);
		} else if (tipoDeTransacao.equals("provento")) {
			transacaoQueSeraAdicionada = new Provento(descricao,
					dataDeInsercao, novoValor, categoria, recorrencia);
		}

		this.moveDinheiroNaConta(
				transacaoQueSeraAdicionada.getValor());
		
		transacoesExistentes.remove(transacaoParaEditar);
		transacoesExistentes.add(transacaoQueSeraAdicionada);
	}
	
	/**
	 * Método de acesso a lista de transações existentes do usuário que está
	 * logado.
	 * 
	 * @return Retorna uma lista com as transações existentes do usuário que
	 *         está logado.
	 */
	public List<Transacao> getTransacoesExistentes() {
		return transacoesExistentes;
	}
	
	/**
	 * Método que recebe um inteiro de 1 até 12, que representa o mês, e filtra
	 * as transações baseado no mês de entrada.
	 * 
	 * @param mes
	 *            O mês que deseja-se usar para o filtro.
	 * @return Retorna uma List de Transacao somente do mês desejado.
	 */
	public List<Transacao> listaTransacoesPeloMes(int mes) {
		List<Transacao> listaFiltradaPorMes = transacoesExistentes.stream()
				.filter(t -> t.getDataDeInsercao().getMonthValue() == mes)
				.collect(Collectors.toList());
		return listaFiltradaPorMes;
	}
	
	/**
	 * Checa se os gastos de uma determinada categoria passou dos limites do orcamento
	 * 
	 * @param categoria
	 * 		categoria que deseja checar se passou do limite
	 * @return
	 * 		valor gasto numa categoria
	 * @throws Exception
	 * 		lanca excecao se o valor atingiu ou ultrapassou o limite
	 */
	public double calculaGastosPorCategoria(Categoria categoria) throws Exception {
		double valor=0;
		
		for (Transacao transacao : this.getTransacoesExistentes()) 
			if (categoria.equals(transacao.getCategoria()))
				valor += transacao.getValor();
		
		if (valor > categoria.getOrcamento())
			throw new Exception("Valor limite excedido para esta categoria.");
		return valor;
	}
}
