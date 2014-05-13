package test;

import Pidev.entite.AskBid;
import Pidev.entite.Currency;

public class CurAskBid {
	private AskBid askbid;
	private Currency currency;
	public AskBid getAskbid() {
		return askbid;
	}
	public void setAskbid(AskBid askbid) {
		this.askbid = askbid;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	@Override
	public String toString() {
		return "CurAskBid [askbid=" + askbid + ", currency=" + currency + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((currency == null) ? 0 : currency.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CurAskBid other = (CurAskBid) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		return true;
	}
		

}
