package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import Pidev.Service.Client.ClientServiceLocal;
import Pidev.Service.Currency.CurrencyServiceLocal;
import Pidev.Service.CurrencyAccountBanque.CurrencyAccountBanqueServiceLocal;
import Pidev.entite.BanqueCommercial;
import Pidev.entite.Client;
import Pidev.entite.Currency;
import Pidev.entite.CurrencyAccountBanque;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean
public class BankAccountBean implements Serializable {

	private Client client;
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
			.getExternalContext().getSession(true);
	private String connectedUser = (String) session
			.getAttribute("connectedUser");

	private CurrencyAccountBanque cARb = new CurrencyAccountBanque();
	private List<CurrencyAccountBanque> cARBset;
	private List<String> availableIds;
	private List<String> account_to_initialize = new ArrayList<String>();
	private float amount;
	private int accountToInit;
	private CurrencyAccountBanque SelectedBAaccount = null;
	private boolean formDisplay = false;
	@EJB
	ClientServiceLocal clientServiceLocal;

	@EJB
	CurrencyServiceLocal currencyServiceLocal;

	@EJB
	CurrencyAccountBanqueServiceLocal currencyAccountBanqueServiceLocal;

	@PostConstruct
	public void init() {

		client = clientServiceLocal.findByLogin(connectedUser);

		availableIds = currencyServiceLocal.findallAvailableIds();

		// ********************* l'initialisation des compte pour les banque
		// ****************************//
		if (client instanceof BanqueCommercial) {

			cARBset = new ArrayList<CurrencyAccountBanque>(((BanqueCommercial) client).getAccount());

			Iterator<CurrencyAccountBanque> j = cARBset.iterator();
			CurrencyAccountBanque ca = new CurrencyAccountBanque();

			for (String i : availableIds) {

				boolean cond = false;
				while (cond == false && j.hasNext()) {

					ca = j.next();
					if (ca.getCurrency().getId_currency().equals(i))
						cond = true;

				}

				j = cARBset.iterator();
				if (cond == false)
					account_to_initialize.add(i);

			}

			System.out.println("------------------------------");

			System.out.println(account_to_initialize);

			for (String i : account_to_initialize) {

				Currency cur = currencyServiceLocal.findBy(i);
				cARb.setCurrency(cur);
				cARb.setInitialized(false);
				cARBset.add(cARb);
				((BanqueCommercial) client).setAccount( new HashSet<CurrencyAccountBanque>(cARBset));
				clientServiceLocal.update(client);

			}

			if (client instanceof BanqueCommercial) {
				client = clientServiceLocal.findByLogin(connectedUser);
				cARBset = new ArrayList<CurrencyAccountBanque>(((BanqueCommercial) client).getAccount());
			}
		}
		account_to_initialize = new ArrayList<String>();
		// **********************************************************************************************//
		
	}

	public List<String> getAccount_to_initialize() {
		return account_to_initialize;
	}

	public void setAccount_to_initialize(List<String> account_to_initialize) {
		this.account_to_initialize = account_to_initialize;
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

	public List<CurrencyAccountBanque> getcARBset() {
		return cARBset;
	}

	public void setcARBset(List<CurrencyAccountBanque> cARBset) {
		this.cARBset = cARBset;
	}

	public List<String> getAvailableIds() {
		return availableIds;
	}

	public void setAvailableIds(List<String> availableIds) {
		this.availableIds = availableIds;
	}

	public int getAccountToInit() {

		return accountToInit;
	}

	public void setAccountToInit(int accountToInit) {

		this.accountToInit = accountToInit;

	}

	public CurrencyAccountBanque getcARb() {
		return cARb;
	}

	public void setcARb(CurrencyAccountBanque cARb) {
		this.cARb = cARb;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public CurrencyAccountBanque getSelectedBAaccount() {
         
		return SelectedBAaccount;
	}

	public void setSelectedBAaccount(CurrencyAccountBanque selectedBAaccount) {
		SelectedBAaccount = selectedBAaccount;
	}

	public boolean isFormDisplay() {
		return formDisplay;
	}

	public void setFormDisplay(boolean formDisplay) {
		this.formDisplay = formDisplay;
	}

	public void initBankAccount() {
if(accountToInit!=0)
		{SelectedBAaccount = currencyAccountBanqueServiceLocal
				.findByID(accountToInit);
		SelectedBAaccount.setAmount(amount);
		SelectedBAaccount.setInitialized(true);
		currencyAccountBanqueServiceLocal.update(SelectedBAaccount);
		client = clientServiceLocal.findByLogin(connectedUser);
		cARBset = new ArrayList<CurrencyAccountBanque>(((BanqueCommercial) client).getAccount());
		
		}
this.formDisplay=false;
	}
}
