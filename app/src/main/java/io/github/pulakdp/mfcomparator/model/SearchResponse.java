
package io.github.pulakdp.mfcomparator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

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

        @SerializedName("search_query")
        @Expose
        public String searchQuery;
        @SerializedName("facets")
        @Expose
        public Facets facets;
        @SerializedName("search_results")
        @Expose
        public List<SearchResult> searchResults = null;
        @SerializedName("search_results_count")
        @Expose
        public Integer searchResultsCount;

    }

    public static class SearchResult {

        @SerializedName("category")
        @Expose
        public String category;
        @SerializedName("rating")
        @Expose
        public Integer rating;
        @SerializedName("minimum_investment")
        @Expose
        public Double minimumInvestment;
        @SerializedName("url_name")
        @Expose
        public String urlName;
        @SerializedName("sub_category")
        @Expose
        public String subCategory;
        @SerializedName("scheme_key")
        @Expose
        public String schemeKey;
        @SerializedName("yoy_return")
        @Expose
        public Double yoyReturn;
        @SerializedName("amc")
        @Expose
        public String amc;
        @SerializedName("return_3yr")
        @Expose
        public Double return3yr;
        @SerializedName("return_5yr")
        @Expose
        public Double return5yr;
        @SerializedName("riskometer")
        @Expose
        public String riskometer;
        @SerializedName("details_id")
        @Expose
        public String detailsId;
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;

        @Override
        public String toString() {
            return "SearchResult{" +
                    "category='" + category + '\'' +
                    ", rating=" + rating +
                    ", minimumInvestment=" + minimumInvestment +
                    ", urlName='" + urlName + '\'' +
                    ", subCategory='" + subCategory + '\'' +
                    ", schemeKey='" + schemeKey + '\'' +
                    ", yoyReturn=" + yoyReturn +
                    ", amc='" + amc + '\'' +
                    ", return3yr=" + return3yr +
                    ", return5yr=" + return5yr +
                    ", riskometer='" + riskometer + '\'' +
                    ", detailsId='" + detailsId + '\'' +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static class Facets {

        @SerializedName("category")
        @Expose
        public List<Category> category = null;
        @SerializedName("amc")
        @Expose
        public List<Amc> amc = null;
        @SerializedName("sub_category")
        @Expose
        public List<SubCategory> subCategory = null;
        @SerializedName("riskometer")
        @Expose
        public List<Riskometer> riskometer = null;

    }

    public static class Category {

        @SerializedName("key")
        @Expose
        public String key;
        @SerializedName("doc_count")
        @Expose
        public Integer docCount;

    }

    public static class Amc {

        @SerializedName("key")
        @Expose
        public String key;
        @SerializedName("doc_count")
        @Expose
        public Integer docCount;

    }

    public class SubCategory {

        @SerializedName("key")
        @Expose
        public String key;
        @SerializedName("doc_count")
        @Expose
        public Integer docCount;

    }

    public class Riskometer {

        @SerializedName("key")
        @Expose
        public String key;
        @SerializedName("doc_count")
        @Expose
        public Integer docCount;

    }
}
