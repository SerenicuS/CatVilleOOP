package org.dreamchurch.mediateam;

public class CatClass {

    private String catName;


    public CatClass(String selectedText) {
        catName = selectedText;
    }

    public CatClass(){
        catName = "";
    }

    public void testCat(){
        System.out.println("The cat name is" + catName);
    }
}
