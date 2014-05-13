package Authentification;

import java.io.Serializable;





import javax.ejb.EJB;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import Pidev.Service.Admin.AdminServiceLocal;
import Pidev.Service.Client.ClientServiceLocal;
import Pidev.Service.Currency.CurrencyServiceLocal;
import Pidev.Service.Trader.TraderServiceLocal;

@SuppressWarnings("serial")
@ManagedBean(name = "authBean")
@SessionScoped
public class Authentification implements Serializable {
	private String login;
	private String password;
	@EJB
	public ClientServiceLocal clientSL;
	@EJB
	public TraderServiceLocal traderSL;
	@EJB
	public AdminServiceLocal adminSL;
	@EJB
	public CurrencyServiceLocal curSL;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Authentification() {

	}

	public String doLogin() {
		if (clientSL.findByLogin(login) != null)
			if (clientSL.findByLogin(login).getPass().equals(password))
				return "index?faces-context=true";
		if (traderSL.findByLogin(login) != null)
			if (traderSL.findByLogin(login).getPassword().equals(password))
				return "index?faces-context=true";
		if (adminSL.findByID(1).getLogin().equals(login)
				&& adminSL.findByID(1).getPass().equals(password))
			return "index?faces-context=true";
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Login ou password incorect !!",
						"Login ou password incorect !!"));
		return null;


		
	}
}
