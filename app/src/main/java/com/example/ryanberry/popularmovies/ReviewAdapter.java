package com.example.ryanberry.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ryanberry.popularmovies.model.Reviewer;

import java.util.List;

public class ReviewAdapter extends ArrayAdapter {
    private static final String TAG = "ReviewAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<Reviewer> reviewers;

    public ReviewAdapter(@NonNull Context context, int resource, List<Reviewer> reviewers) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.reviewers = reviewers;
    }

    @Override
    public int getCount() {
        return reviewers.size();
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Reviewer reviewer = reviewers.get(position);
        viewHolder.author.setText(reviewer.getAuthor());
        viewHolder.preview.setText(reviewer.getContent());
        viewHolder.url.setText(reviewer.getUrl());
        Linkify.addLinks(viewHolder.url,Linkify.WEB_URLS);

        return convertView;
    }

    private class ViewHolder {
        TextView author;
        TextView preview;
        TextView url;

        public ViewHolder(View view) {
            this.author = view.findViewById(R.id.authorTextView);
            this.preview = view.findViewById(R.id.prevewTextView);
            this.url = view.findViewById(R.id.urlTextView);
        }
    }

}
