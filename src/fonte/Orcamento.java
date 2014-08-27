package fonte;

public class Orcamento {
	
	private Categoria categoria;
	private double valorOrcamento;
	
	public Orcamento(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public void setOrcamento(double valorOrcamento) throws Exception {
		if (valorOrcamento <= 0)
			throw new Exception("Valor limite tem que ser positivo!");
		
		categoria.valorLimite = valorOrcamento;
		valorOrcamento = this.valorOrcamento;
	}
	
	public double getValorOrcamento() {
		return valorOrcamento;
	}
	
	public String getMensagemOrcamento() {
		return "Valor excedido para essa categoria.";
	}

}
