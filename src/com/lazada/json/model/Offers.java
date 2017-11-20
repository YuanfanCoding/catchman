
package com.lazada.json.model;

import com.google.gson.annotations.SerializedName;

public class Offers {

	@SerializedName("type")
   private String type;
	@SerializedName("availability")
   private String availability;
	@SerializedName("price")
   private String price;
	@SerializedName("priceCurrency")
   private String priceCurrency;
   public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

   public void setAvailability(String availability) {
        this.availability = availability;
    }
    public String getAvailability() {
        return availability;
    }

   public void setPrice(String price) {
        this.price = price;
    }
    public String getPrice() {
        return price;
    }

   public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }
    public String getPriceCurrency() {
        return priceCurrency;
    }

}