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
public class SortBar {

    private String tItemType;
    private List<SortItems> sortItems;
    private String filter;
    private String style;
    public void setTItemType(String tItemType) {
         this.tItemType = tItemType;
     }
     public String getTItemType() {
         return tItemType;
     }

    public void setSortItems(List<SortItems> sortItems) {
         this.sortItems = sortItems;
     }
     public List<SortItems> getSortItems() {
         return sortItems;
     }

    public void setFilter(String filter) {
         this.filter = filter;
     }
     public String getFilter() {
         return filter;
     }

    public void setStyle(String style) {
         this.style = style;
     }
     public String getStyle() {
         return style;
     }

}