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
public class Page {

    private String internalSearchTerm;
    private String bucket_id;
    private String regCategoryId;
    private String xParams;
    private String SortingPattern;
    private List<String> filters;
    private String rn;
    private long resultNr;
    private String internalSearchResultType;
    public void setInternalSearchTerm(String internalSearchTerm) {
         this.internalSearchTerm = internalSearchTerm;
     }
     public String getInternalSearchTerm() {
         return internalSearchTerm;
     }

    public void setBucket_id(String bucket_id) {
         this.bucket_id = bucket_id;
     }
     public String getBucket_id() {
         return bucket_id;
     }

    public void setRegCategoryId(String regCategoryId) {
         this.regCategoryId = regCategoryId;
     }
     public String getRegCategoryId() {
         return regCategoryId;
     }

    public void setXParams(String xParams) {
         this.xParams = xParams;
     }
     public String getXParams() {
         return xParams;
     }

    public void setSortingPattern(String SortingPattern) {
         this.SortingPattern = SortingPattern;
     }
     public String getSortingPattern() {
         return SortingPattern;
     }

    public void setFilters(List<String> filters) {
         this.filters = filters;
     }
     public List<String> getFilters() {
         return filters;
     }

    public void setRn(String rn) {
         this.rn = rn;
     }
     public String getRn() {
         return rn;
     }

    public void setResultNr(long resultNr) {
         this.resultNr = resultNr;
     }
     public long getResultNr() {
         return resultNr;
     }

    public void setInternalSearchResultType(String internalSearchResultType) {
         this.internalSearchResultType = internalSearchResultType;
     }
     public String getInternalSearchResultType() {
         return internalSearchResultType;
     }

}