/**
  * Copyright 2018 bejson.com 
  */
package com.lazada.model.json.firstlevel;
import java.util.List;

/**
 * Auto-generated: 2018-01-03 17:31:44
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ResultTips {

    private String tItemType;
    private String tips;
    private String brandLink;
    private List<Keywords> keywords;
    public void setTItemType(String tItemType) {
         this.tItemType = tItemType;
     }
     public String getTItemType() {
         return tItemType;
     }

    public void setTips(String tips) {
         this.tips = tips;
     }
     public String getTips() {
         return tips;
     }

    public void setBrandLink(String brandLink) {
         this.brandLink = brandLink;
     }
     public String getBrandLink() {
         return brandLink;
     }

    public void setKeywords(List<Keywords> keywords) {
         this.keywords = keywords;
     }
     public List<Keywords> getKeywords() {
         return keywords;
     }

}