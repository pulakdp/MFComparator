package io.github.pulakdp.mfcomparator.ui;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import io.github.pulakdp.mfcomparator.R;
import io.github.pulakdp.mfcomparator.adapter.FundsAdapter;
import io.github.pulakdp.mfcomparator.databinding.ActivityMainBinding;
import io.github.pulakdp.mfcomparator.model.SearchQuery;
import io.github.pulakdp.mfcomparator.model.SearchResponse;
import io.github.pulakdp.mfcomparator.rest.PiggyApi;
import io.github.pulakdp.mfcomparator.rest.PiggyApiClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener, FundsAdapter.TwoSelectionListener {

    private static final int PAGE_SIZE = 20;

    private int currentPage = 1;
    private String query;

    private MenuItem searchItem;
    private SearchView searchView;
    private FundsAdapter adapter;
    private LinearLayoutManager layoutManager;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private PiggyApi piggyApi = PiggyApiClient.getClient().create(PiggyApi.class);

    public ObservableBoolean showEmptyText = new ObservableBoolean(true);
    public ObservableBoolean isLoading = new ObservableBoolean(false);
    private boolean isLastPage;
    private boolean searchFailed;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setAct(this);
        setSupportActionBar(binding.toolbar);
        initRecyclerView();
    }

    private void initRecyclerView() {
        adapter = new FundsAdapter(this, this);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.mfList.setLayoutManager(layoutManager);
        binding.mfList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.mfList.setAdapter(adapter);
        binding.mfList.addOnScrollListener(onScrollListener);
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0) {
                binding.searchFab.hide();
            } else if (dy < 0) {
                binding.searchFab.show();
            }
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!isLoading.get() && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    loadMoreItems();
                }
            }
        }
    };

    private void loadMoreItems() {
        fetchResults(query, ++currentPage);
    }

    private View.OnClickListener fabSearchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (searchView.isIconified())
                searchItem.expandActionView();
            else {
                searchView.setQuery(searchView.getQuery(), true);
            }
        }
    };

    private View.OnClickListener fabCompareClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            adapter.openCompareActivity(adapter.selectedKeys.get(0), adapter.selectedKeys.get(1));
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        searchItem = menu.findItem(R.id.action_search);

        searchView = (SearchView) searchItem.getActionView();
        binding.searchFab.setOnClickListener(fabSearchClickListener);
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return true;
            }
        });
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    private void fetchResults(@NonNull String query, int pageNumber) {
        compositeDisposable.add(piggyApi.searchMutualFunds(
                new SearchQuery(query, PAGE_SIZE, PAGE_SIZE * (pageNumber - 1)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processResults
                        , this::searchFailed));
    }

    private void processResults(SearchResponse searchResponse) {
        isLoading.set(false);
        if (searchResponse != null) {
            if (searchResponse.data.searchResults.size() == 0) {
                if (currentPage != 1)
                    return;
                Toast.makeText(this, "No results to show", Toast.LENGTH_SHORT).show();
                return;
            }
            if (currentPage == 1)
                adapter.clearData();
            showEmptyText.set(false);
            adapter.addAll(searchResponse.data.searchResults);
            if (searchResponse.data.searchResults.size() < PAGE_SIZE)
                isLastPage = true;
        }
    }

    private void searchFailed(Throwable t) {
        searchFailed = true;
        adapter.clearData();
        isLoading.set(false);
        showEmptyText.set(true);
        Toast.makeText(this, "Search Failed :(\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (!searchFailed && query.equals(this.query))
            return false;
        currentPage = 1;
        showEmptyText.set(false);
        isLoading.set(true);
        hideSoftKeyboard(this);
        fetchResults(query, currentPage);
        this.query = query;
        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        return false;
    }

    public void hideSoftKeyboard(@Nullable Activity activity) {
        if (activity != null) {
            View currentFocus = activity.getCurrentFocus();
            if (currentFocus != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                }
            }
        }
        if (searchView != null)
            searchView.clearFocus();
    }

    @Override
    public void twoItemsSelected(boolean twoItems) {
        if (twoItems) {
            binding.searchFab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_compare_white_24dp));
            binding.searchFab.setOnClickListener(fabCompareClickListener);
            binding.searchFab.hide();
            binding.searchFab.show();
        } else {
            binding.searchFab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_search_white_24dp));
            binding.searchFab.setOnClickListener(fabSearchClickListener);
            binding.searchFab.hide();
            binding.searchFab.show();
        }
    }
}
