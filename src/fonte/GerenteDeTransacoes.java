package fonte;

import java.util.ArrayList;
import java.util.HashMap;
import auxiliar.ArquivadorTransacoes;

public class GerenteDeTransacoes {

	private HashMap<Usuario, ArrayList<Transacao>> transacoesDoSistema;
	private ArrayList<Transacao> transacoesExistentes;
	private ArquivadorTransacoes arquivador;
	private Usuario usuario;

	public GerenteDeTransacoes(Usuario usuario) {
		try {
			arquivador = new ArquivadorTransacoes("transacoes.txt");
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

	public void adicionaTransacao(String tipoDeTransacao, String descricao,
			String dataDeInsercao, double valor, Categoria categoria,
			String recorrencia) throws Exception {

		transacaoValida(tipoDeTransacao, descricao, dataDeInsercao, valor,
				categoria, recorrencia);

		Transacao novaTransacao = new Transacao(tipoDeTransacao, descricao,
				dataDeInsercao, valor, categoria, recorrencia);
		transacoesExistentes.add(novaTransacao);
		transacoesDoSistema.put(usuario, transacoesExistentes);

		arquivador.escreveTransacoes(transacoesDoSistema);
	}

	public void removeTransacao(Transacao transacao) throws Exception {
		if (!transacoesExistentes.contains(transacao))
			throw new Exception("Transação inexistente.");

		transacoesExistentes.remove(transacao);
		transacoesDoSistema.put(usuario, transacoesExistentes);

		arquivador.escreveTransacoes(transacoesDoSistema);
	}

	public void editaTransacao(Transacao transacaoParaEditar,
			String tipoDeTransacao, String descricao, String dataDeInsercao,
			double valor, Categoria categoria, String recorrencia)
			throws Exception {

		transacaoValida(tipoDeTransacao, descricao, dataDeInsercao, valor,
				categoria, recorrencia);
		Transacao novaTransacao = new Transacao(tipoDeTransacao, descricao,
				dataDeInsercao, valor, categoria, recorrencia);

		transacoesExistentes.remove(transacaoParaEditar);
		transacoesExistentes.add(novaTransacao);

		transacoesDoSistema.put(usuario, transacoesExistentes);

		arquivador.escreveTransacoes(transacoesDoSistema);
	}

	private void transacaoValida(String tipoDeTransacao, String descricao,
			String dataDeInsercao, double valor, Categoria categoria,
			String recorrencia) throws Exception {
		if (!tipoDeTransacaoValido(tipoDeTransacao)) {
			throw new Exception("Tipo de transação inválido.");
		}
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

	private boolean tipoDeTransacaoValido(String tipoDeTransacao) {
		if (tipoDeTransacao == null
				|| !tipoDeTransacao.equals("despesa".toLowerCase())
				|| !tipoDeTransacao.equals("provento".toLowerCase()))
			return false;
		return true;
	}
}
