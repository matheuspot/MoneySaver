package fonte;

import java.util.List;

public interface Modo<TipoDaLista> {
	
	public TipoDaLista getTransacoesPreparada(List<Transacao> transacoesFiltrada);
	
}
