package bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import Pidev.Service.Client.ClientServiceLocal;
import Pidev.Service.Currency.CurrencyServiceLocal;
import Pidev.entite.BanqueCommercial;
import Pidev.entite.Client;
import Pidev.entite.Corporate;



@SuppressWarnings("serial")
@ViewScoped
@ManagedBean
public class ProfileBean implements Serializable {

	
private Client client ;

private String phone;
private String address;
private String email;
private String description;
HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
private String connectedUser = (String)session.getAttribute("connectedUser");
private boolean corporateRendred= false;
private boolean bankRendred=false;



public Client getClient() {
	return client;
}


public void setClient(Client client) {
	this.client = client;
}


public String getConnectedUser() {
	return connectedUser;
}


public void setConnectedUser(String connectedUser) {
	this.connectedUser = connectedUser;
}


@EJB
ClientServiceLocal clientServiceLocal;

@EJB
CurrencyServiceLocal currencyServiceLocal;

@PostConstruct
public void init(){
	
	client=clientServiceLocal.findByLogin(connectedUser);
	if(client instanceof Corporate) corporateRendred= true;
	if(client instanceof BanqueCommercial) bankRendred=true;
	this.phone = client.getTel();
	this.address = client.getAddress();
	this.email = client.getMail();
}


public String getPhone() {
	return phone;
}


public void setPhone(String phone) {
	this.phone = phone;
}


public String getAddress() {
	return address;
}


public void setAddress(String address) {
	this.address = address;
}


public String getEmail() {
	return email;
}


public void setEmail(String email) {
	this.email = email;
}


public String doAddContact(){
	 String navigateTo = null;
	 System.out.println("!!");
	System.out.println(client.getLogin());
	if(address !=null)
	 client.setAddress(address);
	if(phone!=null)
	 client.setTel(phone);
	if(email!=null)
	 client.setMail(email);
	 clientServiceLocal.update(client);
	return navigateTo;
}


public String doAddDescription(){
	
	String navigateTo = null;
	client.setDescreption(description);
	clientServiceLocal.update(client);
	
	return navigateTo;
	
}

public String getDescription() {
	return description;
}


public void setDescription(String description) {
	this.description = description;
}


public boolean isCorporateRendred() {
	return corporateRendred;
}


public void setCorporateRendred(boolean corporateRendred) {
	this.corporateRendred = corporateRendred;
}


public boolean isBankRendred() {
	return bankRendred;
}


public void setBankRendred(boolean bankRendred) {
	this.bankRendred = bankRendred;
}
public float getSolde()
{   
	if(client instanceof Corporate) return (float) ((Corporate)client).getCapitale();
	if(client instanceof BanqueCommercial) return (float) ((BanqueCommercial)client).getFondPropre();
	return (float) 0.0;
}

}
