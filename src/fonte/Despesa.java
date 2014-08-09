package fonte;

public class Despesa extends Transacao {

	private static final long serialVersionUID = 1L;

	public Despesa(String descricao, String dataDeInsercao, double valor,
			Categoria categoria, String recorrencia) throws Exception {
		super(descricao, dataDeInsercao, valor, categoria, recorrencia);
	}

	@Override
	public double getValor() {
		return -super.getValor();
	}
}
