package com.vsa.paperknifesample.interactor;

import com.vsa.paperknife.CellElement;
import com.vsa.paperknifesample.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by albertovecinasanchez on 5/9/15.
 */
public class SampleInteractorImpl implements SampleInteractor {

    @Override
    public List<Item> getItems() {

        List<Item> list = new ArrayList<>();
        list.add(new Item("Name1", "Description1"));
        list.add(new Item("Name2", "Description2"));
        list.add(new Item("Name3", "Description3"));
        return list;
    }

}
