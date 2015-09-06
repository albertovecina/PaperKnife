package com.vsa.paperknifesample.interactor;

import com.vsa.paperknifesample.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by albertovecinasanchez on 5/9/15.
 */
public class SampleInteractorImpl implements SampleInteractor {

    private List<Item> mFavouritesList = new ArrayList<>();

    @Override
    public List<Item> getFavouritesList() {
        return mFavouritesList;
    }

    @Override
    public List<Item> getItems() {

        List<Item> list = new ArrayList<>();
        Item item1 = new Item("Name1", "Description1");
        Item item2 = new Item("Name2", "Description2");
        Item item3 = new Item("Name3", "Description3");
        Item item4 = new Item("Name4", "Description4");
        mFavouritesList.add(item2);
        mFavouritesList.add(item4);
        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item4);

        return list;
    }

}
