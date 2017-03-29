package br.com.gerenciapoker.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.gerenciapoker.dao.ColocacaoDao;
import br.com.gerenciapoker.dominio.Colocacao;
import br.com.gerenciapoker.dominio.EntidadeDominio;


@ManagedBean(name="MBColocacao")
@ViewScoped
public class ColocacaoBean implements IBean {

	private List<EntidadeDominio> Colocacacoes;
	private ColocacaoDao ColocacaoDao;
	private Colocacao colocacao;
	
	public ColocacaoBean(){
		Colocacacoes = new ArrayList<EntidadeDominio>();
		ColocacaoDao = new ColocacaoDao();
		colocacao = new Colocacao();
		findAll();
	}
	

	public ColocacaoDao getColocacaoDao() {
		return ColocacaoDao;
	}

	public void setColocacaoDao(ColocacaoDao ColocacaoDao) {
		this.ColocacaoDao = ColocacaoDao;
	}

	public List<EntidadeDominio> getColocacacoes() {
		return Colocacacoes;
	}


	public void setColocacacoes(List<EntidadeDominio> colocacacoes) {
		Colocacacoes = colocacacoes;
	}


	public Colocacao getColocacao() {
		return colocacao;
	}

	public void setColocacao(Colocacao colocacao) {
		this.colocacao = colocacao;
	}

	public void findAll(){
		Colocacacoes = ColocacaoDao.findAll();
	}
	
	public void save(){
		colocacao.setId(ColocacaoDao.save(colocacao));
		
		if(colocacao.getId()==null){
			System.out.println("Houve erro a salvar");
		} else {
			findAll();
			clear();
		}
	}
	
	public void delete(){
		ColocacaoDao.delete(colocacao);
		findAll();
	}
	
	public void edite(){
		ColocacaoDao.edite(colocacao);
		findAll();
		clear();
	}
	
	
	public void clear(){
		colocacao = new Colocacao();
	}
}
