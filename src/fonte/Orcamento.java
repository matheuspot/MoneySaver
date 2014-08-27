package fonte;

public class Orcamento {
	
	private double valorOrcamento;
	
	public void setOrcamento(double valorOrcamento) throws Exception {
		if (valorOrcamento <= 0)
			throw new Exception("Valor limite tem que ser positivo!");
		
		valorOrcamento = this.valorOrcamento;
	}
	
	public double getValorOrcamento() {
		return valorOrcamento;
	}

}
