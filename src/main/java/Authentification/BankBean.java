package Authentification;

import java.io.Serializable;
import java.util.List;









import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


import Pidev.Service.BanqueCommercial.BanqueCommercialServiceLocal;
import Pidev.Service.Corporate.CorporateServiceLocal;
import Pidev.Service.Currency.CurrencyServiceLocal;
import Pidev.Service.Scoreboard.ScoreboardServiceLocal;
import Pidev.entite.AskBid;
import Pidev.entite.BanqueCommercial;
import Pidev.entite.Corporate;


@SuppressWarnings("serial")
@ManagedBean(name = "BankBean")
@RequestScoped
public class BankBean implements Serializable {

	@EJB
	private BanqueCommercialServiceLocal banqueSL;
	@EJB
	private CorporateServiceLocal corporateSL;
	@EJB
	private CurrencyServiceLocal curSL;
	@EJB
	private ScoreboardServiceLocal scSL;

	public List<BanqueCommercial> GetBanque() {
		return banqueSL.findAll();
	}

	public List<Corporate> GetCorporate() {
		return corporateSL.findAll();
	}

	public List<Pidev.entite.Currency> GetPrices(BanqueCommercial banque) {
		int id = banque.getIdClient();
		return scSL.getCurrency(scSL.findByID(banqueSL.findByID(id).getScoreboard().getId()));
		
		
		
	}

	public AskBid GetAskBid(Pidev.entite.Currency currency, BanqueCommercial banque) {
		int id = banque.getIdClient();
		return scSL.getAskBid(scSL.findByID(banqueSL.findByID(id).getScoreboard().getId()), currency);
	}
	public String test(){return "index1.jsf";}
	
}
