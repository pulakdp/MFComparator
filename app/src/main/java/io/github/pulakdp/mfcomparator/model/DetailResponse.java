
package io.github.pulakdp.mfcomparator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailResponse {

    @SerializedName("data")
    @Expose
    public Data data;

    public static class Data {
        @SerializedName("mutual_fund")
        @Expose
        public MutualFund mutualFund;
    }

    public static class BestReturn {
        @SerializedName("todate")
        @Expose
        public String toDate;
        @SerializedName("percent_change")
        @Expose
        public Double percentChange;
        @SerializedName("fromdate")
        @Expose
        public String fromDate;
    }

    public static class MutualFund {

        @SerializedName("date")
        @Expose
        public String date;

        @SerializedName("nav_refresh_date")
        @Expose
        public String navRefreshDate;

        @SerializedName("best_return")
        @Expose
        public BestReturn bestReturn;

        @SerializedName("nav")
        @Expose
        public Double nav;

        @SerializedName("details")
        @Expose
        public Details details;

        @SerializedName("expense_ratio")
        @Expose
        public Double expenseRatio;
    }

    public static class Details {

        @SerializedName("minimum_subscription")
        @Expose
        public Double minimumSubscription;

        @SerializedName("asset_aum")
        @Expose
        public String assetAum;
        @SerializedName("id")
        @Expose
        public Integer id;

        @SerializedName("category")
        @Expose
        public String category;

        @SerializedName("riskometer")
        @Expose
        public String riskometer;

        @SerializedName("objective")
        @Expose
        public String objective;

        @SerializedName("return_3yr")
        @Expose
        public Double return3yr;

        @SerializedName("exit_load_text")
        @Expose
        public String exitLoadText;

        @SerializedName("yoy_return")
        @Expose
        public Double yoyReturn;

        @SerializedName("minimum_addition_subscription")
        @Expose
        public Double minimumAdditionSubscription;

        @SerializedName("amc")
        @Expose
        public Amc amc;

        @SerializedName("benchmark")
        @Expose
        public Benchmark benchmark;

        @SerializedName("suitability")
        @Expose
        public String suitability;

        @SerializedName("scheme_type")
        @Expose
        public String schemeType;

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("return_5yr")
        @Expose
        public Double return5yr;
    }

    public static class Amc {
        @SerializedName("aum")
        @Expose
        public Double aum;

        @SerializedName("id")
        @Expose
        public Integer id;

        @SerializedName("name")
        @Expose
        public String name;
    }

    public static class Benchmark {

        @SerializedName("name")
        @Expose
        public String name;
    }
}
