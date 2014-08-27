package fonte;

import java.util.ArrayList;
import java.util.List;

public class RelatorioHistograma {

	private final static String[] MESES = { "Janeiro", "Fevereiro", "Março",
			"Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro",
			"Novembro", "Dezembro" };

	private GerenteDeCategorias gerenteCategorias;
	private GerenteDeTransacoes gerenteTransacoes;

	public RelatorioHistograma(Usuario usuario) {
		gerenteTransacoes = new GerenteDeTransacoes(usuario);
		gerenteCategorias = new GerenteDeCategorias(usuario);
	}

	public List<Double> valoresDespesas() {
		List<Double> despesas = new ArrayList<>();
		List<Transacao> transacoesDoMes = new ArrayList<>();

		for (int i = 1; i < 13; i++) {
			transacoesDoMes = gerenteTransacoes.listaTransacoesPeloMes(i);
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
			transacoesDoMes = gerenteTransacoes.listaTransacoesPeloMes(i);
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
		List<Categoria> categorias = gerenteCategorias.getCategorias();
		List<Transacao> transacoesDoMes = gerenteTransacoes
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

	public List<Double> valoresDeUmMes(int mes) {
		return null;
	}

	public static String[] getMeses() {
		return MESES;
	}
}