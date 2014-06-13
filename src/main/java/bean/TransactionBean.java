package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import Pidev.Service.BanqueCommercial.BanqueCommercialServiceLocal;
import Pidev.Service.Client.ClientServiceLocal;
import Pidev.Service.Corporate.CorporateServiceLocal;
import Pidev.Service.Currency.CurrencyServiceLocal;
import Pidev.Service.CurrencyAccountBanque.CurrencyAccountBanqueServiceLocal;
import Pidev.Service.CurrencyAccountCorporate.CurrencyAccountCorporateServiceLocal;
import Pidev.Service.Scoreboard.ScoreboardServiceLocal;
import Pidev.Service.Transaction.TransactionServiceLocal;
import Pidev.entite.BanqueCommercial;
import Pidev.entite.Client;
import Pidev.entite.Corporate;
import Pidev.entite.Currency;
import Pidev.entite.CurrencyAccountBanque;
import Pidev.entite.CurrencyAccountCorporate;
import Pidev.entite.Transaction;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class TransactionBean implements Serializable {
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
			.getExternalContext().getSession(true);
	private String connectedUser = (String) session
			.getAttribute("connectedUser");
	private String curSelect;
	private String bnkSelect;
	private double ammountSelect = 0.0;
	@EJB
	public TransactionServiceLocal tSL;
	@EJB
	CurrencyAccountBanqueServiceLocal currencyAccountBanqueSL;
	@EJB
	CurrencyAccountCorporateServiceLocal currencyAccountCorporateSL;
	@EJB
	CurrencyServiceLocal curSL;
	@EJB
	BanqueCommercialServiceLocal bnkSL;
	@EJB
	ScoreboardServiceLocal scSL;
	@EJB
	ClientServiceLocal clientSL;
	@EJB
	CorporateServiceLocal corSL;

	private List<Transaction> transactions;
	private double price;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public String getCurSelect() {
		return curSelect;
	}

	public void setCurSelect(String curSelect) {
		this.curSelect = curSelect;
	}

	public String getBnkSelect() {
		return bnkSelect;
	}

	public void setBnkSelect(String bnkSelect) {
		this.bnkSelect = bnkSelect;
	}

	public double getAmmountSelect() {
		return ammountSelect;
	}

	public void setAmmountSelect(double ammountSelect) {
		this.ammountSelect = ammountSelect;
	}

	public TransactionBean() {
		super();

	}

	@PostConstruct
	public void init() {

		transactions = new ArrayList<Transaction>();
		for (Transaction i : tSL.findAll()) {
			if ((i.getBuyer().getLogin().equals(connectedUser))
					|| (i.getSeller().getLogin().equals(connectedUser)))
				transactions.add(i);
		}
	}

	public String testBayer(Transaction tr) {
		if (this.testBS(tr).equals("Buy"))
			return tr.getBuyer().getLibelle();
		return tr.getSeller().getLibelle();
	}

	public String testBS(Transaction tr) {
		if (tr.getSeller().getLogin()
				.equals(clientSL.findByLogin(connectedUser).getLogin()))
			return "Buy";
		return "Sell";
	}

	public List<String> getCur() {
		List<String> res = new ArrayList<String>();
		if (clientSL.findByLogin(connectedUser) instanceof Corporate) {
			for (CurrencyAccountCorporate i : ((Corporate) clientSL
					.findByLogin(connectedUser)).getAccount())
				if (i.getCurrency().isIs_available())
					res.add(i.getCurrency().getId_currency());
			return res;
		}
		if (clientSL.findByLogin(connectedUser) instanceof BanqueCommercial) {
			for (Currency i : curSL.findall())
				if (i.isIs_available())
					res.add(i.getId_currency());
			return res;
		}
		return null;
	}

	public List<String> getBnk() {
		List<String> res = new ArrayList<String>();
		for (BanqueCommercial i : bnkSL.findAll())
			if (!i.getLogin().equals(this.connectedUser))
				res.add(i.getLibelle());
		return res;
	}

	public double getbid() {
		try {
			if ((bnkSelect != null) && (curSelect != null)) {
				BanqueCommercial banq = bnkSL.findByLogin(bnkSelect);

				Currency cur = curSL.findBy(curSelect);
				return scSL.getAskBid(banq.getScoreboard(), cur).getBid();
			}
			return 0.0;
		} catch (Exception e) {
			return 0.0;
		}
	}
	public double getask() {
		try {
			if ((bnkSelect != null) && (curSelect != null)) {
				BanqueCommercial banq = bnkSL.findByLogin(bnkSelect);

				Currency cur = curSL.findBy(curSelect);
				return scSL.getAskBid(banq.getScoreboard(), cur).getAsk();
			}
			return 0.0;
		} catch (Exception e) {
			return 0.0;
		}
	}

	public double getsommebid() {
		return this.ammountSelect * this.getbid();
	}
	public double getsommeask() {
		return this.ammountSelect * this.getask();
	}

	public String transactionBayer() {
		if (this.curSelect != null && this.bnkSelect != null) {
			Client client = clientSL.findByLogin(connectedUser);
			BanqueCommercial bnk = bnkSL.findByLogin(bnkSelect);
			Set<CurrencyAccountCorporate> setC = null;
			Set<CurrencyAccountBanque> setB = null;
			if (client instanceof BanqueCommercial) {
				setB = ((BanqueCommercial) client).getAccount();
				for (CurrencyAccountBanque i : setB) {
					if (i.getCurrency().getId_currency().equals(curSelect)) {
						i.setAmount((float) (i.getAmount() + this.ammountSelect));
						currencyAccountBanqueSL.update(i);
					}
					
				}
				double font = ((BanqueCommercial) client).getFondPropre();
				((BanqueCommercial) client).setFondPropre(font
						- this.getsommebid());
				bnkSL.update(((BanqueCommercial) client));

			}
			if (client instanceof Corporate) {
				setC = ((Corporate) client).getAccount();
				for (CurrencyAccountCorporate i : setC) {
					if (i.getCurrency().getId_currency().equals(curSelect)) {
						i.setAmount(i.getAmount() + this.ammountSelect);
						currencyAccountCorporateSL.update(i);
					}
					
				}
				double capitale = ((Corporate) client).getCapitale();
				((Corporate) client)
						.setCapitale(capitale - this.getsommebid());
				corSL.update(((Corporate) client));
			}
			Set<CurrencyAccountBanque> set = bnk.getAccount();
			for (CurrencyAccountBanque i : set) {
				if (i.getCurrency().getId_currency().equals(curSelect)) {
					i.setAmount((float) (i.getAmount() - this.ammountSelect));
					currencyAccountBanqueSL.update(i);
				}
				bnk.setFondPropre(bnk.getFondPropre() + this.getsommebid());
				bnkSL.update(bnk);
			}
			Transaction transaction = new Transaction();
			transaction.setAmount_Ctr_Price(getsommebid());
			transaction.setBuyer(client);
			transaction.setSeller(bnkSL.findByLogin(bnkSelect));
			transaction.setCurr(curSL.findBy(curSelect));
			transaction.setCurrencyAmount(ammountSelect);
			transaction.setCurrPrice(getbid());
			transaction.setDate_transaction(new Date());
			try {
				tSL.add(transaction);
			} catch (Exception e) {
			}

			return "Profile";
		}
		return null;
	}
	public String transactionSeller() {
		if (this.curSelect != null && this.bnkSelect != null) {
			Client client = clientSL.findByLogin(connectedUser);
			BanqueCommercial bnk = bnkSL.findByLogin(bnkSelect);
			Set<CurrencyAccountCorporate> setC = null;
			Set<CurrencyAccountBanque> setB = null;
			if (client instanceof BanqueCommercial) {
				setB = ((BanqueCommercial) client).getAccount();
				for (CurrencyAccountBanque i : setB) {
					if (i.getCurrency().getId_currency().equals(curSelect)) {
						i.setAmount((float) (i.getAmount() - this.ammountSelect));
						currencyAccountBanqueSL.update(i);
					}
					
				}
				double font = ((BanqueCommercial) client).getFondPropre();
				((BanqueCommercial) client).setFondPropre(font
						+ this.getsommeask());
				bnkSL.update(((BanqueCommercial) client));

			}
			if (client instanceof Corporate) {
				setC = ((Corporate) client).getAccount();
				for (CurrencyAccountCorporate i : setC) {
					if (i.getCurrency().getId_currency().equals(curSelect)) {
						i.setAmount(i.getAmount() - this.ammountSelect);
						currencyAccountCorporateSL.update(i);
					}
					
				}
				double capitale = ((Corporate) client).getCapitale();
				((Corporate) client)
						.setCapitale(capitale + this.getsommeask());
				corSL.update(((Corporate) client));
			}
			Set<CurrencyAccountBanque> set = bnk.getAccount();
			for (CurrencyAccountBanque i : set) {
				if (i.getCurrency().getId_currency().equals(curSelect)) {
					i.setAmount((float) (i.getAmount() - this.ammountSelect));
					currencyAccountBanqueSL.update(i);
				}
				bnk.setFondPropre(bnk.getFondPropre() - this.getsommeask());
				bnkSL.update(bnk);
			}
			Transaction transaction = new Transaction();
			transaction.setAmount_Ctr_Price(getsommeask());
			transaction.setBuyer(bnkSL.findByLogin(bnkSelect));
			transaction.setSeller(client);
			transaction.setCurr(curSL.findBy(curSelect));
			transaction.setCurrencyAmount(ammountSelect);
			transaction.setCurrPrice(getbid());
			transaction.setDate_transaction(new Date());
			try {
				tSL.add(transaction);
			} catch (Exception e) {
			}

			return "Profile";
		}
		return null;
	}

}
