package fonte;

import java.io.Serializable;

/**
 * Classe que representa uma transacao
 */
public class Transacao implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tipoDeTransacao;
	private String descricao;
	private String dataDeInsercao;
	private double valor;
	private Categoria categoria;
	private String recorrencia;
	
	/**
	 * Construtor da classe
	 * 
	 * @param tipoDeTransacao
	 * 		Tipo da transacao (Despesa ou Provento)
	 * @param descricao
	 * 		Breve descricao sobre oq é a despesa
	 * @param dataDeInsercao
	 * 		Data em que a transacao foi feita
	 * @param valor
	 * 		Valor da transacao
	 * @param categoria
	 * 		Categoria em que a transacao se enquadra
	 * @param recorrencia
	 * 		Frenquencia em que ocorre a transacao
	 * @throws Exception
	 * 		Caso um parametro invalido seja passado,
	 * 		uma excecao sera lancada
	 */
	public Transacao(String tipoDeTransacao, String descricao,
			String dataDeInsercao, double valor, Categoria categoria,
			String recorrencia) throws Exception {
		if (tipoDeTransacao == null || tipoDeTransacao.equals(""))
			throw new Exception("Tipo de transacao vazio!");
		if (valor <= 0) 
			throw new Exception("Valor da transacao nao pode ser menor ou igual zero!");
		if (categoria == null)
			throw new Exception("Indique a categoria da transacao!");
		
		this.tipoDeTransacao = tipoDeTransacao;
		this.descricao = descricao;
		this.dataDeInsercao = dataDeInsercao;
		this.valor = valor;
		this.categoria = categoria;
		this.recorrencia = recorrencia;
	}
	
	/**
	 * Metodo de acesso ao tipo de transacao
	 * 
	 * @return
	 * 		Tipo da transacao
	 */
	public String getTipoDeTransacao() {
		return tipoDeTransacao;
	}
	
	/**
	 * Metodo de acesso a descricao da transacao
	 * 
	 * @return
	 * 		Descricao da transacao
	 */
	public String getDescricao() {
		return descricao;
	}
	
	/**
	 * Metodo de acesso a data de insercao da transacao
	 * 
	 * @return
	 * 		Data da transacao
	 */
	public String getDataDeInsercao() {
		return dataDeInsercao;
	}
	
	/**
	 * Metodo de acesso ao valor da transacao
	 * 
	 * @return
	 * 		Valor da transacao
	 */
	public double getValor() {
		return valor;
	}
	
	/**
	 * Metodo de acesso a categoria da transacao
	 * 
	 * @return
	 * 		Categoria da transacao
	 */
	public Categoria getCategoria() {
		return categoria;
	}
	
	/**
	 * Metodo de acesso a Recorrencia da Transacao
	 * 
	 * @return
	 * 		Recorrencia da Transacao
	 */
	public String getRecorrencia() {
		return recorrencia;
	}
	
	/**
	 * Reescreve o metodo toString da classe String,
	 * Retorna uma string com as informacoes e caracteristicas
	 * da transacao.
	 */
	@Override
	public String toString() {
		return "Tipo de transação: " + tipoDeTransacao + "\nDescrição: "
				+ descricao + "\nData de Inserção: " + dataDeInsercao
				+ "\nValor: " + valor + "\nCategoria: " + categoria.getNome()
				+ "\nRecorrência: " + recorrencia;
	}
	
	/**
	 * Hashcode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result
				+ ((dataDeInsercao == null) ? 0 : dataDeInsercao.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result
				+ ((recorrencia == null) ? 0 : recorrencia.hashCode());
		result = prime * result
				+ ((tipoDeTransacao == null) ? 0 : tipoDeTransacao.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	/**
	 * Reescreve o metodo equals da classe object,
	 * Compara as transacoes para verem se sao iguais,
	 * se forem iguais, retorna true, caso contrario,
	 * retorna false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transacao other = (Transacao) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (dataDeInsercao == null) {
			if (other.dataDeInsercao != null)
				return false;
		} else if (!dataDeInsercao.equals(other.dataDeInsercao))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (recorrencia == null) {
			if (other.recorrencia != null)
				return false;
		} else if (!recorrencia.equals(other.recorrencia))
			return false;
		if (tipoDeTransacao == null) {
			if (other.tipoDeTransacao != null)
				return false;
		} else if (!tipoDeTransacao.equals(other.tipoDeTransacao))
			return false;
		if (Double.doubleToLongBits(valor) != Double
				.doubleToLongBits(other.valor))
			return false;
		return true;
	}
}
