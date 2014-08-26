package fonte;

import java.util.ArrayList;
import java.util.List;

public class Histograma implements Relatorio{
	
	private List<Provento> listaProventos;
	private List<Despesa> listaDespesas;
	private Provento provento;
	private Despesa despesa;
	private final String[] MESES = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
    		"Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    private String[] categorias; 
	private GerenteDeCategorias gerenteCategorias;
	private GerenteDeTransacoes gerenteTransacoes;
	
	public Histograma(Usuario usuarioAtivo){
		gerenteCategorias = new GerenteDeCategorias(usuarioAtivo);
		categorias = gerenteCategorias.listaCategorias();
		gerenteTransacoes = new GerenteDeTransacoes(usuarioAtivo);
		listaProventos = new ArrayList<>();
		listaDespesas = new ArrayList<>();
	}

	@Override
	public List<Despesa> valoresDespesaPorMes(int mes) {
				
		return listaDespesas;
	}

	@Override
	public List<Provento> valoresProventoPorMes(int mes) {
		
		return listaProventos;
	}

	@Override
	public double[] valoresCategoriasDeUmMes(int mes) {
		return null;
	}


	@Override
	public double[] valoresDeUmMes(int mes) {
		// TODO Auto-generated method stub
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
	public double[] valoresDeUmaCategoria() {
		// TODO Auto-generated method stub
		return null;
	}

}
