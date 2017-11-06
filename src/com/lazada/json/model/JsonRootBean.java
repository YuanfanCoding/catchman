/**
  * Copyright 2017 bejson.com 
  */
package com.lazada.json.model;
import java.util.List;

/**
 * Auto-generated: 2017-11-03 15:37:13
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
	public class JsonRootBean {

	    private String context;
	    private String type;
	    private List<ItemListElement> itemListElement;
	    public void setContext(String context) {
	         this.context = context;
	     }
	     public String getContext() {
	         return context;
	     }

	    public void setType(String type) {
	         this.type = type;
	     }
	     public String getType() {
	         return type;
	     }

	    public void setItemListElement(List<ItemListElement> itemListElement) {
	         this.itemListElement = itemListElement;
	     }
	     public List<ItemListElement> getItemListElement() {
	         return itemListElement;
	     }


}