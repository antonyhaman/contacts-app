package com.github.kotvertolet.contactsapp.contacts;

import android.app.SearchManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.github.kotvertolet.contactsapp.R;
import com.github.kotvertolet.contactsapp.data.pojo.ContactGroupItem;
import com.github.kotvertolet.contactsapp.data.source.ContactsDataSource;
import com.github.kotvertolet.contactsapp.data.source.local.LocalContactsDataSource;
import com.github.kotvertolet.contactsapp.data.source.remote.RemoteContactsDataSource;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity implements ContactsContract.View {

    private static final String ITEMS_KEY = "items";
    private static final String CACHE_KEY = "cache";
    private static final String QUERY_KEY = "query";

    private ContactsAdapter mContactsAdapter;
    private ContactsContract.Presenter mContactsPresenter;
    private SearchView mSearchView;
    private ProgressBar mProgressBar;
    private String mRestoredQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView mContactsRecycler = findViewById(R.id.recycler_contacts);
        mProgressBar = findViewById(R.id.progress_bar);
        ContactsDataSource mLocalDataSource = LocalContactsDataSource.getInstance();
        ContactsDataSource mRemoteDataSource = RemoteContactsDataSource.getInstance();
        mContactsPresenter = new ContactsPresenter(this, mRemoteDataSource, mLocalDataSource);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mContactsRecycler.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContactsRecycler.getContext(),
                linearLayoutManager.getOrientation());
        mContactsRecycler.addItemDecoration(dividerItemDecoration);

        if (savedInstanceState != null) {
            List<ContactGroupItem> groupsItems = (List<ContactGroupItem>) savedInstanceState.getSerializable(ITEMS_KEY);
            mContactsAdapter = new ContactsAdapter(groupsItems);
            mContactsRecycler.setAdapter(mContactsAdapter);
            setLoadingIndicator(false);
        } else {
            mContactsAdapter = new ContactsAdapter(new ArrayList<ContactGroupItem>(0));
            mContactsRecycler.setAdapter(mContactsAdapter);
            mContactsPresenter.start();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mContactsAdapter.onSaveInstanceState(outState);
        outState.putSerializable(ITEMS_KEY, (Serializable) mContactsAdapter.getGroups());
        outState.putSerializable(CACHE_KEY, (Serializable) mContactsAdapter.getMCachedGroups());
        if (mSearchView != null && !StringUtils.isEmpty(mSearchView.getQuery())) {
            outState.putString(QUERY_KEY, mSearchView.getQuery().toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mContactsAdapter.onRestoreInstanceState(savedInstanceState);
        mContactsAdapter.setMCachedGroups((List<ContactGroupItem>) savedInstanceState.getSerializable(CACHE_KEY));
        mRestoredQuery = savedInstanceState.getString(QUERY_KEY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setMaxWidth(Integer.MAX_VALUE);
        mSearchView.setQueryHint(getString(R.string.menu_action_search));
        if (mRestoredQuery != null && !mRestoredQuery.isEmpty()) {
            mSearchView.setQuery(mRestoredQuery, false);
            mSearchView.setFocusable(true);
            mSearchView.setIconified(false);
        }
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mContactsAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mContactsAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                mContactsPresenter.loadContacts(true);
                break;
            case R.id.action_search:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!mSearchView.isIconified()) {
            mSearchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void setLoadingIndicator(boolean show) {
        if (show) {
            lockScreenOrientation();
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
            unlockScreenOrientation();
        }
    }

    @Override
    public void showContacts(List<ContactGroupItem> contactGroupList) {
        mContactsAdapter.replaceData(contactGroupList, true);
    }

    @Override
    public void showLoadingContactsError() {
        showMessage(getString(R.string.contacts_loading_error));
    }

    private void showMessage(String message) {
        Snackbar.make(findViewById(R.id.relative_layout_main), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(ContactsContract.Presenter presenter) {
        mContactsPresenter = presenter;
    }

    private void lockScreenOrientation() {
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    private void unlockScreenOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }
}
