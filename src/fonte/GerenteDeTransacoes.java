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

	private void transacaoValida(String tipoDeTransacao, String descricao,
			String dataDeInsercao, double valor, Categoria categoria,
			String recorrencia) throws Exception {
		if (tipoDeTransacaoValido()) {
			throw new Exception("Tipo de transação inválido.");
		}
		if (descricaoValida()) {
			throw new Exception("Descrição inválida.");
		}
		if (dataDeInsercaoValida()) {
			throw new Exception("Data de inserção inválida.");
		}
		if (valorValido()) {
			throw new Exception("Valor inválido.");
		}
		if (categoriaValida()) {
			throw new Exception("Categoria inválida.");
		}
		if (recorrenciaValida()) {
			throw new Exception("Recorrência inválida.");
		}
	}

	private boolean recorrenciaValida() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean categoriaValida() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean valorValido() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean dataDeInsercaoValida() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean descricaoValida() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean tipoDeTransacaoValido() {
		// TODO Auto-generated method stub
		return false;
	}
}
