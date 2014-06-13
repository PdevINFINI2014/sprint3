package bean;

import java.io.Serializable;

import java.util.HashMap;


import java.util.Map;


import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import Pidev.Service.BanqueCommercial.BanqueCommercialServiceLocal;
import Pidev.Service.Corporate.CorporateServiceLocal;
import Pidev.Service.Currency.CurrencyServiceLocal;
import Pidev.entite.AskBid;
import Pidev.entite.BanqueCommercial;
import Pidev.entite.Corporate;
import Pidev.entite.Currency;

import Pidev.entite.ScoreboardPrices;

@SuppressWarnings("serial")
@ManagedBean
@RequestScoped
public class RegisterBean implements Serializable {
	private String name;
	private String phone;
	private String address;
	private String login;
	private String password;
	private String confirmedpassword;
	private String mail;
	private String clientType;
	private SelectOneRadioView selectOneRadioView;
	private boolean validateRegister = false;
	private boolean noValidRegister = false;
	private boolean noPassMatch = false;
	private boolean validateRegisterbank = false;

	@EJB
	BanqueCommercialServiceLocal banqueCommercialServiceLocal;

	@EJB
	CorporateServiceLocal corporateServiceLocal;
    
	@EJB
	CurrencyServiceLocal currencySL;
	
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isValidateRegisterbank() {
		return validateRegisterbank;
	}

	public void setValidateRegisterbank(boolean validateRegisterbank) {
		this.validateRegisterbank = validateRegisterbank;
	}

	public boolean isValidateRegister() {
		return validateRegister;
	}

	public void setValidateRegister(boolean validateRegister) {
		this.validateRegister = validateRegister;
	}

	public boolean isNoValidRegister() {
		return noValidRegister;
	}

	public void setNoValidRegister(boolean noValidRegister) {
		this.noValidRegister = noValidRegister;
	}

	public boolean isNoPassMatch() {
		return noPassMatch;
	}

	public void setNoPassMatch(boolean noPassMatch) {
		this.noPassMatch = noPassMatch;
	}

	public RegisterBean() {

	}

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

	public String getConfirmedpassword() {
		return confirmedpassword;
	}

	public void setConfirmedpassword(String confirmedpassword) {
		this.confirmedpassword = confirmedpassword;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public SelectOneRadioView getSelectOneRadioView() {
		if (selectOneRadioView == null)
			selectOneRadioView = new SelectOneRadioView();
		return selectOneRadioView;
	}

	public void setSelectOneRadioView(SelectOneRadioView selectOneRadioView) {
		this.selectOneRadioView = selectOneRadioView;
	}

	public void onSubmit() {

		if (selectOneRadioView.getclientType().equals("Corporate")) {
			if (password.equals(confirmedpassword)) {
				Corporate corporate = new Corporate();
				corporate.setLogin(login);
				corporate.setPass(password);
				corporate.setMail(mail);
				corporate.setAddress(address);
				corporate.setLibelle(name);
				corporate.setTel(phone);
				corporate.setCapitale(100001.0);
				try {
					corporateServiceLocal.add(corporate);
					validateRegister = true;

				} catch (Exception e) {

					noValidRegister = true;
				}
			} else
				noPassMatch = true;
		}

		else if (selectOneRadioView.getclientType().equals("Commercial Bank")) {

			if (password.equals(confirmedpassword)) {
				BanqueCommercial commercialBanque = new BanqueCommercial();
				commercialBanque.setLogin(login);
				commercialBanque.setPass(password);
				commercialBanque.setMail(mail);
				commercialBanque.setTel(phone);
				commercialBanque.setAddress(address);
				commercialBanque.setLibelle(name);
				commercialBanque.setFondPropre(1000001.0);
				
				ScoreboardPrices scoreboard = new ScoreboardPrices();
				Map<Currency,AskBid> price = new HashMap<Currency,AskBid>();
				for(Currency cur : currencySL.findall())
				{ if(cur.isIs_available())
					price.put(cur, new AskBid());
				}
				scoreboard.setPrice(price);
				commercialBanque.setScoreboard(scoreboard);
				try {
					banqueCommercialServiceLocal.add(commercialBanque);
					validateRegisterbank = true;

				} catch (Exception e) {

					noValidRegister = true;
				}

			} else
				noPassMatch = true;
		}

	}

	public String doCancel() {

		login = " ";
		password = " ";
		mail = " ";
		address = "";
		phone = "";
		name = "";
		confirmedpassword = " ";
		selectOneRadioView.setclientType(" ");

		return "login.xhtml";

	}

}
