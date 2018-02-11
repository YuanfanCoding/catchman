/**
  * Copyright 2018 bejson.com 
  */
package com.lazada.model.json.shopee.first;
import java.util.List;


public class Items {

    private long itemid;
    private int campaignid;
    private long shopid;
    private int adsid;
    private String ads_keyword;
    private List<Integer> logisticid;
    private String deduction_info;
    public void setItemid(long itemid) {
         this.itemid = itemid;
     }
     public long getItemid() {
         return itemid;
     }

    public void setCampaignid(int campaignid) {
         this.campaignid = campaignid;
     }
     public int getCampaignid() {
         return campaignid;
     }

    public void setShopid(long shopid) {
         this.shopid = shopid;
     }
     public long getShopid() {
         return shopid;
     }

    public void setAdsid(int adsid) {
         this.adsid = adsid;
     }
     public int getAdsid() {
         return adsid;
     }

    public void setAds_keyword(String ads_keyword) {
         this.ads_keyword = ads_keyword;
     }
     public String getAds_keyword() {
         return ads_keyword;
     }

    public void setLogisticid(List<Integer> logisticid) {
         this.logisticid = logisticid;
     }
     public List<Integer> getLogisticid() {
         return logisticid;
     }

    public void setDeduction_info(String deduction_info) {
         this.deduction_info = deduction_info;
     }
     public String getDeduction_info() {
         return deduction_info;
     }

}