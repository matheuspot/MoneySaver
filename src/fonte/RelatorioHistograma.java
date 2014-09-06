package fonte;

import java.util.ArrayList;
import java.util.List;

public class RelatorioHistograma {

	private Usuario usuarioAtivo;

	public RelatorioHistograma(Usuario usuario) {
		usuarioAtivo = usuario;
	}

	public List<Double> valoresDespesas() {
		List<Double> despesas = new ArrayList<>();
		List<Transacao> transacoesDoMes = new ArrayList<>();

		for (int i = 1; i < 13; i++) {
			transacoesDoMes = usuarioAtivo.getContaAtiva()
					.listaTransacoesPeloMes(i);
			double total = 0;
			for (Transacao transacao : transacoesDoMes) {
				if (transacao.getValor() < 0)
					total += Math.abs(transacao.getValor());
			}
			despesas.add(total);
		}
		return despesas;
	}

	public List<Double> valoresProventos() {
		List<Double> proventos = new ArrayList<>();
		List<Transacao> transacoesDoMes = new ArrayList<>();

		for (int i = 1; i < 13; i++) {
			transacoesDoMes = usuarioAtivo.getContaAtiva()
					.listaTransacoesPeloMes(i);
			double total = 0;
			for (Transacao transacao : transacoesDoMes) {
				if (transacao.getValor() > 0)
					total += transacao.getValor();
			}
			proventos.add(total);
		}
		return proventos;
	}

	public List<List<Double>> valoresCategorias(int mes) {
		List<Categoria> categorias = usuarioAtivo.getCategorias();
		List<Transacao> transacoesDoMes = usuarioAtivo.getContaAtiva()
				.listaTransacoesPeloMes(mes);
		List<Double> valoresProvento = new ArrayList<>();
		List<Double> valoresDespesa = new ArrayList<>();

		for (int i = 0; i < categorias.size(); i++) {
			double totalProventos = 0;
			double totalDespesas = 0;
			Categoria categoriaAtual = categorias.get(i);

			for (Transacao transacao : transacoesDoMes) {
				if (transacao.getCategoria().equals(categoriaAtual)
						&& transacao.getValor() > 0)
					totalProventos += transacao.getValor();
				else if (transacao.getCategoria().equals(categoriaAtual)
						&& transacao.getValor() < 0)
					totalDespesas += Math.abs(transacao.getValor());
			}

			valoresProvento.add(totalProventos);
			valoresDespesa.add(totalDespesas);
		}

		List<List<Double>> valoresFinais = new ArrayList<>();
		valoresFinais.add(valoresProvento);
		valoresFinais.add(valoresDespesa);
		return valoresFinais;
	}

	public List<List<Double>> valoresIntervaloMes(int mesInicial, int mesFinal) {
		List<Transacao> transacoesDoMes = new ArrayList<>();
		List<Double> valoresProvento = new ArrayList<>();
		List<Double> valoresDespesa = new ArrayList<>();

		for (int i = mesInicial; i <= mesFinal; i++) {
			transacoesDoMes = usuarioAtivo.getContaAtiva()
					.listaTransacoesPeloMes(i);
			double totalProventos = 0;
			double totalDespesas = 0;

			for (Transacao transacao : transacoesDoMes) {
				if (transacao.getValor() > 0)
					totalProventos += transacao.getValor();
				else if (transacao.getValor() < 0)
					totalDespesas += Math.abs(transacao.getValor());
			}

			valoresProvento.add(totalProventos);
			valoresDespesa.add(totalDespesas);
		}

		List<List<Double>> valoresFinais = new ArrayList<>();
		valoresFinais.add(valoresProvento);
		valoresFinais.add(valoresDespesa);
		return valoresFinais;
	}
}