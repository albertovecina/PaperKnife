package com.vsa.paperknifesample;

import com.vsa.paperknife.CellElement;
import com.vsa.paperknife.CellProvider;

import java.util.List;

/**
 * Created by albertovecinasanchez on 5/9/15.
 */
public interface SampleView {

    void setListItems(List<? extends CellElement> items, CellProvider cellProvider);

}
