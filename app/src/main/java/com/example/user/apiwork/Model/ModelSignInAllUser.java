package com.example.user.apiwork.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelSignInAllUser {

    @SerializedName("page")
    @Expose
    private Integer page;

    @SerializedName("per_page")
    @Expose
    private Integer perPage;

    @SerializedName("total")
    @Expose
    private Integer total;

    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    @SerializedName("data")
    @Expose
    private List<ModelSignIn> data;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<ModelSignIn> getData() {
        return data;
    }

    public void setData(List<ModelSignIn> data) {
        this.data = data;
    }

}
