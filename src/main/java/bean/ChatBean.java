package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import Pidev.Service.Client.ClientServiceLocal;
import Pidev.Service.Message.MessageServiceLocal;
import Pidev.entite.Client;
import Pidev.entite.Message;

@SuppressWarnings("serial")
@RequestScoped
@ManagedBean
public class ChatBean implements Serializable {
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
			.getExternalContext().getSession(true);
	private String connectedUser = (String) session
			.getAttribute("connectedUser");
	private List<Message> listMessage;
	private Client client;
	private String message;
	
	@EJB
	ClientServiceLocal clientSL;
	@EJB
	MessageServiceLocal messageSL;
	@PostConstruct
	public void init() {
		client = clientSL.findByLogin(connectedUser);
		listMessage = messageSL.findAll();
	}

	public List<Message> getListMessage() {
		return listMessage;
	}

	public void setListMessage(List<Message> listMessage) {
		this.listMessage = listMessage;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    public void send(){
    	if(!this.message.equals("")){
      Message mes = new Message();
      mes.setContents(this.message);
      mes.setDate_message(new Date());
      mes.setSender(this.client);
      messageSL.add(mes);
      this.listMessage = messageSL.findAll();
      this.message = null;
    }
    }
}
