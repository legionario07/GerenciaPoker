package br.com.gerenciapoker.estatisticas;

import java.math.BigDecimal;

public class EstatisticaJogadorSaldo extends Estatistica {
	private BigDecimal entrada;
	private BigDecimal premiacao;
	private BigDecimal saldo;

	public EstatisticaJogadorSaldo() {

	}

	public BigDecimal getEntrada() {
		return entrada;
	}

	public void setEntrada(BigDecimal entrada) {
		this.entrada = entrada;
	}

	public BigDecimal getPremiacao() {
		return premiacao;
	}

	public void setPremiacao(BigDecimal premiacao) {
		this.premiacao = premiacao;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

}
