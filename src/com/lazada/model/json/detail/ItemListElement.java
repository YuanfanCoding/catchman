package com.lazada.model.json.detail;

import com.google.gson.annotations.SerializedName;

public class ItemListElement {

	@SerializedName("type")
   private String type;
	@SerializedName("image")
   private String image;
	@SerializedName("name")
   private String name;
	@SerializedName("offers")
   private Offers offers;
	@SerializedName("url")
   private String url;
   public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

   public void setImage(String image) {
        this.image = image;
    }
    public String getImage() {
        return image;
    }

   public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

   public void setOffers(Offers offers) {
        this.offers = offers;
    }
    public Offers getOffers() {
        return offers;
    }

   public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

}