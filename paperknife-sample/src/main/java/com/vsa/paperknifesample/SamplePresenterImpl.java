package com.vsa.paperknifesample;

import com.vsa.paperknife.CellProvider;
import com.vsa.paperknife.CellSource;
import com.vsa.paperknifesample.interactor.SampleInteractor;
import com.vsa.paperknifesample.interactor.SampleInteractorImpl;
import com.vsa.paperknifesample.model.Item;

/**
 * Created by albertovecinasanchez on 5/9/15.
 */
public class SamplePresenterImpl implements SamplePresenter, CellProvider {

    private SampleInteractor mInteractor = new SampleInteractorImpl();
    private SampleView mView;

    public SamplePresenterImpl(SampleView sampleView) {
        mView = sampleView;
    }

    @CellSource("Check")
    public boolean isOnFavouritesList(Item item) {
        return mInteractor.getFavouritesList().contains(item);
    }

    @Override
    public void onResume() {
        mView.setListItems(mInteractor.getItems(), this);
    }
}
