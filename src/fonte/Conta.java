package fonte;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Classe usada para representar uma conta.
 */
public class Conta implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private List<Transacao> transacoes;

	/**
	 * Construtor da classe conta, que recebe seu nome como parâmetro.
	 * 
	 * @param nome
	 *            O nome da conta.
	 * @throws Exception
	 *             Lança exceção se o nome da conta for inválido.
	 */
	public Conta(String nome) throws Exception {
		if (nome == null || nome.trim().length() == 0)
			throw new Exception("Nome da conta inválido.");

		this.nome = nome;
		transacoes = new ArrayList<>();
	}

	/**
	 * Método usado para adicionar uma transação.
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
	 * @return Retorna true se o valor da transação passar do orçamento da
	 *         categoria selecionada, e false caso contrário. Também retornará
	 *         false caso a categoria não tenha orçamento.
	 * @throws Exception
	 *             Lança exceção se pelo menos um dos parâmetros for inválido.
	 */
	public boolean adicionaTransacao(String descricao,
			LocalDate dataDeInsercao, String valor, Categoria categoria,
			String recorrencia, String tipoDeTransacao) throws Exception {

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

		transacoes.add(transacaoQueSeraAdicionada);
		Collections.sort(transacoes);

		return calculaGastosPorCategoria(categoria);
	}

	/**
	 * Método usado para remover uma transação.
	 * 
	 * @param transacao
	 *            A transação que deseja-se remover.
	 * @throws Exception
	 *             Lança exceção se a transação não existir.
	 */
	public void removeTransacao(Transacao transacao) throws Exception {
		if (!transacoes.contains(transacao))
			throw new Exception("Transação inexistente.");

		transacoes.remove(transacao);
	}

	/**
	 * Método usado para editar uma transação.
	 * 
	 * @param transacaoParaEditar
	 *            A transação que deseja-se editar.
	 * @param descricao
	 *            A nova descrição da transação.
	 * @param dataDeInsercao
	 *            A nova data de inserção da transação.
	 * @param valor
	 *            O novo valor da transação.
	 * @param categoria
	 *            A nova categoria da transação.
	 * @param recorrencia
	 *            A nova recorrência da transação.
	 * @param tipoDeTransacao
	 *            O novo tipo de transação.
	 * @return Retorna true se o novo valor da transação passar do orçamento da
	 *         nova categoria, e false caso contrário. Também retornará false
	 *         caso a nova categoria não tenha orçamento.
	 * @throws Exception
	 *             Lança exceção se pelo menos um dos parâmetros for inválido.
	 */
	public boolean editaTransacao(Transacao transacaoParaEditar,
			String descricao, LocalDate dataDeInsercao, String valor,
			Categoria categoria, String recorrencia, String tipoDeTransacao)
			throws Exception {

		Transacao transacaoQueSeraAdicionada = null;

		if (transacaoParaEditar == null
				|| !transacoes.contains(transacaoParaEditar))
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

		transacoes.remove(transacaoParaEditar);
		transacoes.add(transacaoQueSeraAdicionada);

		return calculaGastosPorCategoria(categoria);
	}

	/**
	 * Método usado para ter acesso às transações de um determinado mês.
	 * 
	 * @param mes
	 *            O mês que deseja-se filtrar as transações; um inteiro de 1 à
	 *            12, onde 1 representa Janeiro e 12 Dezembro.
	 * @return Retorna uma lista com todas as transações do mês selecionado.
	 * 
	 */
	public List<Transacao> listaTransacoesPeloMes(int mes) {

		List<Transacao> listaFiltradaPorMes = transacoes
				.stream()
				.filter(t -> t.getDataDeInsercao().getMonthValue() == mes
						&& t.getDataDeInsercao().getYear() == LocalDate.now()
								.getYear()).collect(Collectors.toList());
		return listaFiltradaPorMes;
	}

	/**
	 * Método usado para calcular o saldo do mês escolhido.
	 * 
	 * @param mes
	 *            O mês que deseja-se calcular o saldo.
	 * @return Retorna o saldo desse mês.
	 */
	public double pegaSaldoDoMes(int mes) {
		double saldoDoMes = 0;
		List<Transacao> transacoesDoMes = listaTransacoesPeloMes(mes);
		for (Transacao transacao : transacoesDoMes) {
			saldoDoMes += transacao.getValor();
		}
		return saldoDoMes;
	}

	/**
	 * Método que dá acesso ao nome da conta.
	 * 
	 * @return O nome da conta.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Método que dá acesso as transações da conta.
	 * 
	 * @return As transações da conta.
	 */
	public List<Transacao> getTransacoesExistentes() {
		return transacoes;
	}

	/**
	 * Método que modifica o nome da conta.
	 * 
	 * @param nome
	 *            O novo nome da conta.
	 * @throws Exception
	 *             Lança exceção se o nome for inválido.
	 */
	public void setNome(String nome) throws Exception {
		if (nome == null || nome.trim().length() == 0)
			throw new Exception("Nome da conta inválido.");

		this.nome = nome;
	}

	/**
	 * Método toString da conta.
	 */
	@Override
	public String toString() {
		return String.format("Nome: %s", nome);
	}

	/**
	 * Método hashCode.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((transacoes == null) ? 0 : transacoes.hashCode());
		return result;
	}

	/**
	 * Método equals. Duas contas serão iguais se tiverem os mesmos atributos.
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
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (transacoes == null) {
			if (other.transacoes != null)
				return false;
		} else if (!transacoes.equals(other.transacoes))
			return false;
		return true;
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
			throw new Exception("Descrição inválida. Máximo de 25 caracteres.");
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
	 * Método que verifica se uma descrição é válida.
	 * 
	 * @param descricao
	 *            Uma descrição.
	 * @return Retorna true se for válida, e false caso contrário.
	 */
	private boolean descricaoValida(String descricao) {
		if (descricao == null || descricao.trim().length() == 0
				|| descricao.length() > 25)
			return false;
		return true;
	}

	/**
	 * Método que verifica se uma data de inserção é válida.
	 *
	 * @param dataDeInsercao
	 *            Uma data de inserção.
	 * @return Retorna true se for válida, e false caso contrário.
	 */
	private boolean dataDeInsercaoValida(LocalDate dataDeInsercao) {
		if (dataDeInsercao == null
				|| dataDeInsercao.isAfter(LocalDate.now())
				|| LocalDate.now().getDayOfYear()
						- dataDeInsercao.getDayOfYear() > 30)
			return false;
		return true;
	}

	/**
	 * Método que verifica se um valor é válido.
	 * 
	 * @param valor
	 *            Um valor.
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
	 * Método que verifica se uma categoria é válida.
	 * 
	 * @param categoria
	 *            Uma categoria.
	 * @return Retorna true se for válida, e false caso contrário.
	 */
	private boolean categoriaValida(Categoria categoria) {
		if (categoria == null)
			return false;
		return true;
	}

	/**
	 * Método que verifica se uma recorrência é válida.
	 * 
	 * @param recorrencia
	 *            Uma recorrência.
	 * @return Retorna true se for válida, e false caso contrário.
	 */
	private boolean recorrenciaValida(String recorrencia) {
		if (recorrencia == null || recorrencia.trim().length() == 0)
			return false;
		return true;
	}

	/**
	 * Método que verifica se um tipo de transação é válido.
	 * 
	 * @param tipoDeTransacao
	 *            Um tipo de transação.
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
	 * Método que irá verificar se os gastos de uma categoria já passaram de seu
	 * orçamento, caso ele exista.
	 * 
	 * @param categoria
	 *            A categoria que deseja-se verificar.
	 * @return Retorna true se os gastos passaram do orçamento, e false caso
	 *         contrário. Também retornará false caso a categoria não tenha
	 *         orçamento.
	 */
	private boolean calculaGastosPorCategoria(Categoria categoria) {
		if (categoria.getOrcamento() == null)
			return false;
		double valor = 0;

		for (Transacao transacao : transacoes)
			if (categoria.equals(transacao.getCategoria()))
				if (transacao.getValor() < 0)
					valor -= transacao.getValor();

		if (valor > categoria.getOrcamento().getLimite())
			return true;
		return false;
	}

	/**
	 * Método usado para atualizar as transações a partir das suas recorrências.
	 */
	public void atualizaRecorrencias() {
		Set<Transacao> transacoesDoMes = new HashSet<>(transacoes);
		String tipoDeTransacao;

		for (Transacao transacao : transacoesDoMes) {
			if (transacao.getValor() < 0)
				tipoDeTransacao = "despesa";
			else
				tipoDeTransacao = "provento";

			switch (transacao.getRecorrencia()) {
			case "Mensal":
				atualizaMensal(tipoDeTransacao, transacao);
				break;
			case "Semanal":
				atualizaSemanal(tipoDeTransacao, transacao);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Método usado para atualizar as transações que tem recorrência mensal.
	 * 
	 * @param tipoDeTransacao
	 *            O tipo de transação.
	 * @param transacao
	 *            A transação.
	 */
	private void atualizaMensal(String tipoDeTransacao, Transacao transacao) {
		if (LocalDate.now().getDayOfYear()
				- transacao.getDataDeInsercao().getDayOfYear() >= 30) {
			try {
				adicionaTransacao(transacao.getDescricao(), LocalDate.now(),
						Double.toString(transacao.getValor()),
						transacao.getCategoria(), transacao.getRecorrencia(),
						tipoDeTransacao);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Método usado para atualizar as transações que tem recorrência semanal.
	 * 
	 * @param tipoDeTransacao
	 *            O tipo de transação.
	 * @param transacao
	 *            A transação.
	 */
	private void atualizaSemanal(String tipoDeTransacao, Transacao transacao) {
		if (LocalDate.now().getDayOfYear()
				- transacao.getDataDeInsercao().getDayOfYear() >= 7) {
			try {
				adicionaTransacao(transacao.getDescricao(), LocalDate.now(),
						Double.toString(transacao.getValor()),
						transacao.getCategoria(), transacao.getRecorrencia(),
						tipoDeTransacao);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
