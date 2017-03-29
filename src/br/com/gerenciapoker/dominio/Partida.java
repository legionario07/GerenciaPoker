package br.com.gerenciapoker.dominio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Partida extends EntidadeDominio {

	private Date data;
	private Integer participacao;
	private List<Jogador> jogadores;
	private BigDecimal premiacaoRanking;
	private Local local;
	
	public Partida(Integer id, Date data, Local local, Integer participacao){
		this(data,local, participacao);
		this.id = id;
	}
	
	public Partida(Date data, Local local, Integer participacao){
		this.data = data;
		this.local = local;
		this.participacao = participacao;
	}
	public Partida(Integer id){
		this.id = id;
	}
	public Partida(){
		jogadores = new ArrayList<Jogador>();
		local = new Local();
	}
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Integer getParticipacao() {
		return participacao;
	}
	public void setParticipacao(Integer participacao) {
		this.participacao = participacao;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public List<Jogador> getJogadores() {
		return jogadores;
	}

	public void setJogadores(List<Jogador> jogadores) {
		this.jogadores = jogadores;
	}

	public BigDecimal getPremiacaoRanking() {
		return premiacaoRanking;
	}

	public void setPremiacaoRanking(BigDecimal premiacaoRanking) {
		this.premiacaoRanking = premiacaoRanking;
	}
	
	
}
