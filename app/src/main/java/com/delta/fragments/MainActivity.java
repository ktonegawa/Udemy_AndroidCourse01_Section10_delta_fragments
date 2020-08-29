package com.delta.fragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

//this used to extend Activity, but from googling this apparently it is more correct to now
//extend FragmentActivity
public class MainActivity extends FragmentActivity implements HeadlinesFragment.OnHeadlineSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check if the activity is using the layout version
        //with the FrameLayout. if so, we have to add the fragment
        //(it wont be done automatically)
        if(findViewById(R.id.container) != null){
            //However if we are being restored from a previous state,
            //then dont do anything
            if(savedInstanceState != null){
                return;
            }

            //Create an instance of the Headline Fragment
            HeadlinesFragment headlinesFragment = new HeadlinesFragment();

            //In the case this activity was started with special instructions from an Intent,
            //pass the Intent's extras to the fragment as arguments
            headlinesFragment.setArguments(getIntent().getExtras());

            //Ask the Fragment manager to add it to the FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,headlinesFragment)
                    .commit();
//            //getFragmentManager is apparently no longer valid, so using this
//            //getSupportFragmentManager() method
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container,headlinesFragment)
//                    .commit();
        }
    }

    @Override
    public void onArticleSelected(int position) {
        //Capture the article fragment from the activity's dual-pane layout
        ArticleFragment articleFragment = (ArticleFragment) getSupportFragmentManager().findFragmentById(R.id.article_fragment);

        //if we dont find one, we must not be in two pane mode
        //lets swap the Fragments instead
        if(articleFragment != null){
            //we mus be in two pane layout
            articleFragment.updateArticleView(position);
        }else{
            //we must be in one pane layout

            //Create Fragment and give it an argument for the selected article right away
            ArticleFragment swapFragment = new ArticleFragment();
            Bundle args = new Bundle();
            args.putInt(ArticleFragment.ARG_POSITION,position);
            swapFragment.setArguments(args);

            //now that the Fragment is prepared, swap it
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,swapFragment)
                    .addToBackStack(null)
                    .commit();
        }

    }
}
