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
public class Filter {

    private String tItemType;
    private List<FilterItems> filterItems;
    private String bizData;
    private int pos;
    public void setTItemType(String tItemType) {
         this.tItemType = tItemType;
     }
     public String getTItemType() {
         return tItemType;
     }

    public void setFilterItems(List<FilterItems> filterItems) {
         this.filterItems = filterItems;
     }
     public List<FilterItems> getFilterItems() {
         return filterItems;
     }

    public void setBizData(String bizData) {
         this.bizData = bizData;
     }
     public String getBizData() {
         return bizData;
     }

    public void setPos(int pos) {
         this.pos = pos;
     }
     public int getPos() {
         return pos;
     }

}