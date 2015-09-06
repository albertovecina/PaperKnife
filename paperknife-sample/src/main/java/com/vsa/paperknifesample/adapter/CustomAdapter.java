package com.vsa.paperknifesample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.vsa.paperknife.CellElement;
import com.vsa.paperknife.CellProvider;
import com.vsa.paperknife.CellTarget;
import com.vsa.paperknife.PaperKnife;
import com.vsa.paperknife.ViewTarget;
import com.vsa.paperknifesample.R;

import java.util.List;

/**
 * Created by albertovecinasanchez on 5/9/15.
 */
public class CustomAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<? extends CellElement> mList;
    private CellProvider mCellProvider;

    public CustomAdapter (Context context, List<? extends CellElement> list,
                          CellProvider cellProvider) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mList = list;
        mCellProvider = cellProvider;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CellElement getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.row_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PaperKnife.map(mList.get(position))
                .with(mCellProvider)
                .into(viewHolder);

        return convertView;
    }

    private static class ViewHolder implements ViewTarget {

        private TextView mTextViewTitle;
        private TextView mTextViewDescription;
        private CheckBox mCheckBox;

        public ViewHolder (View view) {
            mTextViewTitle = (TextView) view.findViewById(R.id.txt_row_title);
            mTextViewDescription = (TextView) view.findViewById(R.id.txt_row_description);
            mCheckBox = (CheckBox) view.findViewById(R.id.check_row);
        }

        @CellTarget("Title")
        public void setTitle(String title) {
            mTextViewTitle.setText(title);
        }

        @CellTarget("Description")
        public void setDescription(String description) {
            mTextViewDescription.setText(description);
        }

        @CellTarget("Check")
        public void setCheck(boolean checked) {
            mCheckBox.setChecked(checked);
        }

    }

}
