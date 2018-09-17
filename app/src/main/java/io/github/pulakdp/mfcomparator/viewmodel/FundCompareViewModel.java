package io.github.pulakdp.mfcomparator.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.github.pulakdp.mfcomparator.model.DetailResponse;
import io.github.pulakdp.mfcomparator.rest.PiggyApi;
import io.github.pulakdp.mfcomparator.rest.PiggyApiClient;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FundCompareViewModel extends BaseObservable {

    private static final String rupee = "\u20B9";

    private Context context;
    private List<DetailResponse> detailResponseList = new ArrayList<>();

    public String[] title = new String[]{"Mutual Funds Plan 1", "Mutual Funds Plan 2"};
    public String[] nav = new String[]{"-", "-"};
    public String[] returns = new String[]{"-", "-"};
    public String[] schemeType = new String[]{"-", "-"};
    public String[] schemeCategory = new String[]{"-", "-"};
    public String[] benchmarkType = new String[]{"-", "-"};
    public String[] highestReturn = new String[]{"-", "-"};
    public String[] minSub = new String[]{"-", "-"};
    public String[] minAddSub = new String[]{"-", "-"};
    public String[] exitLoad = new String[]{"-", "-"};
    public String[] expenses = new String[]{"-", "-"};
    public String[] schemeAum = new String[]{"-", "-"};
    public String[] amcAum = new String[]{"-", "-"};
    public String[] riskometer = new String[]{"-", "-"};

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private PiggyApi piggyApi = PiggyApiClient.getClient().create(PiggyApi.class);

    public FundCompareViewModel(Context context, String key1, String key2) {
        this.context = context;
        fetchDetailsForComparison(key1, key2);
    }

    private void fetchDetailsForComparison(String key1, String key2) {
        compositeDisposable.add(Observable.combineLatest(piggyApi.getMutualFundDetail(key1),
                piggyApi.getMutualFundDetail(key2), this::saveResponse)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::detailsFetched
                        , this::detailFetchFailed));
    }

    private boolean saveResponse(DetailResponse detailResponse1, DetailResponse detailResponse2) {
        detailResponseList.add(detailResponse1);
        detailResponseList.add(detailResponse2);
        return true;
    }

    private void detailsFetched(boolean callSuccess) {
        if (callSuccess) {
            updateValuesAndNotify();
        }
    }

    private void updateValuesAndNotify() {
        if (detailResponseList.size() != 2) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < detailResponseList.size(); i++) {
            title[i] = detailResponseList.get(i).data.mutualFund.details.name;
            nav[i] = rupee + String.valueOf(Math.round(detailResponseList.get(i).data.mutualFund.nav * 100d) / 100d) +
                    "\nas on " + detailResponseList.get(i).data.mutualFund.navRefreshDate.split("T")[0];
            returns[i] = "LY: " + detailResponseList.get(i).data.mutualFund.details.yoyReturn + "%" +
                    "\nL3Y: " + detailResponseList.get(i).data.mutualFund.details.return3yr + "%" +
                    "\nL5Y: " + detailResponseList.get(i).data.mutualFund.details.return5yr + "%";
            schemeType[i] = detailResponseList.get(i).data.mutualFund.details.schemeType;
            schemeCategory[i] = detailResponseList.get(i).data.mutualFund.details.category;
            benchmarkType[i] = detailResponseList.get(i).data.mutualFund.details.benchmark.name;
            highestReturn[i] =
                    String.valueOf(Math.round(detailResponseList.get(i).data.mutualFund.bestReturn.percentChange * 100d) / 100d)
                            + "%\n(" + detailResponseList.get(i).data.mutualFund.bestReturn.fromDate + " - " +
                            detailResponseList.get(i).data.mutualFund.bestReturn.toDate + ")";
            minSub[i] = rupee + detailResponseList.get(i).data.mutualFund.details.minimumSubscription;
            minAddSub[i] = rupee + detailResponseList.get(i).data.mutualFund.details.minimumAdditionSubscription;
            exitLoad[i] = detailResponseList.get(i).data.mutualFund.details.exitLoadText
                    .trim().replace("Exit load of ", "");
            expenses[i] = String.valueOf(detailResponseList.get(i).data.mutualFund.expenseRatio);
            schemeAum[i] = rupee + detailResponseList.get(i).data.mutualFund.details.assetAum;
            amcAum[i] = rupee + detailResponseList.get(i).data.mutualFund.details.amc.aum;
            riskometer[i] = detailResponseList.get(i).data.mutualFund.details.riskometer;
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
