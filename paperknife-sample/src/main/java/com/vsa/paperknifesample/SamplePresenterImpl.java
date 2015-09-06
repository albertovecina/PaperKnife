package com.vsa.paperknifesample;

import com.vsa.paperknife.CellElement;
import com.vsa.paperknife.CellDataProvider;
import com.vsa.paperknife.DataSource;
import com.vsa.paperknifesample.interactor.SampleInteractor;
import com.vsa.paperknifesample.interactor.SampleInteractorImpl;
import com.vsa.paperknifesample.model.Item;

/**
 * Created by albertovecinasanchez on 5/9/15.
 */
public class SamplePresenterImpl implements SamplePresenter, CellDataProvider {

    private SampleInteractor mInteractor = new SampleInteractorImpl();
    private SampleView mView;

    public SamplePresenterImpl(SampleView sampleView) {
        mView = sampleView;
    }

    @DataSource("Check")
    public boolean isOnFavouritesList(Item item) {
        return mInteractor.getFavouritesList().contains(item);
    }

    @Override
    public void onResume() {
        mView.setListItems(mInteractor.getItems(), this);
    }

    @Override
    public void onCheckChange(CellElement cellElement, boolean checked) {
        if(cellElement instanceof Item) {
            mView.showToast(((Item) cellElement).getTitle() + checked);
        }
    }
}
