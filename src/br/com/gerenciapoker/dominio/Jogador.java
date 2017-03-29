package br.com.gerenciapoker.dominio;

public class Jogador extends EntidadeDominio {

	private String nome;
	private Premiacao premiacao;
	private Despesa despesa;

	public Jogador(Integer id, String nome){
		this(id);
		this.nome = nome;
	}
	
	public Jogador(String nome){
		this.nome = nome;
	}
	
	public Jogador(Integer id){
		this.id = id;
	}
	public Jogador(){
		premiacao = new Premiacao();
		despesa = new Despesa();
		
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Premiacao getPremiacao() {
		return premiacao;
	}

	public void setPremiacao(Premiacao premiacao) {
		this.premiacao = premiacao;
	}

	public Despesa getDespesa() {
		return despesa;
	}

	public void setDespesa(Despesa despesa) {
		this.despesa = despesa;
	}
	
	
	
}
