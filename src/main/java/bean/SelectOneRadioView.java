package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

@SuppressWarnings("serial")
public class SelectOneRadioView implements Serializable {
	 private String clientType="Corporate";
	    private List<SelectItem> clientTypeList;
	 
	    public String getclientType() {
	        return clientType;
	    }
	    public void setclientType(String clientType) {
	        this.clientType = clientType;
	    }
	    public List<SelectItem> getclientTypeList() {
	        if(clientTypeList == null || clientTypeList.size() == 0){
	            clientTypeList = new ArrayList<SelectItem>();
	            clientTypeList.add(new SelectItem("Corporate", "Corporate"));
	            clientTypeList.add(new SelectItem("Commercial Bank", "Commercial Bank"));
	           
	          
	        }
	        return clientTypeList;
	    }
	    public void setclientTypeList(List<SelectItem> clientTypeList) {
	        this.clientTypeList = clientTypeList;
	    }
}
