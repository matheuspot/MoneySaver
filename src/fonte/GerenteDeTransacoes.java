package fonte;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import auxiliar.ArquivadorTransacoes;
import auxiliar.ArquivadorUsuarios;

/**
 * Classe para gerenciamento de transações.
 */

public class GerenteDeTransacoes {

	private HashMap<Usuario, ArrayList<Transacao>> transacoesDoSistema;
	private ArrayList<Transacao> transacoesExistentes;
	private ArquivadorTransacoes arquivadorTransacoes;
	private ArquivadorUsuarios arquivadorUsuarios;
	private Usuario usuario;
	private ArrayList<Usuario> usuariosDoSistema;
	private Transacao transacaoQueSeraAdicionada;

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
			transacoesDoSistema = new HashMap<Usuario, ArrayList<Transacao>>();
			transacoesExistentes = new ArrayList<>();
		} else {
			transacoesDoSistema = new HashMap<Usuario, ArrayList<Transacao>>(
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
	 * Método que lista as transações que o usuário têm de forma resumida.
	 * 
	 * @return Um Array de String com informações das transações do usuário de
	 *         forma reduzida.
	 */

	public String[] listaTransacoesResumidas() {
		int tamanhoDoArray = transacoesExistentes.size();
		String[] transacoes = new String[tamanhoDoArray];

		for (int i = 0; i < tamanhoDoArray; i++) {
			transacoes[i] = transacoesExistentes.get(i).toStringResumido();
		}

		return transacoes;
	}

	/**
	 * Método que lista as transações que o usuário têm de forma detalhada.
	 * 
	 * @return Um Array de String com informações detalhadas das transações que
	 *         o usuário têm.
	 */

	public String[] listaTransacoesDetalhadas() {
		int tamanhoDoArray = transacoesExistentes.size();
		String[] transacoes = new String[tamanhoDoArray];

		for (int i = 0; i < tamanhoDoArray; i++) {
			transacoes[i] = transacoesExistentes.get(i).toString();
		}

		return transacoes;
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
			Double valor2 = Double.parseDouble(valor);
			if (valor2 <= 0)
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
		return true;
	}

	/**
	 * Retorna uma lista com as transações existentes de um determinado usuário.
	 * 
	 * @return Uma lista com as transações existentes de um determinado usuário.
	 */

	public ArrayList<Transacao> getTransacoesExistentes() {
		return transacoesExistentes;
	}
}
