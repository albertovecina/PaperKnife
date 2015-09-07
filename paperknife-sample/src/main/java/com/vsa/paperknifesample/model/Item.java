package com.vsa.paperknifesample.model;

import com.vsa.paperknife.CellElement;
import com.vsa.paperknife.DataSource;

/**
 * Created by albertovecinasanchez on 4/9/15.
 */
public class Item implements CellElement {

    private String title;
    private String description;

    public Item(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Item) {
            Item anotherItem = (Item) o;
            return anotherItem.getTitle().equals(title)
                    && anotherItem.getDescription().equals(description);
        } else {
            return false;
        }
    }
}
