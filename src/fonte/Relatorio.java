package fonte;

import java.util.List;

public interface Relatorio {

	public List<Double> valoresDespesaPorMes();

	public List<Double> valoresProventoPorMes();

	public List<Double> valoresCategoriasPorMes();

	public List<Double> valoresDeUmaCategoria();

	public List<Double> valoresDeUmMes(int mes);
}
