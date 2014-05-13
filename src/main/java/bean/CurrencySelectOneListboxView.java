package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import Pidev.Service.Currency.CurrencyServiceLocal;
import Pidev.entite.Currency;


@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class CurrencySelectOneListboxView implements Serializable {

	    static private Currency localCurrency;
	    private String id;
	    private List<SelectItem> workingCurrenciesList=new ArrayList<SelectItem>();
	    private List<Currency> currencies;
	    private List<String> currenciesIds;
	   
	    
	    
	    @EJB
	    CurrencyServiceLocal currencyServiceLocal;
	    
	    public static Currency getLocalCurrency() {
			return localCurrency;
		}


		public static void setLocalCurrency(Currency localCurrency) {
			CurrencySelectOneListboxView.localCurrency = localCurrency;
		}


		public List<Currency> getCurrencies() {
			return currencies;
		}


		public void setCurrencies(List<Currency> currencies) {
			this.currencies = currencies;
		}


		public List<String> getCurrenciesIds() {
			return currenciesIds;
		}


		public void setCurrenciesIds(List<String> currenciesIds) {
			this.currenciesIds = currenciesIds;
		}


		@PostConstruct
	    public void init(){
	    	currencies = currencyServiceLocal.findall();
	    	currenciesIds = currencyServiceLocal.findallIds();
	    }
	    
	    
		public CurrencySelectOneListboxView() {
			
		}
		
		
		public Currency getlocalCurrency() {
			return localCurrency;
		}
		@SuppressWarnings("static-access")
		public void setSelectedCurrenciesList(Currency localCurrency) {
			this.localCurrency = localCurrency;
		}
		public List<SelectItem> getWorkingCurrenciesList() {
			
		
			if(workingCurrenciesList == null || workingCurrenciesList.size() == 0)
			{	for(int i =0;i<currencies.size();i++)
				workingCurrenciesList.add(new SelectItem(currenciesIds.get(i), currenciesIds.get(i)));}
			return workingCurrenciesList;
		}
		public void setWorkingCurrenciesList(List<SelectItem> workingCurrenciesList) {
		
			this.workingCurrenciesList = workingCurrenciesList;
		}
		
		
		
		public String doRemoveCurrency(){
			
			System.out.println(id);
			Currency currency=currencyServiceLocal.findBy(id);
		    currencyServiceLocal.delete(currency);
		    currenciesIds = currencyServiceLocal.findallIds();
			
			return null;
		}
		public void setLocal(){
			
			System.out.println(id);
		       Currency currency=currencyServiceLocal.findBy(id);
				currencyServiceLocal.setLocal(currency);
				localCurrency=currency;
			
		}


		public String getId() {
			return id;
		}


		public void setId(String id) {
			this.id = id;
		}
	
}
