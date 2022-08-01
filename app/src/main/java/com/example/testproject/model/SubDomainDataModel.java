package com.example.testproject.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class SubDomainDataModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("knowledgeDomain")
    @Expose
    private KnowledgeDomainDataModel knowledgeDomain;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KnowledgeDomainDataModel getKnowledgeDomain() {
        return knowledgeDomain;
    }

    public void setKnowledgeDomain(KnowledgeDomainDataModel knowledgeDomain) {
        this.knowledgeDomain = knowledgeDomain;
    }

}
