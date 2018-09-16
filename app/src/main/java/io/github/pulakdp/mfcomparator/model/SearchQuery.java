package io.github.pulakdp.mfcomparator.model;

public class SearchQuery {
    public String search;
    public int rows;
    public int offset;

    public SearchQuery(String search, int rows, int offset) {
        this.search = search;
        this.rows = rows;
        this.offset = offset;
    }
}
