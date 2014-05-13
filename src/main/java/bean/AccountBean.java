package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import Pidev.Service.Client.ClientServiceLocal;
import Pidev.Service.Currency.CurrencyServiceLocal;
import Pidev.Service.CurrencyAccountBanque.CurrencyAccountBanqueServiceLocal;
import Pidev.Service.CurrencyAccountCorporate.CurrencyAccountCorporateServiceLocal;
import Pidev.entite.BanqueCommercial;
import Pidev.entite.Client;
import Pidev.entite.Corporate;
import Pidev.entite.Currency;
import Pidev.entite.CurrencyAccountBanque;
import Pidev.entite.CurrencyAccountCorporate;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class AccountBean implements Serializable {

	private Client client;
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
			.getExternalContext().getSession(true);
	private String connectedUser = (String) session
			.getAttribute("connectedUser");
	private CurrencyAccountCorporate cAR = new CurrencyAccountCorporate();
	private CurrencyAccountBanque cARb = new CurrencyAccountBanque();
	private String AccountCurrencyId;
	private Set<CurrencyAccountCorporate> cARset;
	private Set<CurrencyAccountBanque> cARBset =null;
	private int selectedCOaccount;
	private List<String> availableIds;
	private List<String> account_to_initialize = new ArrayList<String>();
	private float amount;
	private int accountToInit;

	@EJB
	ClientServiceLocal clientServiceLocal;

	@EJB
	CurrencyServiceLocal currencyServiceLocal;

	@EJB
	CurrencyAccountCorporateServiceLocal currencyAccountCorporateServiceLocal;

	@EJB
	CurrencyAccountBanqueServiceLocal currencyAccountBanqueServiceLocal;

	@PostConstruct
	public void init() {
		
		client = clientServiceLocal.findByLogin(connectedUser);
		
		if (client instanceof Corporate)
			{
			
			cARset = ((Corporate) client).getAccount();
			
			System.out.println(cARset);}
			
		availableIds = currencyServiceLocal.findallAvailableIds();
		
		
		
		//********************* l'initialisation des compte pour les banque ****************************//
		if (client instanceof BanqueCommercial) {

			cARBset = ((BanqueCommercial) client).getAccount();
		

		Iterator<CurrencyAccountBanque> j =cARBset.iterator();
		CurrencyAccountBanque ca = new CurrencyAccountBanque();
		
		for(String i : availableIds){
			
		boolean cond = false;
		while(cond==false && j.hasNext()){
			
			ca=j.next();
			if(ca.getCurrency().getId_currency().equals(i)) 
				cond=true;
			
		}
			
		j =cARBset.iterator();
		if(cond==false) account_to_initialize.add(i);
			
		}
		
		
		System.out.println("------------------------------");
		
		System.out.println(account_to_initialize);

		for (String i : account_to_initialize) {

			Currency cur = currencyServiceLocal.findBy(i);
			cARb.setCurrency(cur);
			cARb.setInitialized(false);
			cARBset.add(cARb);
			((BanqueCommercial) client).setAccount(cARBset);
			clientServiceLocal.update(client);

		}

		if (client instanceof BanqueCommercial) {
			client = clientServiceLocal.findByLogin(connectedUser);
			cARBset = ((BanqueCommercial) client).getAccount();
		}}
		account_to_initialize = new ArrayList<String>();
		//**********************************************************************************************//

	}

	public List<String> getAccount_to_initialize() {
		return account_to_initialize;
	}

	public void setAccount_to_initialize(List<String> account_to_initialize) {
		this.account_to_initialize = account_to_initialize;
	}

	public Set<CurrencyAccountCorporate> getcARset() {
		return cARset;
	}

	public void setcARset(Set<CurrencyAccountCorporate> cARset) {
		this.cARset = cARset;
	}

	public CurrencyAccountCorporate getcAR() {
		return cAR;
	}

	public void setcAR(CurrencyAccountCorporate cAR) {
		this.cAR = cAR;
	}

	public String getAccountCurrencyId() {
		return AccountCurrencyId;
	}

	public void setAccountCurrencyId(String accountCurrencyId) {
		AccountCurrencyId = accountCurrencyId;
	}

	public Set<CurrencyAccountBanque> getcARBset() {

		return cARBset;
	}

	public void setcARBset(Set<CurrencyAccountBanque> cARBset) {
		this.cARBset = cARBset;
	}
	
	

	public int getAccountToInit() {
		System.out.println("!!"+accountToInit);
		return accountToInit;
	}

	public void setAccountToInit(int accountToInit) {
		
		this.accountToInit = accountToInit;
		System.out.println("!!"+accountToInit);
	}

	public void doAddAccount() {

		// pour les banque il faut une fonction set accounts qui gérent leur
		// crée un nouveau compte pour chaque devise valable
		if (client instanceof Corporate) {

			Currency cur = currencyServiceLocal.findBy(AccountCurrencyId);
			cAR.setCurrency(cur);

			cARset.add(cAR);
			((Corporate) client).setAccount(cARset);
			clientServiceLocal.update(client);
			client = clientServiceLocal.findByLogin(connectedUser);
			cARset = ((Corporate) client).getAccount();

		}

	}

	public int getSelectedCOaccount() {
		return selectedCOaccount;
	}

	public void setSelectedCOaccount(int selectedCOaccount) {
		this.selectedCOaccount = selectedCOaccount;

	}
	
	

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public void doDeleteCOAccount() {

		// problème au niveau de sql parent key !!!

		if (client instanceof Corporate) {

			CurrencyAccountCorporate cA = currencyAccountCorporateServiceLocal
					.findByID(selectedCOaccount);

			cARset.remove(cA);
			((Corporate) client).setAccount(cARset);
			clientServiceLocal.update(client);

		}

	}
	
	

	public CurrencyAccountBanque getcARb() {
		return cARb;
	}

	public void setcARb(CurrencyAccountBanque cARb) {
		this.cARb = cARb;
	}

	public void initBankAccount() {
		
//         System.out.println("!!"+accountToInit);
//		cARb=currencyAccountBanqueServiceLocal.findByID(accountToInit);
//		cARb.setAmount(amount);
//		cARb.setInitialized(true);
//		currencyAccountBanqueServiceLocal.update(cARb);
		
		

	}

}
