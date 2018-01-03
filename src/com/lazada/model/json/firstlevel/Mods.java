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
public class Mods {

    private Filter filter;
    private List<ListItems> listItems;
    private List<Breadcrumb> breadcrumb;
    private SortBar sortBar;
    private ResultTips resultTips;
    public void setFilter(Filter filter) {
         this.filter = filter;
     }
     public Filter getFilter() {
         return filter;
     }

    public void setListItems(List<ListItems> listItems) {
         this.listItems = listItems;
     }
     public List<ListItems> getListItems() {
         return listItems;
     }

    public void setBreadcrumb(List<Breadcrumb> breadcrumb) {
         this.breadcrumb = breadcrumb;
     }
     public List<Breadcrumb> getBreadcrumb() {
         return breadcrumb;
     }

    public void setSortBar(SortBar sortBar) {
         this.sortBar = sortBar;
     }
     public SortBar getSortBar() {
         return sortBar;
     }

    public void setResultTips(ResultTips resultTips) {
         this.resultTips = resultTips;
     }
     public ResultTips getResultTips() {
         return resultTips;
     }

}