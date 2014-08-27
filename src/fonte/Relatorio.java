package fonte;

import java.util.List;

public interface Relatorio {

	public List<Double> valoresDespesas();

	public List<Double> valoresProventos();

	public List<Double> valoresCategorias(int mes);

	public List<Double> valoresDeUmMes(int mes);
}
