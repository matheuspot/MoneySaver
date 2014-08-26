package fonte;

import java.util.List;

public interface Relatorio {

	public List<Double> valoresDespesaPorMes(int mes);
	
	public List<Double> valoresProventoPorMes(int mes);
	
	public List<Double> valoresCategoriasDeUmMes(int mes);
	
	public List<Double> valoresDeUmaCategoria();
	
	public List<Double> valoresDeUmMes(int mes);
	
}
