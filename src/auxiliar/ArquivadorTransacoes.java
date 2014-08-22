package auxiliar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
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
	private Map<Usuario, List<Transacao>> transacoesJaEscritas;

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
	 * Método que recebe um Map(Key: Usuario, Value: List de Transacao) para
	 * escrever no arquivo.
	 * 
	 * @param mapaDeTransacoes
	 *            Um Map(Key: Usuario, Value: List de Transacao) para escrever
	 *            no arquivo.
	 * @throws Exception
	 *             Lança exceção se o Map estiver vazio.
	 */
	public void escreveTransacoes(Map<Usuario, List<Transacao>> mapaDeTransacoes)
			throws Exception {
		if (mapaDeTransacoes.isEmpty()) {
			throw new Exception(
					"Coleção de transações para escrever está vazia.");
		}

		try {
			fos = new FileOutputStream(nomeDoArquivo);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(mapaDeTransacoes);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			oos.close();
			fos.close();
		}
	}

	/**
	 * Método que irá ler as transações de seus respectivos usuários que estão
	 * escritos no arquivo. Se o arquivo não existir, irá retornar null.
	 * 
	 * @return Um Map(Key: Usuario, Value: List de Transacao). Caso o arquivo
	 *         não exista, irá retornar null.
	 */
	@SuppressWarnings("unchecked")
	public Map<Usuario, List<Transacao>> leTransacoes() {
		try {
			try {
				fis = new FileInputStream(nomeDoArquivo);
			} catch (Exception e) {
				return null;
			}

			ois = new ObjectInputStream(fis);
			transacoesJaEscritas = (Map<Usuario, List<Transacao>>) ois
					.readObject();
			ois.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transacoesJaEscritas;
	}
}
