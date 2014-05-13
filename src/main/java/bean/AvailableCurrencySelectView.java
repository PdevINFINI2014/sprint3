package bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import Pidev.Service.Currency.CurrencyServiceLocal;
import Pidev.entite.Currency;
@ViewScoped
@ManagedBean

public class AvailableCurrencySelectView {

	
	
	
	    private List<SelectItem> workingCurrenciesList=new ArrayList<SelectItem>();
	    private List<SelectItem> workingList= new ArrayList<SelectItem>();
	    private List<String> currenciesIds;
	    private String Selected=null;
	    private List<Currency> availableList;
	    private String selectedCur=null;
	    private List<String> currencies;
	    private boolean formDisplay=false;
	    private int unity;
	    




		@EJB
	    CurrencyServiceLocal currencyServiceLocal;
	    
	    @PostConstruct
	   public void init(){
	    	
	    	
	    	currenciesIds = currencyServiceLocal.findallAvailableIds();
	    	availableList =currencyServiceLocal.findAvailables();
	    	currencies= currencyServiceLocal.findallIds();
	    	
	    }
	    
	    
	    
	    
		public List<SelectItem> getWorkingCurrenciesList() {

			if(workingCurrenciesList == null || workingCurrenciesList.size() == 0)
			{	for(int i =0;i<currenciesIds.size();i++)
				workingCurrenciesList.add(new SelectItem(currenciesIds.get(i), currenciesIds.get(i)));}
			return workingCurrenciesList;
		}
		public void setWorkingCurrenciesList(List<SelectItem> workingCurrenciesList) {
			this.workingCurrenciesList = workingCurrenciesList;
		}
		public String getSelected() {
			return Selected;
		}
		public void setSelected(String selected) {
			Selected = selected;
		}
		public AvailableCurrencySelectView() {
		
		}
		
		  
	    public List<Currency> getAvailableList() {
			return availableList;
		}




		public void setAvailableList(List<Currency> availableList) {
			this.availableList = availableList;
		}
		  
				public String getSelectedCur() {
					return selectedCur;
				}




				public void setSelectedCur(String selectedCur) {
					this.selectedCur = selectedCur;
				}




				public boolean isFormDisplay() {
					return formDisplay;
				}




				public void setFormDisplay(boolean formDisplay) {
					this.formDisplay = formDisplay;
					
				}
				
				public int getUnity() {
					return unity;
				}




				public void setUnity(int unity) {
					this.unity = unity;
				}
				
				public String removeAvailability(){
					
					String navigateTo=null;
					Currency c =currencyServiceLocal.findBy(selectedCur);
					c.setIs_available(false);
					currencyServiceLocal.update(c);
					availableList =currencyServiceLocal.findAvailables();
					return navigateTo;
					
				}
				
				public String modifyCurrency(){
					
					String navigateTo=null;
					
					return navigateTo;
					
				}

				public void doSetAvailable(){
					
					System.out.println(Selected);
				       Currency currency=currencyServiceLocal.findBy(Selected);
				       currency.setIs_available(true);
						currencyServiceLocal.update(currency);
						availableList =currencyServiceLocal.findAvailables();
						
						
					
				}




				public List<String> getCurrencies() {
					return currencies;
				}




				public void setCurrencies(List<String> currencies) {
					this.currencies = currencies;
				}




				public List<SelectItem> getWorkingList() {
					if(workingList == null || workingList.size() == 0)
					{	for(int i =0;i<currencies.size();i++)
						workingList.add(new SelectItem(currencies.get(i), currencies.get(i)));}
					return workingList;
					
				}
				




				public void setWorkingList(List<SelectItem> workingList) {
					this.workingList = workingList;
				}
				
				
	    
}
