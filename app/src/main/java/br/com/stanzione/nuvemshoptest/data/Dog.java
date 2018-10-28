package br.com.stanzione.nuvemshoptest.data;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Dog {

    @SerializedName("id")
    private String id;
    @SerializedName("url")
    private String url;
    @SerializedName("sub_id")
    private String subId;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("original_filename")
    private String originalFilename;
    @SerializedName("breeds")
    private List<Breed> breedList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public List<Breed> getBreedList() {
        return breedList;
    }

    public void setBreedList(List<Breed> breedList) {
        this.breedList = breedList;
    }
}
