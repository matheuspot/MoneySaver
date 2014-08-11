package fonte;

import java.util.ArrayList;
import java.util.HashMap;
import auxiliar.ArquivadorTransacoes;

public class GerenteDeTransacoes {

	private HashMap<Usuario, ArrayList<Transacao>> transacoesDoSistema;
	private ArrayList<Transacao> transacoesExistentes;
	private ArquivadorTransacoes arquivador;
	private Usuario usuario;
	private Transacao transacaoQueSeraAdicionada;

	public GerenteDeTransacoes(Usuario usuario) {
		try {
			arquivador = new ArquivadorTransacoes("data2.mos");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		if (arquivador.leTransacoes() == null) {
			transacoesDoSistema = new HashMap<Usuario, ArrayList<Transacao>>();
			transacoesExistentes = new ArrayList<>();
		} else {
			transacoesDoSistema = new HashMap<Usuario, ArrayList<Transacao>>(
					arquivador.leTransacoes());
			transacoesExistentes = new ArrayList<>(
					transacoesDoSistema.get(usuario));
		}
		this.usuario = usuario;
	}

	public void adicionaTransacao(String descricao, String dataDeInsercao,
			double valor, Categoria categoria, String recorrencia,
			String tipoDeTransacao) throws Exception {

		transacaoValida(descricao, dataDeInsercao, valor, categoria,
				recorrencia, tipoDeTransacao);

		if (tipoDeTransacao.equals("despesa")) {
			transacaoQueSeraAdicionada = new Despesa(descricao, dataDeInsercao,
					valor, categoria, recorrencia);
		} else if (tipoDeTransacao.equals("provento")) {
			transacaoQueSeraAdicionada = new Provento(descricao,
					dataDeInsercao, valor, categoria, recorrencia);
		}

		usuario.getConta().moveDinheiroNaConta(
				transacaoQueSeraAdicionada.getValor());
		transacoesExistentes.add(transacaoQueSeraAdicionada);
		transacoesDoSistema.put(usuario, transacoesExistentes);

		arquivador.escreveTransacoes(transacoesDoSistema);
	}

	public void removeTransacao(Transacao transacao) throws Exception {
		if (!transacoesExistentes.contains(transacao))
			throw new Exception("Transação inexistente.");

		usuario.getConta().moveDinheiroNaConta(-transacao.getValor());
		transacoesExistentes.remove(transacao);
		transacoesDoSistema.put(usuario, transacoesExistentes);

		arquivador.escreveTransacoes(transacoesDoSistema);
	}

	public void editaTransacao(Transacao transacaoParaEditar, String descricao,
			String dataDeInsercao, double valor, Categoria categoria,
			String recorrencia, String tipoDeTransacao) throws Exception {

		transacaoValida(descricao, dataDeInsercao, valor, categoria,
				recorrencia, tipoDeTransacao);

		if (tipoDeTransacao.equals("despesa")) {
			transacaoQueSeraAdicionada = new Despesa(descricao, dataDeInsercao,
					valor, categoria, recorrencia);
		} else if (tipoDeTransacao.equals("provento")) {
			transacaoQueSeraAdicionada = new Provento(descricao,
					dataDeInsercao, valor, categoria, recorrencia);
		}

		usuario.getConta().moveDinheiroNaConta(-transacaoParaEditar.getValor());
		transacoesExistentes.remove(transacaoParaEditar);
		usuario.getConta().moveDinheiroNaConta(
				transacaoQueSeraAdicionada.getValor());
		transacoesExistentes.add(transacaoQueSeraAdicionada);

		transacoesDoSistema.put(usuario, transacoesExistentes);

		arquivador.escreveTransacoes(transacoesDoSistema);
	}

	public String[] listaTransacoesResumidas() {
		int tamanhoDoArray = transacoesExistentes.size();
		String[] transacoes = new String[tamanhoDoArray];

		for (int i = 0; i < tamanhoDoArray; i++) {
			transacoes[i] = transacoesExistentes.get(i).toStringResumido();
		}

		return transacoes;
	}
	
	public String[] listaTransacoesDetalhadas() {
		int tamanhoDoArray = transacoesExistentes.size();
		String[] transacoes = new String[tamanhoDoArray];
		
		for (int i = 0; i < tamanhoDoArray; i++) {
			transacoes[i] = transacoesExistentes.get(i).toString();
		}
		
		return transacoes;
	}

	private void transacaoValida(String descricao, String dataDeInsercao,
			double valor, Categoria categoria, String recorrencia,
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

	private boolean tipoDeTransacaoValido(String tipoDeTransacao) {
		if (!tipoDeTransacao.equals("despesa")
				|| !tipoDeTransacao.equals("provento"))
			return false;
		return true;
	}

	private boolean recorrenciaValida(String recorrencia) {
		if (recorrencia == null || recorrencia.trim().length() == 0)
			return false;
		return true;
	}

	private boolean categoriaValida(Categoria categoria) {
		if (categoria == null)
			return false;
		return true;
	}

	private boolean valorValido(double valor) {
		if (valor <= 0)
			return false;
		return true;
	}

	private boolean dataDeInsercaoValida(String dataDeInsercao) {
		if (dataDeInsercao == null)
			return false;
		return true;
	}

	private boolean descricaoValida(String descricao) {
		if (descricao == null)
			return false;
		return true;
	}
}
