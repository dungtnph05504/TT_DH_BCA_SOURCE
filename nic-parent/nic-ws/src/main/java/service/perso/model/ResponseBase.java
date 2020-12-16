package service.perso.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ResponseBase")
@XmlType ( name = "ResponseBase",  propOrder = { "response", "property" } )
public class ResponseBase<T> {
	
	private T response;
	private PropertyModel property;
	public ResponseBase(){
		
	}
	
	public void setResponse(T response) {
		this.response = response;
	}
	
	public T getResponse() {
		return response;
	}
	
	public void setProperty(PropertyModel property) {
		this.property = property;
	}
	
	public PropertyModel getProperty() {
		return property;
	}
}
