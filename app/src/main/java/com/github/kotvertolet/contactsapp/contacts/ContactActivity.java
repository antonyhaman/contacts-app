package com.github.kotvertolet.contactsapp.contacts;

import android.app.SearchManager;
import android.content.Context;
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
import com.github.kotvertolet.contactsapp.data.pojo.GroupsItem;
import com.github.kotvertolet.contactsapp.data.source.ContactsDataSource;
import com.github.kotvertolet.contactsapp.data.source.ContactsRepository;
import com.github.kotvertolet.contactsapp.data.source.remote.RemoteContactsDataSource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity implements ContactsContract.View {

    private static final String ITEMS_KEY = "items";
    private static final String CACHE_KEY = "cache";
    private static final String QUERY_KEY = "query";

    private ContactsAdapter mContactsAdapter;
    private ContactsDataSource mContactsDataSource;
    private ContactsRepository mContactsRepository;
    private ContactsContract.Presenter mContactsPresenter;
    private RecyclerView mContactsRecycler;
    private SearchView mSearchView;
    private ProgressBar mProgressBar;
    private String mRestoredQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContactsRecycler = findViewById(R.id.recycler_contacts);
        mProgressBar = findViewById(R.id.progress_bar);
        mContactsDataSource = RemoteContactsDataSource.getInstance();
        mContactsRepository = ContactsRepository.getInstance(mContactsDataSource);
        new ContactsPresenter(this, mContactsRepository);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mContactsRecycler.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContactsRecycler.getContext(),
                linearLayoutManager.getOrientation());
        mContactsRecycler.addItemDecoration(dividerItemDecoration);

        if (savedInstanceState != null) {
            List<GroupsItem> groupsItems = (List<GroupsItem>) savedInstanceState.getSerializable(ITEMS_KEY);
            mContactsAdapter = new ContactsAdapter(groupsItems);
            mContactsRecycler.setAdapter(mContactsAdapter);
            setLoadingIndicator(false);
        } else {
            mContactsAdapter = new ContactsAdapter(new ArrayList<GroupsItem>(0));
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
        outState.putString(QUERY_KEY, mSearchView.getQuery().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mContactsAdapter.onRestoreInstanceState(savedInstanceState);
        mContactsAdapter.setMCachedGroups((List<GroupsItem>) savedInstanceState.getSerializable(CACHE_KEY));
        mRestoredQuery = savedInstanceState.getString(QUERY_KEY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setMaxWidth(Integer.MAX_VALUE);
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
                mContactsPresenter.start();
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
        if (show) mProgressBar.setVisibility(View.VISIBLE);
        else mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showContacts(List<GroupsItem> contactGroupList) {
        mContactsAdapter.replaceData(contactGroupList);
    }

    @Override
    public void showLoadingContactsError() {
        setLoadingIndicator(false);
        showMessage(getString(R.string.contacts_loading_error));
    }

    private void showMessage(String message) {
        Snackbar.make(findViewById(R.id.relative_layout_main), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(ContactsContract.Presenter presenter) {
        mContactsPresenter = presenter;
    }
}
