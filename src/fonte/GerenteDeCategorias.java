package fonte;

import java.util.ArrayList;
import auxiliar.ArquivadorCategorias;

public class GerenteDeCategorias {
	private ArquivadorCategorias arquivadorCategorias;
	private ArrayList<Categoria> listaCategorias;
	
	public GerenteDeCategorias() {
		try {
			arquivadorCategorias = new ArquivadorCategorias("categorias.txt");
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
		if (arquivadorCategorias.leCategorias() == null) {
			listaCategorias = new ArrayList<Categoria>();
		} else {
			listaCategorias = new ArrayList<Categoria>(arquivadorCategorias.leCategorias());
		}		
	}
	
	public void adicionaCategoria(String nomeCategoria) throws Exception {
		categoriaValida(nomeCategoria);
		Categoria categoria = new Categoria(nomeCategoria);
		listaCategorias.add(categoria);
		arquivadorCategorias.escreveCategorias(listaCategorias);
	}
	
	public void categoriaValida(String nomeCategoria) throws Exception {
		if(nomeCategoria==null || nomeCategoria.length()==0)
			throw new Exception("Nome da categoria está invalido.");
	}
	

}
