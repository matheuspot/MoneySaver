package fonte;

import java.util.ArrayList;
import java.util.List;
import excecao.MoneySaverException;

/**
 * Classe usada para representar um relatório.
 */
public class Relatorio {

	private ArrayList<Transacao> transacoes;
	private final ArrayList<Transacao> transacoesAuxiliar;

	/**
	 * Construtor de relatório.
	 * 
	 * @param contaAtiva
	 *            A conta ativa no momento.
	 * @throws MoneySaverException
	 *             Lança exceção se a conta não tiver transações.
	 */
	@SuppressWarnings("unchecked")
	public Relatorio(Conta contaAtiva) throws MoneySaverException {
		transacoes = (ArrayList<Transacao>) contaAtiva
				.getTransacoesExistentes();
		if (transacoes == null || transacoes.isEmpty())
			throw new MoneySaverException("Conta sem transações.");

		transacoesAuxiliar = (ArrayList<Transacao>) transacoes.clone();
	}

	/**
	 * Método usado para filtrar as transações por categoria.
	 * 
	 * @param categoria
	 *            A categoria que deseja-se filtrar.
	 */
	@SuppressWarnings("unchecked")
	public void filtraPorCategoria(Categoria categoria) {
		for (Transacao transacao : transacoes) {
			if (!categoria.equals(transacao.getCategoria()))
				transacoesAuxiliar.remove(transacao);
		}
		transacoes = (ArrayList<Transacao>) transacoesAuxiliar.clone();
	}

	/**
	 * Método usado para filtrar as transações por intervalo de mês.
	 * 
	 * @param mesInicial
	 *            O mês inicial.
	 * @param mesFinal
	 *            O mês final.
	 */
	@SuppressWarnings("unchecked")
	public void filtraPorData(int mesInicial, int mesFinal) {

		for (Transacao transacao : transacoes) {
			if (transacao.getDataDeInsercao().getMonthValue() < mesInicial
					|| transacao.getDataDeInsercao().getMonthValue() > mesFinal)
				transacoesAuxiliar.remove(transacao);
		}
		transacoes = (ArrayList<Transacao>) transacoesAuxiliar.clone();
	}

	/**
	 * Método usado para filtrar as transações pelo tipo de transação.
	 * 
	 * @param tipoDaTransacao
	 *            O tipo de transação.
	 */
	@SuppressWarnings("unchecked")
	public void filtraPorTipo(String tipoDaTransacao) {
		for (Transacao transacao : transacoes) {

			if (tipoDaTransacao.equals("Despesa"))
				if (transacao.getValor() > 0)
					transacoesAuxiliar.remove(transacao);
			if (tipoDaTransacao.equals("Provento"))
				if (transacao.getValor() < 0)
					transacoesAuxiliar.remove(transacao);
		}
		transacoes = (ArrayList<Transacao>) transacoesAuxiliar.clone();
	}

	/**
	 * Método que dá acesso à lista de transações filtrada.
	 * 
	 * @return A lista de transações filtradas.
	 */
	public List<Transacao> getListaFiltrada() {
		return transacoes;
	}

	/**
	 * Método que irá preparar a lista de transações para visualização.
	 * 
	 * @param modo
	 *            O modo que deseja-se visualizar.
	 * @return A lista preparada para se visualizar no modo escolhido.
	 */
	public List<?> getTransacoesPreparadas(Modo<?> modo) {
		return (List<?>) modo.getTransacoesPreparada(transacoes);
	}
}
