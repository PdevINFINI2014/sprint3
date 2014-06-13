package News;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;





import Pidev.Service.News.NewsServiceLocal;
import Pidev.entite.News;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class NewsBean implements Serializable {
//	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
//			.getExternalContext().getSession(true);
//	private String connectedUser = (String) session
//			.getAttribute("connectedUser");
	String contents ;
	
	
	@EJB
	NewsServiceLocal newsSL;
	
	List<News> listnews ;
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String post(){
		if(!this.contents.equals("")){
		News news = new News();
		news.setContents(this.contents);
		news.setDate_publication(new Date());
		newsSL.add(news);
		this.contents="";
		}
		return "news";
	}
	public List<News> getListnews() {
		return listnews;
	}
	public void setListnews(List<News> listnews) {
		this.listnews = listnews;
	}
	@PostConstruct 
	public void init(){
		this.listnews = newsSL.findAll();
	}
    public String delete(News news){
    	newsSL.remove(news);
    	
    	this.listnews = newsSL.findAll();
    	return "news";
    }
}
