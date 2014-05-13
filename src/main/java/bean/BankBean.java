package bean;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import Pidev.entite.BanqueCommercial;
import Pidev.Service.BanqueCommercial.BanqueCommercialServiceLocal;

@ViewScoped
@ManagedBean
public class BankBean {

	List <BanqueCommercial> banques;
	int selectedId;
	boolean formDisplay;
	String selectedResponse;
	BanqueCommercial banque = new BanqueCommercial();
	
	
	@EJB
	BanqueCommercialServiceLocal banqueCommercialServiceLocal;
	@PostConstruct
	public void init(){
		banques=banqueCommercialServiceLocal.findAll();
		
		
	}
	public List<BanqueCommercial> getBanques() {
		return banques;
	}
	public void setBanques(List<BanqueCommercial> banques) {
		this.banques = banques;
	}
	public int getSelectedId() {
		return selectedId;
	}
	public void setSelectedId(int selectedId) {
		this.selectedId = selectedId;
	}
	public boolean isFormDisplay() {
		return formDisplay;
	}
	public void setFormDisplay(boolean formDisplay) {
		this.formDisplay = formDisplay;
	}
	
	public String getSelectedResponse() {
		return selectedResponse;
	}
	public void setSelectedResponse(String selectedResponse) {
		this.selectedResponse = selectedResponse;
	}
	
	
	public BanqueCommercial getBanque() {
		return banque;
	}
	public void setBanque(BanqueCommercial banque) {
		this.banque = banque;
	}
	public String ModifyBank(){
		String navigateTo=null;
		banque=banqueCommercialServiceLocal.findByID(selectedId);
		if(selectedResponse.equals("true")) banque.setReqConfirmed(true);
		else banque.setReqConfirmed(false);
		banqueCommercialServiceLocal.update(banque);
		banques=banqueCommercialServiceLocal.findAll();
		return navigateTo;
	}
	
}
