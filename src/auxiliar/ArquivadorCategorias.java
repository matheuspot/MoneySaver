package auxiliar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import fonte.Categoria;
import fonte.Transacao;
import fonte.Usuario;

public class ArquivadorCategorias {
	private String nomeDoArquivo;
	private FileOutputStream fos;
	private ObjectOutputStream oos;
	private FileInputStream fis;
	private ObjectInputStream ois;
	private ArrayList<Categoria> categoriasJaEscritas;
	
	/**
	 * Construtor do arquivador, que tem como parâmetro o nome desejado para o
	 * arquivo no qual será armazenado as categorias.
	 * 
	 * @param nomeDoArquivo
	 *            Nome desejado para o arquivo no qual será armazenado as
	 *            transações.
	 * @throws Exception
	 *             Lança exceção caso o nome do arquivo seja inválido.
	 */
	public ArquivadorCategorias(String nomeDoArquivo) throws Exception {
		if (nomeDoArquivo == null || nomeDoArquivo.trim().length() == 0) {
			throw new Exception("Nome do arquivo inválido.");
		}
		this.nomeDoArquivo = nomeDoArquivo;
		
	}
	
	/**
	 * Método que recebe um ArrayList para escrever as categorias
	 * 
	 * @param categoriasJaEscritas
	 *            Um ArrayList para
	 *            escrever no arquivo.
	 * @throws Exception
	 *             Lança exceção se o ArrayList estiver vazio.
	 */
	
	public void escreveCategorias(ArrayList<Categoria> listaCategorias) throws Exception {
		
		if (listaCategorias.isEmpty()) {
			throw new Exception(
					"Coleção de categorias para escrever está vazia.");
		}

		try {
			fos = new FileOutputStream(nomeDoArquivo);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(listaCategorias);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que irá ler as categorias que estão
	 * escritas no arquivo. Se o arquivo não existir, irá retornar null.
	 * 
	 * @return Um ArrayList contendo as categorias.
	 */
	
	@SuppressWarnings("unchecked")
	public ArrayList<Categoria> leCategorias() {
		try {
			try {
				fis = new FileInputStream(nomeDoArquivo);
			} catch (Exception e) {
				return null;
			}

			ois = new ObjectInputStream(fis);
			categoriasJaEscritas = (ArrayList<Categoria>) ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoriasJaEscritas;
	}
	
	/**
	 * Método que irá remover uma categoria que está escrita no
	 * arquivo.
	 * 
	 * @param categoria
	 *            O nome da categoria que deseja remover.
	 *            
	 */

	public void deletaCategoria(Categoria categoria) {
		ArrayList<Categoria> listaCategorias = new ArrayList<Categoria>(leCategorias());

		listaCategorias.remove(categoria);

		try {
			escreveCategorias(listaCategorias);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
