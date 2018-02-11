/**
  * Copyright 2018 bejson.com 
  */
package com.lazada.model.json.shopee.category;
import java.util.List;

/**
 * Auto-generated: 2018-02-11 16:39:23
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Sub {

    private String display_name;
    private String name;
    private int catid;
    private String image;
    private int parent_category;
    private int is_adult;
    private int sort_weight;
    private List<Sub_sub> sub_sub;
    public void setDisplay_name(String display_name) {
         this.display_name = display_name;
     }
     public String getDisplay_name() {
         return display_name;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setCatid(int catid) {
         this.catid = catid;
     }
     public int getCatid() {
         return catid;
     }

    public void setImage(String image) {
         this.image = image;
     }
     public String getImage() {
         return image;
     }

    public void setParent_category(int parent_category) {
         this.parent_category = parent_category;
     }
     public int getParent_category() {
         return parent_category;
     }

    public void setIs_adult(int is_adult) {
         this.is_adult = is_adult;
     }
     public int getIs_adult() {
         return is_adult;
     }

    public void setSort_weight(int sort_weight) {
         this.sort_weight = sort_weight;
     }
     public int getSort_weight() {
         return sort_weight;
     }

    public void setSub_sub(List<Sub_sub> sub_sub) {
         this.sub_sub = sub_sub;
     }
     public List<Sub_sub> getSub_sub() {
         return sub_sub;
     }

}