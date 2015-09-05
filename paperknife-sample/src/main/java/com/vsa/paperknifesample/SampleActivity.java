package com.vsa.paperknifesample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.vsa.paperknife.CellElement;
import com.vsa.paperknifesample.adapter.CustomAdapter;

import java.util.List;

public class SampleActivity extends Activity implements SampleView {

    private ListView mListView;

    private SamplePresenter mPresenter = new SamplePresenterImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        mListView = (ListView) findViewById(R.id.list_view);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void setListItems(List<? extends CellElement> items) {
        CustomAdapter adapter = new CustomAdapter(this, items);
        mListView.setAdapter(adapter);
    }
}
