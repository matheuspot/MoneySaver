package fonte;

import java.io.Serializable;

public class Transacao implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tipoDeTransacao;
	private String descricao;
	private String dataDeInsercao;
	private double valor;
	private Categoria categoria;
	private String recorrencia;

	public Transacao(String tipoDeTransacao, String descricao,
			String dataDeInsercao, double valor, Categoria categoria,
			String recorrencia) {
		this.tipoDeTransacao = tipoDeTransacao;
		this.descricao = descricao;
		this.dataDeInsercao = dataDeInsercao;
		this.valor = valor;
		this.categoria = categoria;
		this.recorrencia = recorrencia;
	}

	@Override
	public String toString() {
		return "Tipo de transação: " + tipoDeTransacao + "\nDescrição: "
				+ descricao + "\nData de Inserção: " + dataDeInsercao
				+ "\nValor: " + valor + "\nCategoria: " + categoria
				+ "\nRecorrência: " + recorrencia;
	}

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
