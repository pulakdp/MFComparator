package io.github.pulakdp.mfcomparator.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.pulakdp.mfcomparator.databinding.FundItemLayoutBinding;
import io.github.pulakdp.mfcomparator.model.SearchResponse;
import io.github.pulakdp.mfcomparator.viewmodel.FundItemViewModel;

public class FundsAdapter extends RecyclerView.Adapter<FundsAdapter.FundViewHolder> {

    private Context context;
    private List<SearchResponse.SearchResult> data;

    public FundsAdapter(Context context) {
        this.context = context;
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

    public class FundViewHolder extends RecyclerView.ViewHolder {

        private FundItemLayoutBinding binding;

        public FundViewHolder(FundItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public FundItemLayoutBinding getBinding() {
            return binding;
        }
    }

}
