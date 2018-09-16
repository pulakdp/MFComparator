package io.github.pulakdp.mfcomparator.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import io.github.pulakdp.mfcomparator.R;
import io.github.pulakdp.mfcomparator.model.DetailResponse;
import io.github.pulakdp.mfcomparator.rest.PiggyApi;
import io.github.pulakdp.mfcomparator.rest.PiggyApiClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FundDetailViewModel extends BaseObservable {

    public final String rupee = "\u20B9";

    private Context context;

    public String title = "Mutual Funds Plan";
    public String nav = "-";
    public String dateUpdated = "-";
    public String lastYearReturn = "-";
    public String last3YearsReturn = "-";
    public String last5YearsReturn = "-";
    public String schemeType = "-";
    public String schemeCategory = "-";
    public String benchmarkType = "-";
    public String highestReturn = "-";
    public String minSub = "-";
    public String minAddSub = "-";
    public String exitLoad = "-";
    public String expenses = "-";
    public String schemeAum = "-";
    public String amcAum = "-";
    public String riskometer = "-";
    public String objective = "";
    public String suitability = "";

    public boolean showObjective;
    public boolean showSuitability;

    private DetailResponse detailResponse;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private PiggyApi piggyApi = PiggyApiClient.getClient().create(PiggyApi.class);

    public FundDetailViewModel(Context context, String key) {
        this.context = context;
        fetchMFDetails(key);
    }

    private void fetchMFDetails(String key) {
        compositeDisposable.add(piggyApi.getMutualFundDetail(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::detailsFetched
                        , this::detailFetchFailed));
    }

    private void detailsFetched(DetailResponse detailResponse) {
        if (detailResponse == null)
            return;
        this.detailResponse = detailResponse;
        updateValuesAndNotify();
    }

    private void updateValuesAndNotify() {
        title = detailResponse.data.mutualFund.details.name;
        nav = String.valueOf(Math.round(detailResponse.data.mutualFund.nav * 100d) / 100d);
        dateUpdated = "as on" + detailResponse.data.mutualFund.navRefreshDate.split("T")[0];
        lastYearReturn = detailResponse.data.mutualFund.details.yoyReturn + "%";
        last3YearsReturn = detailResponse.data.mutualFund.details.return3yr + "%";
        last5YearsReturn = detailResponse.data.mutualFund.details.return5yr + "%";
        schemeType = detailResponse.data.mutualFund.details.schemeType;
        schemeCategory = detailResponse.data.mutualFund.details.category;
        benchmarkType = detailResponse.data.mutualFund.details.benchmark.name;
        highestReturn = String.valueOf(Math.round(detailResponse.data.mutualFund.bestReturn.percentChange * 100d) / 100d)
                + "%\n(" + detailResponse.data.mutualFund.bestReturn.fromDate + " - " +
                detailResponse.data.mutualFund.bestReturn.toDate + ")";
        minSub = rupee + detailResponse.data.mutualFund.details.minimumSubscription;
        minAddSub = rupee + detailResponse.data.mutualFund.details.minimumAdditionSubscription;
        exitLoad = detailResponse.data.mutualFund.details.exitLoadText.trim();
        expenses = String.valueOf(detailResponse.data.mutualFund.expenseRatio);
        schemeAum = rupee + detailResponse.data.mutualFund.details.assetAum;
        amcAum = rupee + detailResponse.data.mutualFund.details.amc.aum;
        riskometer = detailResponse.data.mutualFund.details.riskometer;
        objective = detailResponse.data.mutualFund.details.objective;
        String[] suitabilityArray = detailResponse.data.mutualFund.details.suitability.split(":");
        StringBuilder builder = new StringBuilder(suitabilityArray[0].replace("*", ":"));
        for (int i = 1; i < suitabilityArray.length; i++) {
            builder.append("\n");
            builder.append("* ");
            builder.append(suitabilityArray[i]);
        }
        suitability = builder.toString();
        notifyChange();
    }

    public void toggleObjective(View view) {
        TextView textView = (TextView) view;
        if (!showObjective) {
            showObjective = true;
            Drawable minus = context.getResources().getDrawable( R.drawable.minus);
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null, minus, null);
        } else {
            showObjective = false;
            Drawable plus = context.getResources().getDrawable( R.drawable.plus);
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null, plus, null);
        }
        notifyChange();
    }

    public void toggleSuitability(View view) {
        TextView textView = (TextView) view;
        if (!showSuitability) {
            showSuitability = true;
            Drawable minus = context.getResources().getDrawable( R.drawable.minus);
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null, minus, null);
        } else {
            showSuitability = false;
            Drawable plus = context.getResources().getDrawable( R.drawable.plus);
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null, plus, null);
        }
        notifyChange();
    }

    private void detailFetchFailed(Throwable t) {
        Toast.makeText(context, "Failed to get details :(\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public void onDestroy() {
        compositeDisposable.dispose();
    }
}
