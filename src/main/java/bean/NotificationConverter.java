package bean;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import Pidev.entite.Notification;




@FacesConverter(value = "notifConverter", forClass = Notification.class)
public class NotificationConverter implements Converter {

		public Object getAsObject(FacesContext context, UIComponent component,
				String value) {
		
			
			return null;
		}

		public String getAsString(FacesContext context, UIComponent component,
				Object value) {
			
			
			Notification notification = (Notification) value;
			return notification.getId()+".."+notification.getDate()+"   "+notification.getText();
		}
	
}
