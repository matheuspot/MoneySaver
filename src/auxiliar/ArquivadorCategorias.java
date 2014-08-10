package auxiliar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import fonte.Categoria;
import fonte.Usuario;

/**
 * Classe usada para escrever uma coleção de objetos da classe Categoria em um
 * arquivo.
 */

public class ArquivadorCategorias {
	private String nomeDoArquivo;
	private FileOutputStream fos;
	private ObjectOutputStream oos;
	private FileInputStream fis;
	private ObjectInputStream ois;
	private HashMap<Usuario, ArrayList<Categoria>> categoriasJaEscritas;

	/**
	 * Construtor do arquivador, que tem como parâmetro o nome desejado para o
	 * arquivo no qual será armazenado as categorias.
	 * 
	 * @param nomeDoArquivo
	 *            Nome desejado para o arquivo no qual será armazenado as
	 *            categorias.
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
	 * Método que recebe um HashMap(Key: Usuario, Value: ArrayList de Categoria)
	 * para escrever no arquivo.
	 * 
	 * @param mapaDeCategorias
	 *            Um HashMap(Key: Usuario, Value: ArrayList de Categoria) para
	 *            escrever no arquivo.
	 * @throws Exception
	 *             Lança exceção se o HashMap estiver vazio.
	 */

	public void escreveCategorias(
			HashMap<Usuario, ArrayList<Categoria>> mapaDeCategorias)
			throws Exception {
		if (mapaDeCategorias.isEmpty()) {
			throw new Exception(
					"Coleção de categorias para escrever está vazia.");
		}

		try {
			fos = new FileOutputStream(nomeDoArquivo);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(mapaDeCategorias);
			oos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que irá ler as categorias de seus respectivos usuários que estão
	 * escritos no arquivo. Se o arquivo não existir, irá retornar null.
	 * 
	 * @return Um HashMap(Key: Usuario, Value: ArrayList de Categoria). Caso o
	 *         arquivo não exista, irá retornar null.
	 */

	@SuppressWarnings("unchecked")
	public HashMap<Usuario, ArrayList<Categoria>> leCategorias() {
		try {
			try {
				fis = new FileInputStream(nomeDoArquivo);
			} catch (Exception e) {
				return null;
			}

			ois = new ObjectInputStream(fis);
			categoriasJaEscritas = (HashMap<Usuario, ArrayList<Categoria>>) ois
					.readObject();
			ois.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoriasJaEscritas;
	}
}
