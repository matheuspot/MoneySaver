package fonte;

public class Provento extends Transacao {

	private static final long serialVersionUID = 1L;

	public Provento(String descricao, String dataDeInsercao, double valor,
			Categoria categoria, String recorrencia) throws Exception {
		super(descricao, dataDeInsercao, valor, categoria, recorrencia);
	}
}
