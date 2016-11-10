package fi.virpihav.yelpdemo.model;

import java.util.List;

public class SearchResponse {

    private int total;

    private List<Business> businesses;

    public int getTotal() {
        return total;
    }

    public List<Business> getBusinesses() {
        return businesses;
    }
}
