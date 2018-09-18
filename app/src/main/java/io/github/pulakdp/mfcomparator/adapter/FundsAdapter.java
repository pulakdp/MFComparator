package io.github.pulakdp.mfcomparator.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

import java.util.ArrayList;
import java.util.List;

import io.github.pulakdp.mfcomparator.R;
import io.github.pulakdp.mfcomparator.databinding.FundItemLayoutBinding;
import io.github.pulakdp.mfcomparator.model.SearchResponse;
import io.github.pulakdp.mfcomparator.ui.CompareActivity;
import io.github.pulakdp.mfcomparator.viewmodel.FundItemViewModel;

public class FundsAdapter extends RecyclerView.Adapter<FundsAdapter.FundViewHolder> {

    private Context context;
    private List<SearchResponse.SearchResult> data;
    private MultiSelector multiSelector = new MultiSelector();

    private int itemsSelected = 0;
    public List<String> selectedKeys = new ArrayList<>();

    private TwoSelectionListener twoSelectionListener;

    public interface TwoSelectionListener {
        void twoItemsSelected(boolean twoItems);
    }

    public void openCompareActivity(String detailsId1, String detailsId2) {
        if (detailsId1 == null || detailsId2 == null
                || TextUtils.isEmpty(detailsId1) || TextUtils.isEmpty(detailsId2)) {
            Toast.makeText(context, "Key information missing!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent starter = new Intent(context, CompareActivity.class);
        starter.putExtra("key1", detailsId1);
        starter.putExtra("key2", detailsId2);
        context.startActivity(starter);
    }

    private ModalMultiSelectorCallback actionModeCallback = new ModalMultiSelectorCallback(multiSelector) {
        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (menuItem.getItemId() == R.id.action_compare) {
                if (itemsSelected < 2) {
                    Toast.makeText(context, "Make 2 selections before proceeding", Toast.LENGTH_SHORT).show();
                    return true;
                }
                actionMode.finish();

                //Launch compare activity
                openCompareActivity(selectedKeys.get(0), selectedKeys.get(1));

                multiSelector.clearSelections();
                return true;

            }
            return false;
        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            super.onCreateActionMode(actionMode, menu);
            ((AppCompatActivity) context).getMenuInflater().inflate(R.menu.list_context_menu, menu);
            actionMode.setTitle("Select 2 items");
            selectedKeys.clear();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            super.onDestroyActionMode(actionMode);
            itemsSelected = 0;
            twoSelectionListener.twoItemsSelected(false);
        }
    };

    public FundsAdapter(Context context, TwoSelectionListener twoSelectionListener) {
        this.context = context;
        this.twoSelectionListener = twoSelectionListener;
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public FundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FundItemLayoutBinding itemBinding =
                FundItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false);
        return new FundViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FundViewHolder fundViewHolder, int position) {
        SearchResponse.SearchResult searchResult = data.get(position);
        fundViewHolder.getBinding().setVm(new FundItemViewModel(context, searchResult));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void addAll(List<SearchResponse.SearchResult> newData) {
        data.addAll(newData);
        notifyDataSetChanged();
    }

    public void clearData() {
        data.clear();
        notifyDataSetChanged();
    }

    public class FundViewHolder extends SwappingHolder implements View.OnClickListener {

        private FundItemLayoutBinding binding;

        public FundViewHolder(FundItemLayoutBinding binding) {
            super(binding.getRoot(), multiSelector);
            this.binding = binding;
            getBinding().getRoot().setOnClickListener(this);
        }

        public FundItemLayoutBinding getBinding() {
            return binding;
        }

        @Override
        public void onClick(View v) {
            if (!multiSelector.isSelectable()) {
                ((AppCompatActivity) context).startSupportActionMode(actionModeCallback);
                multiSelector.setSelectable(true);
            }
            if (multiSelector.isSelected(getAdapterPosition(), 0)) {
                multiSelector.setSelected(FundViewHolder.this, false);
                --itemsSelected;
                twoSelectionListener.twoItemsSelected(false);
                selectedKeys.remove(data.get(getAdapterPosition()).detailsId);
            } else {
                if (itemsSelected == 2) {
                    Toast.makeText(context, "Can't make more than 2 selections", Toast.LENGTH_SHORT).show();
                    return;
                }
                multiSelector.setSelected(FundViewHolder.this, true);
                if (++itemsSelected == 2) {
                    twoSelectionListener.twoItemsSelected(true);
                }
                selectedKeys.add(data.get(getAdapterPosition()).detailsId);
            }
        }
    }

}
