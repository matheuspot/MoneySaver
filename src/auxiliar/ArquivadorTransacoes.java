package auxiliar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import fonte.Transacao;
import fonte.Usuario;

/**
 * Classe usada para escrever uma coleção de objetos da classe Transacao em um
 * arquivo.
 */

public class ArquivadorTransacoes {
	private String nomeDoArquivo;
	private FileOutputStream fos;
	private ObjectOutputStream oos;
	private FileInputStream fis;
	private ObjectInputStream ois;
	private HashMap<Usuario, ArrayList<Transacao>> transacoesJaEscritas;

	/**
	 * Construtor do arquivador, que tem como parâmetro o nome desejado para o
	 * arquivo no qual será armazenado as transações.
	 * 
	 * @param nomeDoArquivo
	 *            Nome desejado para o arquivo no qual será armazenado as
	 *            transações.
	 * @throws Exception
	 *             Lança exceção caso o nome do arquivo seja inválido.
	 */

	public ArquivadorTransacoes(String nomeDoArquivo) throws Exception {
		if (nomeDoArquivo == null || nomeDoArquivo.trim().length() == 0) {
			throw new Exception("Nome do arquivo inválido.");
		}

		this.nomeDoArquivo = nomeDoArquivo;
	}

	/**
	 * Método que recebe um HashMap(Key: Usuario, Value: ArrayList de Transacao)
	 * para escrever no arquivo.
	 * 
	 * @param mapaDeTransacoes
	 *            Um HashMap(Key: Usuario, Value: ArrayList de Transacao) para
	 *            escrever no arquivo.
	 * @throws Exception
	 *             Lança exceção se o HashMap estiver vazio.
	 */

	public void escreveTransacoes(
			HashMap<Usuario, ArrayList<Transacao>> mapaDeTransacoes)
			throws Exception {
		if (mapaDeTransacoes.isEmpty()) {
			throw new Exception(
					"Coleção de transações para escrever está vazia.");
		}

		try {
			fos = new FileOutputStream(nomeDoArquivo);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(mapaDeTransacoes);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que irá ler as transações de seus respectivos usuários que estão
	 * escritos no arquivo. Se o arquivo não existir, irá retornar null.
	 * 
	 * @return Um HashMap(Key: Usuario, Value: ArrayList de Transacao). Caso o
	 *         arquivo não exista, irá retornar null.
	 */

	@SuppressWarnings("unchecked")
	public HashMap<Usuario, ArrayList<Transacao>> leTransacoes() {
		try {
			try {
				fis = new FileInputStream(nomeDoArquivo);
			} catch (Exception e) {
				return null;
			}

			ois = new ObjectInputStream(fis);
			transacoesJaEscritas = (HashMap<Usuario, ArrayList<Transacao>>) ois
					.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transacoesJaEscritas;
	}
}
