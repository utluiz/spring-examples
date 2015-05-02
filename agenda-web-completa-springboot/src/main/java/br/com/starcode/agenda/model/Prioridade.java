package br.com.starcode.agenda.model;

public enum Prioridade {

	Passatempo("P"), NadaDeMais("N"), PrecisaAtencao("A"), Importantissimo("I");
	
	private String code;
	
	private Prioridade(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	public static Prioridade fromCode(String code) {
		for (Prioridade item : Prioridade.values()) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}
	
}
