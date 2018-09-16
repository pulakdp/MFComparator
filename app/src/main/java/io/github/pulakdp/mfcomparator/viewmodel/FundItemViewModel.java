package io.github.pulakdp.mfcomparator.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.text.TextUtils;
import android.widget.Toast;

import io.github.pulakdp.mfcomparator.ui.MFDetailActivity;
import io.github.pulakdp.mfcomparator.model.SearchResponse;

public class FundItemViewModel extends BaseObservable {

    private Context context;
    private SearchResponse.SearchResult searchResult;

    public String title;
    public String desc;

    public FundItemViewModel(Context context, SearchResponse.SearchResult searchResult) {
        this.context = context;
        this.searchResult = searchResult;

        title = searchResult.name;

        StringBuilder builder = new StringBuilder();
        builder.append(searchResult.category.substring(0, 1).toUpperCase());
        builder.append(searchResult.category.substring(1).toLowerCase());
        builder.append(" | ");
        builder.append("Min Inv. \u20B9 ");
        builder.append(searchResult.minimumInvestment);
        builder.append(" | ");
        builder.append("Risk: ");
        builder.append(searchResult.riskometer);
        desc = builder.toString();

        notifyChange();
    }

    public void openDetailsActivity() {
        if (searchResult.detailsId == null || TextUtils.isEmpty(searchResult.detailsId)) {
            Toast.makeText(context, "Key information missing!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent starter = new Intent(context, MFDetailActivity.class);
        starter.putExtra("key", searchResult.detailsId);
        context.startActivity(starter);
    }
}
