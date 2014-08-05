package fonte;

public class Transacao {

	private String tipoDeTransacao;
	private String descricao;
	private String dataDeInsercao;
	private double valor;
	private Categoria categoria;
	private String recorrencia;
	private Usuario usuario;

	public Transacao(Usuario usuario) {
		this.usuario = usuario;
	}

	public void adicionaTransacao(String tipoDeTransacao, String descricao,
			String dataDeInsercao, double valor, Categoria categoria,
			String recorrencia) {
		this.tipoDeTransacao = tipoDeTransacao;
		this.descricao = descricao;
		this.dataDeInsercao = dataDeInsercao;
		this.valor = valor;
		this.categoria = categoria;
		this.recorrencia = recorrencia;
	}

	public void editarTransacao() {

	}

	public void deletarTransacao() {

	}
}
