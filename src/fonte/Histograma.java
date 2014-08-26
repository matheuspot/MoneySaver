package fonte;

import java.util.ArrayList;
import java.util.List;

public class Histograma implements Relatorio{
	
	private final String[] MESES = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
    		"Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    private String[] categorias; 
	private Usuario usuarioAtivo;
	private GerenteDeCategorias gerenteCategorias = new GerenteDeCategorias(usuarioAtivo);
	private GerenteDeTransacoes gerenteTransacoes;
	private List<Transacao> transacoesDoMes;
	
	public Histograma(Usuario usuario){
		usuarioAtivo = usuario;
		gerenteTransacoes = new GerenteDeTransacoes(usuarioAtivo);
	}

	@Override
	public List<Double> valoresDespesaPorMes() {
		List<Double> despesasDoAno = new ArrayList<Double>();
		double total;
		
		for (int i=1; i<13; i++){
			transacoesDoMes = gerenteTransacoes.listaTransacoesPeloMes(i);
			total = 0;
			for (Transacao transacao : transacoesDoMes) {
				if (transacao.getValor() < 0)
					total += Math.abs(transacao.getValor());
			}
			despesasDoAno.add(total);
		}
		return despesasDoAno;
	}

	@Override
	public List<Double> valoresProventoPorMes() {
		List<Double> proventosDoAno = new ArrayList<Double>();
		double total;
		
		for (int i=1; i<13; i++){
			transacoesDoMes = gerenteTransacoes.listaTransacoesPeloMes(i);
			total = 0;
			for (Transacao transacao : transacoesDoMes) {
				if (transacao.getValor() > 0)
					total += transacao.getValor();
			}
			proventosDoAno.add(total);
		}
		return proventosDoAno;
	}

	@Override
	public List<Double> valoresCategoriasDeUmMes() {
		return null;
	}


	@Override
	public List<Double> valoresDeUmMes() {
		// TODasdsdsaO Auto-generated method stub
		return null;
	}
	
	/**
	 * @return the categorias
	 */
	public String[] getCategorias() {
		return categorias;
	}

	/**
	 * @return the mESES
	 */
	public String[] getMESES() {
		return MESES;
	}

	@Override
	public List<Double> valoresDeUmaCategoria() {
		// TODO Auto-generated method stub
		return null;
	}

}
