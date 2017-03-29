package br.com.gerenciapoker.dao;

import java.util.List;

import br.com.gerenciapoker.dominio.EntidadeDominio;

public interface IDao {

	public Integer save(EntidadeDominio entidade);
	public void edite(EntidadeDominio entidade);
	public void delete(EntidadeDominio entidade);
	public EntidadeDominio find(EntidadeDominio entidade);
	public List<EntidadeDominio> findAll();
	
}
