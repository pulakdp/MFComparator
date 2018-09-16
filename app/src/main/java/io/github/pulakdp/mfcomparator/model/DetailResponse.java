
package io.github.pulakdp.mfcomparator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailResponse {

    @SerializedName("status-message")
    @Expose
    public String statusMessage;
    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("status-code")
    @Expose
    public Integer statusCode;

    public static class Data {

        @SerializedName("holdings")
        @Expose
        public Holdings holdings;
        @SerializedName("funds")
        @Expose
        public List<Fund> funds = null;
        @SerializedName("mutual_fund")
        @Expose
        public MutualFund mutualFund;
        @SerializedName("user")
        @Expose
        public User user;
        @SerializedName("block_message")
        @Expose
        public String blockMessage;
        @SerializedName("can_invest")
        @Expose
        public Integer canInvest;
        @SerializedName("is_tax_saving_fund")
        @Expose
        public Boolean isTaxSavingFund;

    }

    public static class Holdings {}

    public static class Fund {}

    public static class User {}

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

        @SerializedName("cams_code")
        @Expose
        public String camsCode;
        @SerializedName("last_day_date")
        @Expose
        public String lastDayDate;
        @SerializedName("date")
        @Expose
        public String date;
        @SerializedName("dividend_type")
        @Expose
        public String dividendType;
        @SerializedName("nav_refresh_date")
        @Expose
        public String navRefreshDate;
        @SerializedName("best_return")
        @Expose
        public BestReturn bestReturn;
        @SerializedName("money_control_code")
        @Expose
        public String moneyControlCode;
        @SerializedName("nav")
        @Expose
        public Double nav;
        @SerializedName("details")
        @Expose
        public Details details;
        @SerializedName("key")
        @Expose
        public String key;
        @SerializedName("last_day_nav")
        @Expose
        public Double lastDayNav;
        @SerializedName("nav_change_percentage")
        @Expose
        public Double navChangePercentage;
        @SerializedName("launch_date")
        @Expose
        public String launchDate;
        @SerializedName("dividend_type_text")
        @Expose
        public String dividendTypeText;
        @SerializedName("scheme_id")
        @Expose
        public String schemeId;
        @SerializedName("expense_ratio")
        @Expose
        public Double expenseRatio;

    }

    public static class Details {

        @SerializedName("rating")
        @Expose
        public Integer rating;
        @SerializedName("minimum_subscription")
        @Expose
        public Double minimumSubscription;
        @SerializedName("minimum_balance_maintainence")
        @Expose
        public Double minimumBalanceMaintainence;
        @SerializedName("asset_aum")
        @Expose
        public String assetAum;
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("multiple_subscription")
        @Expose
        public Double multipleSubscription;
        @SerializedName("category")
        @Expose
        public String category;
        @SerializedName("url_name")
        @Expose
        public String urlName;
        @SerializedName("benchmark_text")
        @Expose
        public String benchmarkText;
        @SerializedName("riskometer")
        @Expose
        public String riskometer;
        @SerializedName("sid")
        @Expose
        public Object sid;
        @SerializedName("objective")
        @Expose
        public String objective;
        @SerializedName("minimum_swp_withdrawal")
        @Expose
        public Double minimumSwpWithdrawal;
        @SerializedName("minimum_redemption")
        @Expose
        public Double minimumRedemption;
        @SerializedName("exit_load")
        @Expose
        public Double exitLoad;
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
        @SerializedName("scheme_details_id")
        @Expose
        public String schemeDetailsId;
        @SerializedName("benchmark")
        @Expose
        public Benchmark benchmark;
        @SerializedName("value_research_rank")
        @Expose
        public Object valueResearchRank;
        @SerializedName("suitability")
        @Expose
        public String suitability;
        @SerializedName("multiple_redemption")
        @Expose
        public Double multipleRedemption;
        @SerializedName("scheme_type")
        @Expose
        public String schemeType;
        @SerializedName("is_elss")
        @Expose
        public Boolean isElss;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("scheme_class")
        @Expose
        public String schemeClass;
        @SerializedName("return_5yr")
        @Expose
        public Double return5yr;
        @SerializedName("multiple_addition_subscription")
        @Expose
        public Double multipleAdditionSubscription;
        @SerializedName("sid_url")
        @Expose
        public String sidUrl;
        @SerializedName("minimum_sip_subscription")
        @Expose
        public Double minimumSipSubscription;

    }

    public static class Amc {

        @SerializedName("fund")
        @Expose
        public String fund;
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
