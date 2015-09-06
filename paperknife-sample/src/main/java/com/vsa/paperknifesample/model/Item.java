package com.vsa.paperknifesample.model;

import com.vsa.paperknife.CellElement;
import com.vsa.paperknife.CellSource;

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

    @CellSource("Title")
    public String getTitle() {
        return title;
    }

    @CellSource("Description")
    public String getDescription() {
        return description;
    }

    public int getSize() {
        return 0;
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
