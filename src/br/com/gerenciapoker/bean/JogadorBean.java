package br.com.gerenciapoker.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.gerenciapoker.dao.JogadorDao;
import br.com.gerenciapoker.dominio.EntidadeDominio;
import br.com.gerenciapoker.dominio.Jogador;

@ManagedBean(name = "MBJogador")
@ViewScoped
public class JogadorBean implements IBean {

	private Jogador jogador;
	private JogadorDao jogadorDao;
	private List<EntidadeDominio> jogadores;
	
	public JogadorBean(){
		jogador = new Jogador();
		jogadorDao = new JogadorDao();
		jogadores = new ArrayList<EntidadeDominio>();
		
		//preenchendo a lista de todos os jogadores
		findAll();
	}
	
	public Jogador getJogador() {
		return jogador;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

	public JogadorDao getJogadorDao() {
		return jogadorDao;
	}

	public void setJogadorDao(JogadorDao jogadorDao) {
		this.jogadorDao = jogadorDao;
	}

	public List<EntidadeDominio> getJogadores() {
		return jogadores;
	}

	public void setJogadores(List<EntidadeDominio> jogadores) {
		this.jogadores = jogadores;
	}

	@Override
	public void save() {
		
		if(valide())
			jogador.setId(jogadorDao.save(jogador));
		else{
			System.out.println("Não foi possivel executar a operação");
			return;
		}
		
		if(jogador.getId()==null){
			System.out.println("Não foi possivel executar a operação");
		} else{
			findAll();
			clear();
		}
		
	}

	@Override
	public void delete() {

		jogadorDao.delete(jogador);
		clear();
	}

	@Override
	public void edite() {
		if(valide())
			jogadorDao.edite(jogador);
		else{
			System.out.println("Não foi possivel executar a operação");
		}
		
		findAll();
		clear();
	}

	@Override
	public void findAll() {
		jogadores = jogadorDao.findAll();
	}

	@Override
	public void clear() {
		jogador = new Jogador();
	}

	private boolean valide(){
		
		if(jogador.getNome().length()==0)
			return false;
		
		return true;
	}
}
