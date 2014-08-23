package fonte;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import auxiliar.ArquivadorTransacoes;
import auxiliar.ArquivadorUsuarios;

/**
 * Classe para gerenciamento de transações.
 */
public class GerenteDeTransacoes {

	private Map<Usuario, List<Transacao>> transacoesDoSistema;
	private List<Transacao> transacoesExistentes;
	private ArquivadorTransacoes arquivadorTransacoes;
	private ArquivadorUsuarios arquivadorUsuarios;
	private Usuario usuario;
	private List<Usuario> usuariosDoSistema;
	private Transacao transacaoQueSeraAdicionada;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private LocalDate dataAtual;

	/**
	 * Construtor da classe GerenteDeTransacoes, que leva um usuário como
	 * parâmetro.
	 * 
	 * @param usuario
	 *            O usuário que está logado.
	 */
	public GerenteDeTransacoes(Usuario usuario) {
		try {
			arquivadorTransacoes = new ArquivadorTransacoes("data2.mos");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		try {
			arquivadorUsuarios = new ArquivadorUsuarios("data1.mos");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		if (arquivadorTransacoes.leTransacoes() == null) {
			transacoesDoSistema = new HashMap<Usuario, List<Transacao>>();
			transacoesExistentes = new ArrayList<>();
		} else {
			transacoesDoSistema = new HashMap<Usuario, List<Transacao>>(
					arquivadorTransacoes.leTransacoes());
			transacoesExistentes = new ArrayList<>(
					transacoesDoSistema.get(usuario));
		}

		if (arquivadorUsuarios.leUsuarios() == null) {
			usuariosDoSistema = new ArrayList<>();
		} else {
			usuariosDoSistema = new ArrayList<>(arquivadorUsuarios.leUsuarios());
		}

		this.usuario = usuario;
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

		usuariosDoSistema.remove(usuario);
		usuario.getConta().moveDinheiroNaConta(
				transacaoQueSeraAdicionada.getValor());
		usuariosDoSistema.add(usuario);
		arquivadorUsuarios.escreveUsuarios(usuariosDoSistema);

		transacoesExistentes.add(transacaoQueSeraAdicionada);
		Collections.sort(transacoesExistentes);
		transacoesDoSistema.put(usuario, transacoesExistentes);

		arquivadorTransacoes.escreveTransacoes(transacoesDoSistema);
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

		usuariosDoSistema.remove(usuario);
		usuario.getConta().moveDinheiroNaConta(-transacao.getValor());
		usuariosDoSistema.add(usuario);
		arquivadorUsuarios.escreveUsuarios(usuariosDoSistema);

		transacoesExistentes.remove(transacao);
		transacoesDoSistema.put(usuario, transacoesExistentes);

		arquivadorTransacoes.escreveTransacoes(transacoesDoSistema);
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

		usuariosDoSistema.remove(usuario);
		usuario.getConta().moveDinheiroNaConta(-transacaoParaEditar.getValor());
		transacoesExistentes.remove(transacaoParaEditar);
		usuario.getConta().moveDinheiroNaConta(
				transacaoQueSeraAdicionada.getValor());
		usuariosDoSistema.add(usuario);
		arquivadorUsuarios.escreveUsuarios(usuariosDoSistema);

		transacoesExistentes.add(transacaoQueSeraAdicionada);
		transacoesDoSistema.put(usuario, transacoesExistentes);

		arquivadorTransacoes.escreveTransacoes(transacoesDoSistema);
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
		if (!tipoDeTransacaoValido(tipoDeTransacao))
			throw new Exception("Tipo de transação inválido.");
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
		if (dataDeInsercao == null)
			return false;
		if (dataDeInsercao.isAfter(dataAtual.now()))
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
		if (descricao == null || descricao.trim().length() == 0)
			return false;
		if (descricao.length() > 50)
			return false;
		return true;
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
}
