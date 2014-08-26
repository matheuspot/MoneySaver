package fonte;

import java.util.List;

public interface Relatorio {

	public List<Despesa> valoresDespesaPorMes(int mes);
	
	public List<Provento> valoresProventoPorMes(int mes);
	
	public double[] valoresCategoriasDeUmMes(int mes);
	
	public double[] valoresDeUmaCategoria();
	
	public double[] valoresDeUmMes(int mes);
	
}
