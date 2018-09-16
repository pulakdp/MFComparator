
package io.github.pulakdp.mfcomparator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

    @SerializedName("data")
    @Expose
    public Data data;

    public static class Data {

        @SerializedName("search_results")
        @Expose
        public List<SearchResult> searchResults;

    }

    public static class SearchResult {

        @SerializedName("category")
        @Expose
        public String category;

        @SerializedName("minimum_investment")
        @Expose
        public Double minimumInvestment;

        @SerializedName("riskometer")
        @Expose
        public String riskometer;

        @SerializedName("details_id")
        @Expose
        public String detailsId;

        @SerializedName("name")
        @Expose
        public String name;
    }
}
