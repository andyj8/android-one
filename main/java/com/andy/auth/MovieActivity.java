package com.andy.auth;

import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MovieActivity extends FragmentActivity {

    private String[] mMovies;
    private String[] mDescriptions;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        mMovies = getResources().getStringArray(R.array.movies);
        mDescriptions = getResources().getStringArray(R.array.movies_descs);

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.movie_pager);
        mViewPager.setAdapter(new MovieCollectionPagerAdapter(getSupportFragmentManager()));

        final Fragment pagerFragment = new Fragment() {
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                return mViewPager;
            }
        };

        Fragment listFragment = new ListFragment() {
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setListAdapter(new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_list_item_1, mMovies
                ));
            }
            @Override
            public void onListItemClick(ListView l, View v, int position, long id) {
                super.onListItemClick(l, v, position, id);
                getSupportFragmentManager().beginTransaction()
                    .hide(this)
                    .addToBackStack(null)
                    .show(pagerFragment)
                    .commit();
            }
            @Override
            public void onPause() {
                super.onPause();
                getSupportFragmentManager().beginTransaction()
                    .hide(pagerFragment)
                    .show(this)
                    .commit();
            }
        };

        getSupportFragmentManager().beginTransaction()
            .add(R.id.movies_container, listFragment)
            .add(R.id.movies_container, pagerFragment)
            .hide(pagerFragment)
            .commit();

    }

    public class MovieCollectionPagerAdapter extends FragmentStatePagerAdapter {
        public MovieCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return getDescFragment(position);
        }
        @Override
        public int getCount() {
            return mMovies.length;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mMovies[position];
        }
    }

    public Fragment getDescFragment(final int position) {
        return new Fragment() {
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                TextView movieDescTextView = new TextView(getActivity());
                movieDescTextView.setId(R.id.movie_desc_textview);
                int padding = getPadding();
                movieDescTextView.setPadding(padding, padding, padding, padding);
                movieDescTextView.setText(mDescriptions[position]);
                return movieDescTextView;
            }
            private int getPadding() {
                return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    4, getActivity().getResources().getDisplayMetrics());
            }
        };
    }

}


