package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import Pidev.Service.Client.ClientServiceLocal;
import Pidev.Service.Currency.CurrencyServiceLocal;
import Pidev.Service.CurrencyAccountCorporate.CurrencyAccountCorporateServiceLocal;
import Pidev.entite.Client;
import Pidev.entite.Corporate;
import Pidev.entite.Currency;
import Pidev.entite.CurrencyAccountCorporate;


@SuppressWarnings("serial")
@ViewScoped
@ManagedBean
public class CorporateAccountBean implements Serializable {
    
	

	


	private Client client;
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
			.getExternalContext().getSession(true);
	private String connectedUser = (String) session
			.getAttribute("connectedUser");
	private CurrencyAccountCorporate cAR = new CurrencyAccountCorporate();
	private int selectedCOaccount;
	private List<String> availableIds;
	private List<CurrencyAccountCorporate> cARset;
	private String AccountCurrencyId;
	private boolean formDisplay=false;
	@EJB
	ClientServiceLocal clientServiceLocal;

	@EJB
	CurrencyServiceLocal currencyServiceLocal;

	@EJB
	CurrencyAccountCorporateServiceLocal currencyAccountCorporateServiceLocal;
	
	@PostConstruct
	public void init() {
		
		client = clientServiceLocal.findByLogin(connectedUser);
		
		if (client instanceof Corporate)
			{
			
			cARset = new ArrayList<CurrencyAccountCorporate>(((Corporate) client).getAccount());
			
			}
			
		setAvailableIds(currencyServiceLocal.findallAvailableIds());
	}
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
	

	public List<CurrencyAccountCorporate> getcARset() {
		return cARset;
	}
	public void setcARset(List<CurrencyAccountCorporate> cARset) {
		this.cARset = cARset;
	}
	public CurrencyAccountCorporate getcAR() {
		return cAR;
	}

	public void setcAR(CurrencyAccountCorporate cAR) {
		this.cAR = cAR;
	}
	public int getSelectedCOaccount() {
		return selectedCOaccount;
	}

	public void setSelectedCOaccount(int selectedCOaccount) {
		this.selectedCOaccount = selectedCOaccount;

	}
	

		
		public String getAccountCurrencyId() {
			return AccountCurrencyId;
		}

		public void setAccountCurrencyId(String accountCurrencyId) {
			AccountCurrencyId = accountCurrencyId;
		}

		public List<String> getAvailableIds() {
			return availableIds;
		}

		public void setAvailableIds(List<String> availableIds) {
			this.availableIds = availableIds;
		}
		
		
		public boolean isFormDisplay() {
			return formDisplay;
		}

		public void setFormDisplay(boolean formDisplay) {
			this.formDisplay = formDisplay;
		}

		public String doAddAccount() {

			
			if (client instanceof Corporate) {

				Currency cur = currencyServiceLocal.findBy(AccountCurrencyId);
				cAR.setCurrency(cur);

				cARset.add(cAR);
				((Corporate) client).setAccount( new HashSet<CurrencyAccountCorporate>(cARset));
				clientServiceLocal.update(client);
				client = clientServiceLocal.findByLogin(connectedUser);
				cARset = new ArrayList<CurrencyAccountCorporate>(((Corporate) client).getAccount());
                System.out.println("compteajouter");
			}
          return "Profile.jsf";
		}
		

		public void doDeleteCOAccount() {
            boolean test = currencyAccountCorporateServiceLocal.findByID(selectedCOaccount).getCurrency().isIs_local_currency();
	

			if(selectedCOaccount!=0 && !test)

			{  this.formDisplay = false;
				
				CurrencyAccountCorporate cA = currencyAccountCorporateServiceLocal
						.findByID(selectedCOaccount);
				
				

				cARset.remove(cA);
				((Corporate) client).setAccount(new HashSet<CurrencyAccountCorporate>(cARset));
				clientServiceLocal.update(client);
				client = clientServiceLocal.findByLogin(connectedUser);
				cARset = new ArrayList<CurrencyAccountCorporate>(((Corporate) client).getAccount());

			}}
	
	
}
