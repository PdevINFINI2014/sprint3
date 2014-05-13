package bean;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import Pidev.entite.Currency;



@SuppressWarnings("serial")
public class currencyConverter implements Converter,Serializable {

		public Object getAsObject(FacesContext context, UIComponent component,
				String value) {
			
			
			return null;
		}

		public String getAsString(FacesContext context, UIComponent component,
				Object value) {
			Currency currency = (Currency) value;
			return currency.toString();
		}
	
}
