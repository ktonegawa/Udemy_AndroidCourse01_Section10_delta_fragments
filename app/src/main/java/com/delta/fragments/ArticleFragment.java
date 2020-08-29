package com.delta.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ArticleFragment extends Fragment {

    final static String ARG_POSITION = "position";
    private int currentPosition = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null){
            //if we recreated this Fragment (for instance from a screen rotate)
            //restore the previous article selection by getting it here
            currentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        //inflate the view for this fragment
        View myFragmentView = inflater.inflate(R.layout.article_fragment,container,false);
        return myFragmentView;
    }

    public void updateArticleView(int position){
        View v = getView();
        TextView article = (TextView) v.findViewById(R.id.article);
        String[] data = Ipsum.Articles;
        article.setText(data[position]);
        currentPosition = position;
    }

    @Override
    public void onStart() {
        super.onStart();

        //during startup, we should check if there are arguments (data)
        //passed to this Fragment. We know the layout has already been
        //applied to the Fragment so we can safely call the method that
        //set the article test
        Bundle args = getArguments();
        if(args != null){
            //set the article based on the argument passed in
            updateArticleView(args.getInt(ARG_POSITION));
        }else if (currentPosition != -1){
            //set the article based on the saved instance state defined during onCreateView
            updateArticleView(currentPosition);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //save the current selection for later recreation of this Fragment
        outState.putInt(ARG_POSITION, currentPosition);
    }
}
