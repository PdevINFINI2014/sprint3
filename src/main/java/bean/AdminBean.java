package bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.ViewScoped;

import Pidev.entite.BanqueCentrale;
import Pidev.entite.Currency;
import Pidev.Service.BanqueCentrale.BanqueCentraleServiceLocal;
import Pidev.Service.Currency.CurrencyServiceLocal;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class AdminBean implements Serializable {

	@EJB
	CurrencyServiceLocal currencyServiceLocal;
	@EJB
	BanqueCentraleServiceLocal banqueCentraleServiceLocal;
	
	

	private List<Currency> currencies ;
	
	private Currency currency = new Currency();
	private BanqueCentrale banquecentrale=new BanqueCentrale();
	private String login;
	private String pass;
	private String passconfirmed;
	private String mail;
	private Currency currencySelected = new Currency();
	private List<String> currenciesId;
	public List<String> getCurrenciesId() {
		return currenciesId;
	}



	public void setCurrenciesId(List<String> currenciesId) {
		this.currenciesId = currenciesId;
	}


	private boolean passwordMatch = false;
	private boolean centralRegister=false;
	private boolean currencyRegister=false;
	
	@PostConstruct
	public void init(){
		
		currencies = currencyServiceLocal.findall();
		currenciesId=currencyServiceLocal.findallIds();
		
	
//		System.out.println(currencies.size());
//		
//for(int i=0;i<currencies.size();i++)
//{
//			System.out.println(currencies.get(i));
//            System.out.println(currenciesId.get(i));}
		// CurrencySelectOneListboxView = new CurrencySelectOneListboxView();
		
	}
	
	
	
    public boolean isCentralRegister() {
		return centralRegister;
	}



	public void setCentralRegister(boolean centralRegister) {
		this.centralRegister = centralRegister;
	}



	public boolean isCurrencyRegister() {
		return currencyRegister;
	}



	public void setCurrencyRegister(boolean currencyRegister) {
		this.currencyRegister = currencyRegister;
	}



	public Currency getCurrencySelected() {
		return currencySelected;
	}


	public void setCurrencySelected(Currency currencySelected) {
		this.currencySelected = currencySelected;
	}


	


	public boolean isPasswordMatch() {
		return passwordMatch;
	}



	public void setPasswordMatch(boolean passwordMatch) {
		this.passwordMatch = passwordMatch;
	}



	public String getPassconfirmed() {
		return passconfirmed;
	}


	public void setPassconfirmed(String passconfirmed) {
		this.passconfirmed = passconfirmed;
	}


	public BanqueCentrale getBanquecentrale() {
		return banquecentrale;
	}

	public void setBanquecentrale(BanqueCentrale banquecentrale) {
		this.banquecentrale = banquecentrale;
	}

	public List<Currency> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(List<Currency> currencies) {
		this.currencies = currencies;
	}
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public AdminBean() {
		
	
		

	}
	
	public String doAddCurrency(){
		String navigateTo=null;
		currency.setIs_available(false);
		currency.setIs_local_currency(false);
		
		currencyServiceLocal.add(currency);
		currencyRegister=true;
		return navigateTo;
	}

	
	public String doAddCentralBank(){
		
		String navigateTo=null;
		if(pass.equals(passconfirmed)){
		banquecentrale.setLogin(login);
		banquecentrale.setMail(mail);
		banquecentrale.setPass(pass);
		banqueCentraleServiceLocal.add(banquecentrale);
		centralRegister=true;
		}
		else passwordMatch=true;
		return navigateTo;
	}
	



	
}
