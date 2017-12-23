package com.bullseyedevs.vitrin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by brkckr on 17.11.2017.
 */

public class Solution implements Serializable
{
    public Category category;
    public ArrayList<SubCategory> subCategoryList;
    public ArrayList<Item> itemList;
    public Map<SubCategory, ArrayList<Item>> itemMap;

    public Solution(Category category, ArrayList<SubCategory> subCategoryList, ArrayList<Item> itemList, Map<SubCategory, ArrayList<Item>> itemMap)
    {
        this.category = category;
        this.subCategoryList = subCategoryList;
        this.itemList = itemList;
        this.itemMap = itemMap;
    }
}
