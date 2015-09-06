package com.vsa.paperknifesample;

import com.vsa.paperknife.CellElement;

/**
 * Created by albertovecinasanchez on 5/9/15.
 */
public interface SamplePresenter {

    void onResume();
    void onCheckChange(CellElement cellElement, boolean checked);

}
