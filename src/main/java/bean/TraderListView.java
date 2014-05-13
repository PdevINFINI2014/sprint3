package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import Pidev.Service.Client.ClientServiceLocal;
import Pidev.Service.Trader.TraderServiceLocal;
import Pidev.entite.Client;
import Pidev.entite.Trader;
@SuppressWarnings("serial")
@RequestScoped
@ManagedBean
public class TraderListView implements Serializable {

private String SelectedTrader;
private List<SelectItem> workingTraderList=new ArrayList<SelectItem>();
private List<String> traderIds=new ArrayList<String>();
HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
private String connectedUser = (String)session.getAttribute("connectedUser");
private Trader trader=new Trader();

private Client client ;
@EJB
ClientServiceLocal clientServiceLocal;

@EJB
TraderServiceLocal traderServiceLocal;

@PostConstruct
public void init(){
	
	 client = clientServiceLocal.findByLogin(connectedUser);
	Set <Trader> traders= client.getTrader();
	
	Iterator<Trader> i=traders.iterator(); 
	while(i.hasNext()) 
	{
		 traderIds.add((i.next()).getLogin());
	}
	
}
public String getSelectedTrader() {
	return SelectedTrader;
}
public void setSelectedTrader(String selectedTrader) {
	SelectedTrader = selectedTrader;
}
public List<SelectItem> getWorkingTraderList() {
	
	if(workingTraderList == null || workingTraderList.size() == 0)
	{	for(int i =0;i<traderIds.size();i++)
		workingTraderList.add(new SelectItem(traderIds.get(i), traderIds.get(i)));}
	
	return workingTraderList;
}


public Trader getTrader() {
	return trader;
}
public void setTrader(Trader trader) {
	this.trader = trader;
}
public void setWorkingTraderList(List<SelectItem> workingTraderList) {
	this.workingTraderList = workingTraderList;
}

public List<String> getTraderIds() {
	return traderIds;
}

public void setTraderIds(List<String> traderIds) {
	this.traderIds = traderIds;
}
public String doAddTrader(){
	
	
	client.getTrader().add(trader);
	
	
	clientServiceLocal.update(client);
	
	return "Profile?faces-redirecd=true";
	
	
}

public void doRemoveTrader(){
	@SuppressWarnings("unused")
	Set<Trader> set= new TreeSet<Trader>();
	trader=traderServiceLocal.findByLogin(SelectedTrader);
	
	Set<Trader> tr = client.getTrader();
	tr.remove(trader);
	client.setTrader(tr);
	clientServiceLocal.update(client);
	traderServiceLocal.delete(trader);
	
	
}


	
}
