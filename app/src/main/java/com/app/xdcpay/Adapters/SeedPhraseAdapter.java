package com.app.xdcpay.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatTextView;

import com.app.xdcpay.R;

import java.util.List;

public class SeedPhraseAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<String> phraselist;
    private final int i;
    // Constructor
    public SeedPhraseAdapter(Context c, List<String> strList, int i) {
        mContext = c;
        this.phraselist = strList;
        this.i = i;
    }

    public int getCount() {
        return phraselist.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).
                    inflate(R.layout.item_seedphrase, parent, false);
        }


        // get the TextView for item name and item description
        AppCompatTextView tvWords = convertView.findViewById(R.id.tvWords);

        if(i==0)
        {
            tvWords.setText(phraselist.get(position));

        }
        else
        {
            tvWords.setText(position+1+". "+phraselist.get(position));
        }

        return convertView;
    }
}
