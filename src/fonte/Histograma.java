package fonte;

import java.util.ArrayList;
import java.util.List;

public class Histograma implements Modo<List<Double>> {
	private Usuario usuarioAtivo;
	
	public Histograma(Usuario usuario) {
		usuarioAtivo = usuario;
	}
	
	@Override
	public List<Double> vizualizar() {
		List<Double> valores = new ArrayList<>();
		
		for (Transacao transacao : usuarioAtivo.getContaAtiva().getTransacoesExistentes()) {
			valores.add(transacao.getValor());
		} return valores;
	}

}
