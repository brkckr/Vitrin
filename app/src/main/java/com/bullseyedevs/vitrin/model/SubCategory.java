package com.bullseyedevs.vitrin.model;

import java.io.Serializable;

/**
 * Created by brkckr on 28.10.2017.
 */

public class SubCategory implements Serializable
{
    public int id;
    public int categoryId;
    public String name;
    public boolean isSelected = false;
    public boolean isExpanded = true;

    public SubCategory(int id, int categoryId, String name)
    {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
    }

    public SubCategory(SubCategory subCategory)
    {
        this.id = subCategory.id;
        this.categoryId = subCategory.categoryId;
        this.name = subCategory.name;
        this.isSelected = subCategory.isSelected;
    }

    public void setIsExpanded()
    {
        isExpanded= (!isExpanded);
    }
}
