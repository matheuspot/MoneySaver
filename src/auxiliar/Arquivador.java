package auxiliar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import fonte.Usuario;

/**
 * Classe para escrever uma lista de objetos da classe Usuario para um arquivo.
 */

public class Arquivador {
	String nomeDoArquivo;
	FileOutputStream fos;
	ObjectOutputStream oos;
	FileInputStream fis;
	ObjectInputStream ois;
	ArrayList<Usuario> usuariosJaEscritos;

	/**
	 * Construtor do arquivador, que tem como parâmetro o nome desejado para o
	 * arquivo no qual será armazenado os usuários.
	 * 
	 * @param nomeDoArquivo
	 *            Nome desejado para o arquivo no qual será armazenado os
	 *            usuários.
	 * @throws Exception
	 *             Lança exceção caso o nome do arquivo seja inválido.
	 */

	public Arquivador(String nomeDoArquivo) throws Exception {
		if (nomeDoArquivo == null || nomeDoArquivo.trim().length() == 0) {
			throw new Exception("Nome do arquivo inválido.");
		}

		this.nomeDoArquivo = nomeDoArquivo;
	}

	/**
	 * Método que recebe um ArrayList de usuários para escrever no arquivo.
	 * 
	 * @param listaDeUsuarios
	 *            Um ArrayList de usuários para escrever no arquivo.
	 * @throws Exception
	 *             Lança exceção se o ArrayList estiver vazio.
	 */

	public void escreveUsuarios(ArrayList<Usuario> listaDeUsuarios)
			throws Exception {
		if (listaDeUsuarios.isEmpty()) {
			throw new Exception("Lista de usuários para escrever está vazia.");
		}

		try {
			fos = new FileOutputStream(nomeDoArquivo);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(listaDeUsuarios);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que irá ler os usuários que estão escritos no arquivo. Se o
	 * arquivo não existir, irá retornar null.
	 * 
	 * @return Um ArrayList com objetos da classe Usuario. Caso o arquivo não
	 *         exista, irá retornar null.
	 */

	@SuppressWarnings("unchecked")
	public ArrayList<Usuario> leUsuarios() {
		try {
			try {
				fis = new FileInputStream(nomeDoArquivo);
			} catch (Exception e) {
				return null;
			}

			ois = new ObjectInputStream(fis);
			usuariosJaEscritos = (ArrayList<Usuario>) ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuariosJaEscritos;
	}
}
