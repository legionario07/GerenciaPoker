package br.com.gerenciapoker.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.gerenciapoker.dao.EstatisticasDao;
import br.com.gerenciapoker.dao.JogadorDao;
import br.com.gerenciapoker.dominio.EntidadeDominio;
import br.com.gerenciapoker.dominio.Jogador;
import br.com.gerenciapoker.estatisticas.EstatisticaJogadorPontuacao;
import br.com.gerenciapoker.estatisticas.EstatisticaJogadorSaldo;

@ManagedBean(name = "MBEstatisticasBean")
@ViewScoped
public class EstatisticasBean {

	private EstatisticaJogadorSaldo ej;
	private List<EntidadeDominio> ejs;
	private EstatisticaJogadorPontuacao ep;
	private List<EntidadeDominio> eps;
	
	public EstatisticasBean(){
		ej= new EstatisticaJogadorSaldo();
		ejs = new ArrayList<EntidadeDominio>();
		
		ejs = consultarSaldo();
		
		ep = new EstatisticaJogadorPontuacao();
		eps = new ArrayList<EntidadeDominio>();
		
		eps = consultarPontuacao();
		
	}

	public EstatisticaJogadorSaldo getEj() {
		return ej;
	}

	public void setEj(EstatisticaJogadorSaldo ej) {
		this.ej = ej;
	}
	
	public List<EntidadeDominio> getEjs() {
		return ejs;
	}

	public void setEjs(List<EntidadeDominio> ejs) {
		this.ejs = ejs;
	}

	public EstatisticaJogadorPontuacao getEp() {
		return ep;
	}

	public void setEp(EstatisticaJogadorPontuacao ep) {
		this.ep = ep;
	}

	public List<EntidadeDominio> getEps() {
		return eps;
	}

	public void setEps(List<EntidadeDominio> eps) {
		this.eps = eps;
	}

	private List<EntidadeDominio> consultarSaldo(){
		return new EstatisticasDao().pesquisarPremiacao();
	}
	
	private List<EntidadeDominio> consultarPontuacao(){
		
		List<EntidadeDominio> jogadores = new ArrayList<EntidadeDominio>();
		jogadores = new JogadorDao().findAll();
		
		List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();
		
		for(EntidadeDominio e : jogadores){
			if(e instanceof Jogador){
				ep = (EstatisticaJogadorPontuacao) new EstatisticasDao().pesquisarPontuacao(e);
				
				ep.setJogos(new EstatisticasDao().pesquisarPontosDeParticipacao(e));
				ep.setParticipacao(ep.getJogos()*10);
				ep.setTotal(ep.getPontosConquistados()+ep.getParticipacao());
				
				ep.setPosicaoVsQtde(new EstatisticasDao().pesquisarPosicaoVsQuantidade(e));
				
				lista.add(ep);
			}
		}
		
		return lista;
	}
	
}
