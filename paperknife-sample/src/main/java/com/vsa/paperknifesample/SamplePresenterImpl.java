package com.vsa.paperknifesample;

import com.vsa.paperknifesample.interactor.SampleInteractor;
import com.vsa.paperknifesample.interactor.SampleInteractorImpl;

/**
 * Created by albertovecinasanchez on 5/9/15.
 */
public class SamplePresenterImpl implements SamplePresenter {

    private SampleInteractor mInteractor = new SampleInteractorImpl();
    private SampleView mView;

    public SamplePresenterImpl(SampleView sampleView) {
        mView = sampleView;
    }


    @Override
    public void onResume() {
        mView.setListItems(mInteractor.getItems());
    }
}
