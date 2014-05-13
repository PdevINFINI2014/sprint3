package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import test.CurAskBid;
import Pidev.Service.Admin.AdminServiceLocal;
import Pidev.Service.BanqueCommercial.BanqueCommercialServiceLocal;
import Pidev.Service.Client.ClientServiceLocal;
import Pidev.Service.Scoreboard.ScoreboardServiceLocal;
import Pidev.Service.Trader.TraderServiceLocal;
import Pidev.entite.Admin;
import Pidev.entite.AskBid;
import Pidev.entite.BanqueCentrale;
import Pidev.entite.BanqueCommercial;
import Pidev.entite.Client;
import Pidev.entite.Corporate;
import Pidev.entite.Currency;
import Pidev.entite.Trader;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class AuthenticateBean implements Serializable {

	private String login;
	private String password;
	private Admin admin;
	private Client client;
	private BanqueCommercial banque;

	private List<CurAskBid> list;

	public List<CurAskBid> getList() {
		return list;
	}

	public void setList(List<CurAskBid> list) {
		this.list = list;
	}

	public BanqueCommercial getBanque() {
		return banque;
	}

	public void setBanque(BanqueCommercial banque) {
		this.banque = banque;
	}

	private Trader trader;

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Trader getTrader() {
		return trader;
	}

	public void setTrader(Trader trader) {
		this.trader = trader;
	}

	private boolean failure = false;

	@EJB
	public AdminServiceLocal adminServiceLocal;
	@EJB
	public TraderServiceLocal traderServiceLocal;
	@EJB
	public ClientServiceLocal clientServiceLocal;
	@EJB
	public BanqueCommercialServiceLocal banqueCommercialServiceLocal;
	@EJB
	public ScoreboardServiceLocal scSL;

	public boolean isFailure() {
		return failure;
	}

	public void setFailure(boolean failure) {
		this.failure = failure;
	}

	public AuthenticateBean() {

	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String authenticate() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);

		System.out.println(login + " " + password);

		admin = adminServiceLocal.findAll().get(0);

		client = clientServiceLocal.findByLogin(login);

		trader = traderServiceLocal.findByLogin(login);

		banque = banqueCommercialServiceLocal.findByLogin(login);

		if (admin != null && admin.getLogin().equals(login)) {

			session.setAttribute("connectedUser", admin.getLogin());
			return "admin";
		}

		else if (client != null && client.getPass().equals(password)) {
			if (client instanceof Corporate) {
				session.setAttribute("connectedUser", client.getLogin());

				return "Profile";
			}

			if (client instanceof BanqueCommercial) {
				session.setAttribute("connectedUser", client.getLogin());

				return "Profile";
			}
			if (client instanceof BanqueCentrale) {
				session.setAttribute("connectedUser", client.getLogin());

				return "centralBank";

			}

		}

		else if (trader != null && trader.getPassword().equals(password))

		{
			for (Client i : clientServiceLocal.findAll())
				for (Trader j : i.getTrader())
					if (j.getLogin().equals(trader.getLogin())) {
						session.setAttribute("connectedUser", i.getLogin());
						return "success";
					}
		}

		else {
			failure = true;
			return null;
		}
		return null;
	}

	public String logout() {
		String navigateTo = null;
		admin = new Admin();
		client = new Client();
		trader = new Trader();

		navigateTo = "login.jsf";
		return navigateTo;

		// revoir cette methode
	}

	public List<Currency> getCur() {

		int id = this.banque.getIdClient();
		return scSL.getCurrency(scSL.findByID(banqueCommercialServiceLocal
				.findByID(id).getScoreboard().getId()));

	}

	public List<AskBid> getAskBid() {
		int id = this.banque.getIdClient();
		return scSL.getAskBid(scSL.findByID(banqueCommercialServiceLocal
				.findByID(id).getScoreboard().getId()));
	}

	public List<CurAskBid> listCA() {
		if (this.banque != null) {
			List<CurAskBid> res = new ArrayList<CurAskBid>();
			List<Currency> cur = this.getCur();
			List<AskBid> askbid = this.getAskBid();
			for (int i = 0; i < cur.size(); i++) {
				CurAskBid curaskbid = new CurAskBid();
				curaskbid.setAskbid(askbid.get(i));
				curaskbid.setCurrency(cur.get(i));
				res.add(curaskbid);
			}

			return res;
		}
		return null;
	}

}
