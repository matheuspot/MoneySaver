package fonte;

import java.util.ArrayList;
import java.util.List;

public class RelatorioLista {

	private Usuario usuarioAtivo;

	public RelatorioLista(Usuario usuario) {
		usuarioAtivo = usuario;
	}

	public List<Transacao> listaDespesas() {
		List<Transacao> transacoesDoMes;
		List<Transacao> transacoesFinais = new ArrayList<>();

		for (int i = 1; i < 13; i++) {
			transacoesDoMes = usuarioAtivo.getContaAtiva()
					.listaTransacoesPeloMes(i);
			for (Transacao transacao : transacoesDoMes) {
				if (transacao.getValor() < 0)
					transacoesFinais.add(transacao);
			}
		}
		return transacoesFinais;
	}

	public List<Transacao> listaProventos() {
		List<Transacao> transacoesDoMes;
		List<Transacao> transacoesFinais = new ArrayList<>();

		for (int i = 1; i < 13; i++) {
			transacoesDoMes = usuarioAtivo.getContaAtiva()
					.listaTransacoesPeloMes(i);
			for (Transacao transacao : transacoesDoMes) {
				if (transacao.getValor() > 0)
					transacoesFinais.add(transacao);
			}
		}
		return transacoesFinais;
	}

	public List<List<Transacao>> listaCategorias(int mes) {
		List<Categoria> categorias = usuarioAtivo.getCategorias();
		List<Transacao> transacoesDoMes = usuarioAtivo.getContaAtiva()
				.listaTransacoesPeloMes(mes);

		List<Transacao> transacoesPorCategoria;
		List<List<Transacao>> transacoesFinais = new ArrayList<>();

		for (Categoria categoria : categorias) {
			transacoesPorCategoria = new ArrayList<>();
			for (Transacao transacao : transacoesDoMes) {
				if (transacao.getCategoria().equals(categoria))
					transacoesPorCategoria.add(transacao);
			}
			transacoesFinais.add(transacoesPorCategoria);
		}
		return transacoesFinais;
	}

	public List<Transacao> listaIntervaloMes(int mesInicial, int mesFinal) {
		List<Transacao> transacoesDoIntervalo = new ArrayList<>();
		for (int i = mesInicial; i <= mesFinal; i++) {
			transacoesDoIntervalo.addAll(usuarioAtivo.getContaAtiva()
					.listaTransacoesPeloMes(i));
		}
		return transacoesDoIntervalo;
	}
}
