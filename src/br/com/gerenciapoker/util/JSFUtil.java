package br.com.gerenciapoker.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class JSFUtil {
	/**
	 * 
	 * @param mensagem Recebe uma String e com a mensagem que ser�
	 * exibida na tela se a a��o ocorrer sem erro
	 */
	public static void adicionarMensagemSucesso(String mensagem) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem);
		FacesContext contexto = FacesContext.getCurrentInstance();
		contexto.addMessage(null, msg);
	}
	
	/**
	 * 
	 * @param mensagem Recebe uma String e com a mensagem que ser�
	 * exibida na tela se a a��o ocorrer erro
	 */
	public static void adicionarMensagemErro(String mensagem) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagem);
		FacesContext contexto = FacesContext.getCurrentInstance();
		contexto.addMessage(null, msg);
	}
	/**
	 * 
	 * @param mensagem Recebe uma String e com a mensagem que ser�
	 * exibida na tela se o objeto procurado n�o foi encontrado
	 */
	public static void adicionarMensagemNaoLocalizado(String mensagem) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagem);
		FacesContext contexto = FacesContext.getCurrentInstance();
		contexto.addMessage(null, msg);
	}
	


}