package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import Pidev.Service.BanqueCommercial.BanqueCommercialServiceLocal;
import Pidev.Service.Notification.NotificationServiceLocal;
import Pidev.entite.BanqueCommercial;
import Pidev.entite.Notification;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class NotificationBean implements Serializable {

	String text;
	List<String> banques;
	BanqueCommercial destination;
	String selectedBanque;
	List<Notification> notifications;
	List<Notification> newNotifications;
	boolean showNotification=false;
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	private String connectedUser = (String)session.getAttribute("connectedUser");
	



	@EJB
	BanqueCommercialServiceLocal banqueCommercialServiceLocal;
	
	@EJB
	NotificationServiceLocal notificationServiceLocal;
	
	
	
	@PostConstruct 
	public void init(){
		
		banques=banqueCommercialServiceLocal.findALLNames();
		notifications=notificationServiceLocal.findAll();
		newNotifications=notificationServiceLocal.findUnread(connectedUser);
		
		if(newNotifications.size() != 0) showNotification=true;
		System.out.println(newNotifications.size());
		
		
		
		
		
	}
	
	

	public NotificationBean() {
	
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}



	public List<String> getBanques() {
		return banques;
	}



	public void setBanques(List<String> banques) {
		this.banques = banques;
	}



	public BanqueCommercial getDestination() {
		return destination;
	}



	public void setDestination(BanqueCommercial destination) {
		this.destination = destination;
	}
	public String getSelectedBanque() {
		return selectedBanque;
	}



	public void setSelectedBanque(String selectedBanque) {
		this.selectedBanque = selectedBanque;
	}
	
	
	
	public String sendNotification(){
		
		Notification notif= new Notification();
		notif.setText(text);
		notif.setRead(false);
	    notif.setDate(new Date());
		notif.setId_destination(selectedBanque);
		notificationServiceLocal.add(notif);
		
		
		return null;
	}



	public List<Notification> getNotifications() {
		return notifications;
	}



	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
		
	}



	public boolean isShowNotification() {
		return showNotification;
	}



	public void setShowNotification(boolean showNotification) {
		this.showNotification = showNotification;
		
	}
	
	
	public String ShowHideNotification(){
		
		
		notificationServiceLocal.setRead();
		showNotification=false;
		
		
		return null;
	}

		public List<Notification> getNewNotifications() {
			return newNotifications;
		}



		public void setNewNotifications(List<Notification> newNotifications) {
			this.newNotifications = newNotifications;
		}

	
	
	
}
