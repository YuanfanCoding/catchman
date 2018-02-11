package com.lazada.model.json.shopee.category;
import java.util.List;

public class Calist {

    private Main main;
    private List<Sub> sub;
    public void setMain(Main main) {
         this.main = main;
     }
     public Main getMain() {
         return main;
     }

    public void setSub(List<Sub> sub) {
         this.sub = sub;
     }
     public List<Sub> getSub() {
         return sub;
     }

}