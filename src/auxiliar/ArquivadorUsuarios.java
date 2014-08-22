package auxiliar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import fonte.Usuario;

/**
 * Classe usada para escrever uma lista de objetos da classe Usuario em um
 * arquivo.
 */
public class ArquivadorUsuarios {
	private String nomeDoArquivo;
	private FileOutputStream fos;
	private ObjectOutputStream oos;
	private FileInputStream fis;
	private ObjectInputStream ois;
	private List<Usuario> usuariosJaEscritos;

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
	public ArquivadorUsuarios(String nomeDoArquivo) throws Exception {
		if (nomeDoArquivo == null || nomeDoArquivo.trim().length() == 0) {
			throw new Exception("Nome do arquivo inválido.");
		}

		this.nomeDoArquivo = nomeDoArquivo;
	}

	/**
	 * Método que recebe um List de usuários para escrever no arquivo.
	 * 
	 * @param listaDeUsuarios
	 *            Um List de usuários para escrever no arquivo.
	 * @throws Exception
	 *             Lança exceção se o List estiver vazio.
	 */
	public void escreveUsuarios(List<Usuario> listaDeUsuarios) throws Exception {
		if (listaDeUsuarios.isEmpty()) {
			throw new Exception("Lista de usuários para escrever está vazia.");
		}

		try {
			fos = new FileOutputStream(nomeDoArquivo);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(listaDeUsuarios);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null)
					oos.close();
				if (fos != null)
					fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Método que irá ler os usuários que estão escritos no arquivo. Se o
	 * arquivo não existir, irá retornar null.
	 * 
	 * @return Um List com objetos da classe Usuario. Caso o arquivo não exista,
	 *         irá retornar null.
	 */
	@SuppressWarnings("unchecked")
	public List<Usuario> leUsuarios() {
		try {
			try {
				fis = new FileInputStream(nomeDoArquivo);
			} catch (Exception e) {
				return null;
			}

			ois = new ObjectInputStream(fis);
			usuariosJaEscritos = (List<Usuario>) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (ois != null)
					ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return usuariosJaEscritos;
	}
}
