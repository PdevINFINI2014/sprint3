package bean;

import java.io.Serializable;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import Pidev.Service.BanqueCommercial.BanqueCommercialServiceLocal;
import Pidev.Service.Currency.CurrencyServiceLocal;
import Pidev.Service.Scoreboard.ScoreboardServiceLocal;
import Pidev.entite.AskBid;
import Pidev.entite.BanqueCommercial;
import Pidev.entite.Currency;


@SuppressWarnings("serial")
@RequestScoped
@ManagedBean
public class AskBidBean implements Serializable {
	@EJB
	public ScoreboardServiceLocal scSL;
	@EJB
	public BanqueCommercialServiceLocal banqueCommercialServiceLocal;
	@EJB
	public CurrencyServiceLocal curSL;
	
	
	private String selectCurrency ;
	public String getSelectCurrency() {
		return selectCurrency;
	}
	public void setSelectCurrency(String selectCurrency) {
		this.selectCurrency = selectCurrency;
	}
	private AskBid askbid = new AskBid();
	private List<SelectItem> workingCurrency=new ArrayList<SelectItem>();
	private List<String> currencyIds=new ArrayList<String>();
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	private String connectedUser = (String)session.getAttribute("connectedUser");
	private BanqueCommercial banque;
	private boolean banqueRendred;
	private boolean corRendred;
	
	
	public boolean isCorRendred() {
		return corRendred;
	}
	public void setCorRendred(boolean corRendred) {
		this.corRendred = corRendred;
	}
	public boolean isBanqueRendred() {
		return banqueRendred;
	}
	public void setBanqueRendred(boolean banqueRendred) {
		this.banqueRendred = banqueRendred;
	}
	public List<String> getCurrencyIds() {
		return currencyIds;
	}
	public void setCurrencyIds(List<String> currencyIds) {
		this.currencyIds = currencyIds;
	}
	public List<SelectItem> getWorkingCurrency() {
		if(workingCurrency == null || workingCurrency.size() == 0)
		{	for(int i =0;i<currencyIds.size();i++)
			workingCurrency.add(new SelectItem(currencyIds.get(i), currencyIds.get(i)));}
		
		return workingCurrency;
	}
	public void setWorkingCurrency(List<SelectItem> workingCurrency) {
		this.workingCurrency = workingCurrency;
	}
	public BanqueCommercial getBanque() {
		return banque;
	}
	public void setBanque(BanqueCommercial banque) {
		this.banque = banque;
	}
	@PostConstruct
	public void init(){
		
		 banque = banqueCommercialServiceLocal.findByLogin(connectedUser);
		 if(banque != null){
		 this.banqueRendred = true;
		 
		 for(Currency i : scSL.getCurrency(banque.getScoreboard()))
		 {
			 this.currencyIds.add(i.getId_currency());
		 }
		 }
		 else this.corRendred = true;
		
	}
	public String getConnectedUser() {
		return connectedUser;
	}
	public void setConnectedUser(String connectedUser) {
		this.connectedUser = connectedUser;
	}

	public AskBid getAskbid() {
		return askbid;
	}
	public void setAskbid(AskBid askbid) {
		this.askbid = askbid;
	}
	public String setAB() {
		int id = this.banque.getIdClient();
		

		scSL.setAsk(
				scSL.findByID(banqueCommercialServiceLocal.findByID(id)
						.getScoreboard().getId()), curSL.findBy(selectCurrency), 
						this.askbid);
	return "Profile.jsf";
	}
	public void getAB()
	{  int id = this.banque.getIdClient();
		this.askbid = scSL.getAskBid(banqueCommercialServiceLocal.findByID(id)
						.getScoreboard(), curSL.findBy(selectCurrency));
	System.out.println(this.askbid);
	}

}
