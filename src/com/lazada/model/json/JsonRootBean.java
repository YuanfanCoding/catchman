
package com.lazada.model.json;
import java.util.List;

import com.google.gson.annotations.SerializedName;

	public class JsonRootBean {

		@SerializedName("context")
	    private String context;
		@SerializedName("type")
	    private String type;
		@SerializedName("itemListElement")
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