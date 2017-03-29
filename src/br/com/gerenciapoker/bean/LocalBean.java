package br.com.gerenciapoker.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.gerenciapoker.dao.LocalDao;
import br.com.gerenciapoker.dominio.EntidadeDominio;
import br.com.gerenciapoker.dominio.Local;

@ManagedBean(name = "MBLocal")
@ViewScoped
public class LocalBean implements IBean {

	private Local local;
	private LocalDao localDao;
	private List<EntidadeDominio> locais;
	
	public LocalBean(){
		local = new Local();
		localDao = new LocalDao();
		locais = new ArrayList<EntidadeDominio>();
		
		//preenchendo a lista de todos os locais
		findAll();
	}
	
	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public LocalDao getLocalDao() {
		return localDao;
	}

	public void setLocalDao(LocalDao localDao) {
		this.localDao = localDao;
	}

	public List<EntidadeDominio> getLocais() {
		return locais;
	}

	public void setLocais(List<EntidadeDominio> locais) {
		this.locais = locais;
	}

	@Override
	public void save() {
		
		if(valide())
			local.setId(localDao.save(local));
		else{
			System.out.println("Não foi possivel executar a operação");
			return;
		}
		
		if(local.getId()==null){
			System.out.println("Não foi possivel executar a operação");
		} else{
			findAll();
			clear();
		}
		
	}

	@Override
	public void delete() {

		localDao.delete(local);
		clear();
	}

	@Override
	public void edite() {
		if(valide())
			localDao.edite(local);
		else{
			System.out.println("Não foi possivel executar a operação");
		}
		
		findAll();
		clear();
	}

	@Override
	public void findAll() {
		locais = localDao.findAll();
	}

	@Override
	public void clear() {
		local = new Local();
	}

	private boolean valide(){
		
		if(local.getLocal().length()==0)
			return false;
		
		return true;
	}
}
