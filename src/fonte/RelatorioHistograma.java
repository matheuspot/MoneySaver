package fonte;

import java.util.ArrayList;
import java.util.List;

public class RelatorioHistograma implements Relatorio {

	private final static String[] MESES = { "Janeiro", "Fevereiro", "Março",
			"Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro",
			"Novembro", "Dezembro" };

	private Usuario usuarioAtivo;
	private GerenteDeCategorias gerenteCategorias;
	private GerenteDeTransacoes gerenteTransacoes;
	private List<Transacao> transacoesDoMes;

	public RelatorioHistograma(Usuario usuario) {
		usuarioAtivo = usuario;
		gerenteTransacoes = new GerenteDeTransacoes(usuarioAtivo);
		gerenteCategorias = new GerenteDeCategorias(usuarioAtivo);
	}

	@Override
	public List<Double> valoresDespesas() {
		List<Double> despesas = new ArrayList<>();
		double total;

		for (int i = 1; i < 13; i++) {
			transacoesDoMes = gerenteTransacoes.listaTransacoesPeloMes(i);
			total = 0;
			for (Transacao transacao : transacoesDoMes) {
				if (transacao.getValor() < 0)
					total += Math.abs(transacao.getValor());
			}
			despesas.add(total);
		}
		return despesas;
	}

	@Override
	public List<Double> valoresProventos() {
		List<Double> proventos = new ArrayList<>();
		double total;

		for (int i = 1; i < 13; i++) {
			transacoesDoMes = gerenteTransacoes.listaTransacoesPeloMes(i);
			total = 0;
			for (Transacao transacao : transacoesDoMes) {
				if (transacao.getValor() > 0)
					total += transacao.getValor();
			}
			proventos.add(total);
		}
		return proventos;
	}

	@Override
	public List<Double> valoresCategorias(int mes) {
		return null;
	}

	@Override
	public List<Double> valoresDeUmMes(int mes) {
		return null;
	}

	public static String[] getMeses() {
		return MESES;
	}
}
