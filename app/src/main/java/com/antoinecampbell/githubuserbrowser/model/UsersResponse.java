package com.antoinecampbell.githubuserbrowser.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UsersResponse {

    @JsonProperty("total_count")
    private int totalCount;
    private List<User> items;

    public List<User> getItems() {
        return items;
    }

    public void setItems(List<User> items) {
        this.items = items;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
