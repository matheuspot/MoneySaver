package fonte;

public class Transacao {

	private String tipoDeTransacao;
	private String descricao;
	private String dataDeInsercao;
	private double valor;
	private Categoria categoria;
	private String recorrencia;
	private Conta conta;

	public Transacao() {

	}

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

	}

	public void editarTransacao() {

	}

	public void deletarTransacao() {

	}
}
