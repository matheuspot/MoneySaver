package fonte;

import java.util.ArrayList;
import java.util.HashMap;
import auxiliar.ArquivadorCategorias;

public class GerenteDeCategorias {

	private HashMap<Usuario, ArrayList<Categoria>> categoriasDoSistema;
	private ArrayList<Categoria> categoriasExistentes;
	private ArquivadorCategorias arquivador;
	private Usuario usuario;

	public GerenteDeCategorias(Usuario usuario) {
		try {
			arquivador = new ArquivadorCategorias("data3.mos");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		if (arquivador.leCategorias() == null) {
			categoriasDoSistema = new HashMap<Usuario, ArrayList<Categoria>>();
			categoriasExistentes = new ArrayList<>();
		} else {
			categoriasDoSistema = new HashMap<Usuario, ArrayList<Categoria>>(
					arquivador.leCategorias());
			categoriasExistentes = new ArrayList<>(
					categoriasDoSistema.get(usuario));
		}
		this.usuario = usuario;

		if (categoriasExistentes.isEmpty())
			adicionaCategoriasDefault();
	}

	private void adicionaCategoriasDefault() {
		try {
			categoriasExistentes.add(new Categoria("Lazer", "Verde"));
			categoriasExistentes.add(new Categoria("Alimentação", "Azul"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		categoriasDoSistema.put(usuario, categoriasExistentes);

		try {
			arquivador.escreveCategorias(categoriasDoSistema);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Categoria[] listaCategorias() {
		Categoria[] arrayDeCategorias = (Categoria[]) categoriasExistentes
				.toArray();
		return arrayDeCategorias;
	}

	public void adicionaCategoria(String nomeCategoria, String corCategoria)
			throws Exception {

		categoriaValida(nomeCategoria, corCategoria);

		Categoria novaCategoria = new Categoria(nomeCategoria, corCategoria);
		categoriasExistentes.add(novaCategoria);
		categoriasDoSistema.put(usuario, categoriasExistentes);

		arquivador.escreveCategorias(categoriasDoSistema);
	}

	public void removeCategoria(Categoria categoria) throws Exception {
		if (!categoriasExistentes.contains(categoria))
			throw new Exception("Categoria inexistente.");

		categoriasExistentes.remove(categoria);
		categoriasDoSistema.put(usuario, categoriasExistentes);

		arquivador.escreveCategorias(categoriasDoSistema);
	}

	public void editaCategoria(Categoria categoriaParaEditar,
			String nomeCategoria, String corCategoria) throws Exception {

		categoriaValida(nomeCategoria, corCategoria);

		Categoria novaCategoria = new Categoria(nomeCategoria, corCategoria);

		categoriasExistentes.remove(categoriaParaEditar);
		categoriasExistentes.add(novaCategoria);

		categoriasDoSistema.put(usuario, categoriasExistentes);

		arquivador.escreveCategorias(categoriasDoSistema);
	}

	public void categoriaValida(String nomeCategoria, String corCategoria)
			throws Exception {
		if (!nomeValido(nomeCategoria))
			throw new Exception("Nome de categoria inválido.");
		if (!corValido(corCategoria))
			throw new Exception("Cor inválida.");
	}

	private boolean nomeValido(String nomeCategoria) {
		if (nomeCategoria == null || nomeCategoria.length() == 0)
			return false;
		return true;
	}

	private boolean corValido(String corCategoria) {
		if (corCategoria == null || corCategoria.length() == 0)
			return false;
		return true;
	}
}
