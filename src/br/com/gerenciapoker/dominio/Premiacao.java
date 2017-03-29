package br.com.gerenciapoker.dominio;

import java.math.BigDecimal;

public class Premiacao extends EntidadeDominio {

	private BigDecimal valor;
	private Colocacao colocacao;

	public Premiacao(Integer id, BigDecimal valor) {
		this(id);
		this.valor = valor;
		
	}

	public Premiacao(BigDecimal valor) {
		this.valor = valor;
	}

	public Premiacao(Integer id) {
		this.id = id;
	}

	public Premiacao() {
		colocacao = new Colocacao();
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Colocacao getColocacao() {
		return colocacao;
	}

	public void setColocacao(Colocacao colocacao) {
		this.colocacao = colocacao;
	}

}
