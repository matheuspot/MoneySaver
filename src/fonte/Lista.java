package fonte;

import java.util.List;

public class Lista implements Modo<List<Transacao>> {
	private Usuario usuarioAtivo;
	
	public Lista(Usuario usuario) {
		usuarioAtivo = usuario;
	}
	
	@Override
	public List<Transacao> vizualizar() {
		return usuarioAtivo.getContaAtiva().getTransacoesExistentes();
	}

}
