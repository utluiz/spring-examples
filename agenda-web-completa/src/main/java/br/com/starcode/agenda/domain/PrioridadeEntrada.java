package br.com.starcode.agenda.domain;

public enum PrioridadeEntrada {

	Passatempo("P"), NadaDeMais("N"), PrecisaAtencao("A"), Importantissimo("I");
	
	private String code;
	
	private PrioridadeEntrada(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	public static PrioridadeEntrada fromCode(String code) {
		for (PrioridadeEntrada item : PrioridadeEntrada.values()) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}
	
}
