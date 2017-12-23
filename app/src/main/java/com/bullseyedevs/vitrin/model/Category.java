package com.bullseyedevs.vitrin.model;

import java.io.Serializable;

/**
 * Created by brkckr on 28.10.2017.
 */

public class Category implements Serializable
{
    public int id;
    public String name;
    public int resourceId;

    public Category(int id, String name, int resourceId)
    {
        this.id = id;
        this.name = name;
        this.resourceId = resourceId;
    }
}
