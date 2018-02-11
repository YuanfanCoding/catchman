/**
  * Copyright 2018 bejson.com 
  */
package com.lazada.model.json.shopee.first;
import java.util.List;

/**
 * Auto-generated: 2018-02-11 18:14:50
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class FirstShopeeList {

    private boolean show_disclaimer;
    private Adjust adjust;
    private String version;
    private String algorithm;
    private List<Items> items;
    private List<String> hint_keywords;
    private int total_ads_count;
    private String reserved_keyword;
    private Price_adjust price_adjust;
    private int suggestion_algorithm;
    private long total_count;
    private boolean nomore;
    public void setShow_disclaimer(boolean show_disclaimer) {
         this.show_disclaimer = show_disclaimer;
     }
     public boolean getShow_disclaimer() {
         return show_disclaimer;
     }

    public void setAdjust(Adjust adjust) {
         this.adjust = adjust;
     }
     public Adjust getAdjust() {
         return adjust;
     }

    public void setVersion(String version) {
         this.version = version;
     }
     public String getVersion() {
         return version;
     }

    public void setAlgorithm(String algorithm) {
         this.algorithm = algorithm;
     }
     public String getAlgorithm() {
         return algorithm;
     }

    public void setItems(List<Items> items) {
         this.items = items;
     }
     public List<Items> getItems() {
         return items;
     }

    public void setHint_keywords(List<String> hint_keywords) {
         this.hint_keywords = hint_keywords;
     }
     public List<String> getHint_keywords() {
         return hint_keywords;
     }

    public void setTotal_ads_count(int total_ads_count) {
         this.total_ads_count = total_ads_count;
     }
     public int getTotal_ads_count() {
         return total_ads_count;
     }

    public void setReserved_keyword(String reserved_keyword) {
         this.reserved_keyword = reserved_keyword;
     }
     public String getReserved_keyword() {
         return reserved_keyword;
     }

    public void setPrice_adjust(Price_adjust price_adjust) {
         this.price_adjust = price_adjust;
     }
     public Price_adjust getPrice_adjust() {
         return price_adjust;
     }

    public void setSuggestion_algorithm(int suggestion_algorithm) {
         this.suggestion_algorithm = suggestion_algorithm;
     }
     public int getSuggestion_algorithm() {
         return suggestion_algorithm;
     }

    public void setTotal_count(long total_count) {
         this.total_count = total_count;
     }
     public long getTotal_count() {
         return total_count;
     }

    public void setNomore(boolean nomore) {
         this.nomore = nomore;
     }
     public boolean getNomore() {
         return nomore;
     }

}