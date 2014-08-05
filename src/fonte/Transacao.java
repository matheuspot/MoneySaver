package fonte;

public class Transacao {

	private String tipoDeTransacao;
	private String descricao;
	private String dataDeInsercao;
	private double valor;
	private Categoria categoria;
	private String recorrencia;
<<<<<<< HEAD
	private Conta conta;
=======
	private Usuario usuario;
>>>>>>> origin/master

	public Transacao(Usuario usuario) {
		this.usuario = usuario;
	}

<<<<<<< HEAD
	public void adicionaTransacao(String tipoDeTransacao) {
		if(tipoDeTransacao=="Despesa") {
			try{
				conta.retiraDinheiro(valor);
			} catch(Exception e) {
				e.getMessage();
			}
		}
		
		// Aqui será colocado a data de inserção
		
		// Aqui será colocado a categoria
		
		// Aqui será adicionada a descrição

=======
	public void adicionaTransacao(String tipoDeTransacao, String descricao,
			String dataDeInsercao, double valor, Categoria categoria,
			String recorrencia) {
		this.tipoDeTransacao = tipoDeTransacao;
		this.descricao = descricao;
		this.dataDeInsercao = dataDeInsercao;
		this.valor = valor;
		this.categoria = categoria;
		this.recorrencia = recorrencia;
>>>>>>> origin/master
	}

	public void editarTransacao() {

	}

	public void deletarTransacao() {

	}
}
