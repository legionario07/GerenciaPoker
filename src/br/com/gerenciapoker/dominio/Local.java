package br.com.gerenciapoker.dominio;

public class Local extends EntidadeDominio{

	private String local;
	
	public Local(String local){
		this.local = local;
	}
	
	public Local(Integer id){
		this.id = id;
	}
	
	public Local(){
		
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
	
	
}
