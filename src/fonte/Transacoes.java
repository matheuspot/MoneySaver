package fonte;

import java.util.GregorianCalendar;

public class Transacoes {
	
	GregorianCalendar calendario = new GregorianCalendar();  
	int mesNum = calendario.get(GregorianCalendar.MONTH);
	
	private String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
					  "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
	
	public boolean adicionaDespesa(String categoria, Despesa despesa) {
		
		return true;
	}
	
	public String[] getMeses() {
		return meses;		
	}

}
