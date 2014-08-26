package fonte;

import java.util.ArrayList;
import java.util.List;

public class Histograma implements Relatorio{
	
	private final String[] MESES = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
    		"Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    private String[] categorias; 
	private GerenteDeCategorias gerenteCategorias;
	private GerenteDeTransacoes gerenteTransacoes;
	
	public Histograma(Usuario usuarioAtivo){
		gerenteCategorias = new GerenteDeCategorias(usuarioAtivo);
		categorias = gerenteCategorias.listaCategorias();
		gerenteTransacoes = new GerenteDeTransacoes(usuarioAtivo);
	}

	@Override
	public List<Double> valoresDespesaPorMes(int mes) {
				
		return null;
	}

	@Override
	public List<Double> valoresProventoPorMes(int mes) {
		
		return null;
	}

	@Override
	public List<Double> valoresCategoriasDeUmMes(int mes) {
		return null;
	}


	@Override
	public List<Double> valoresDeUmMes(int mes) {
		
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
