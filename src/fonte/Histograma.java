package fonte;

public class Histograma implements Relatorio{
	
	private final String[] MESES = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
    		"Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};;
    private String[] categorias; 
	private GerenteDeCategorias gerenteCategorias;
	private GerenteDeTransacoes gerenteTransacoes;
	
	public Histograma(Usuario usuarioAtivo){
		gerenteCategorias = new GerenteDeCategorias(usuarioAtivo);
		categorias = gerenteCategorias.listaCategorias();
		gerenteTransacoes = new GerenteDeTransacoes(usuarioAtivo);
	}

	@Override
	public double[] valoresDespesaPorMes() {
		
		return null;
	}

	@Override
	public double[] valoresProventoPorMes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] valoresCategoriasPorMes() {
		
		return null;
	}

	@Override
	public double[] valoresCategoriasDeUmMes() {
		return null;
	}


	@Override
	public double[] valoresProventoDespesaDeUmaData() {
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

}
