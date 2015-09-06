package com.vsa.paperknifesample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Toast;

import com.vsa.paperknife.CellElement;
import com.vsa.paperknife.CellListenerProvider;
import com.vsa.paperknife.CellDataProvider;
import com.vsa.paperknife.ListenerSource;
import com.vsa.paperknifesample.adapter.CustomAdapter;

import java.util.List;

public class SampleActivity extends Activity implements SampleView, CellListenerProvider {

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
    public void setListItems(List<? extends CellElement> items, CellDataProvider cellDataProvider) {
        CustomAdapter adapter = new CustomAdapter(this, items, cellDataProvider, this);
        mListView.setAdapter(adapter);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @ListenerSource("CheckBox")
    public OnCheckedChangeListener provideCheckBoxListener(final CellElement cellElement) {
        return new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPresenter.onCheckChange(cellElement, isChecked);
            }
        };
    }

    @ListenerSource("Title")
    public View.OnClickListener provideOnClickListener(final CellElement cellElement) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onCheckChange(cellElement, true);
            }
        };
    }


}
