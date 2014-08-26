package fonte;

import java.util.List;

public interface Relatorio {

	public List<Despesa> valoresDespesaPorMes(int mes);
	
	public List<Provento> valoresProventoPorMes(int mes);
	
	public List<Transacao> valoresCategoriasDeUmMes(int mes);
	
	public List<Transacao> valoresDeUmaCategoria();
	
	public List<Transacao> valoresDeUmMes(int mes);
	
}
