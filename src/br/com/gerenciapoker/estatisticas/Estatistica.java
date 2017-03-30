package br.com.gerenciapoker.estatisticas;

import br.com.gerenciapoker.dominio.EntidadeDominio;
import br.com.gerenciapoker.dominio.Jogador;

public class Estatistica extends EntidadeDominio{
	
	private Jogador jogador;
	
	public Estatistica(){
		jogador = new Jogador();
	}

	public Jogador getJogador() {
		return jogador;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

}
