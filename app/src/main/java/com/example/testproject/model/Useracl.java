package com.example.testproject.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Useracl")
 public class Useracl {

    @PrimaryKey(autoGenerate = true)
    public int dbId;

    public String id;
    public String uniqueId;
    public String userName;
    public ContentAndQueryAccess contentAndQueryAccess;

    public class ContentAndQueryAccess{

        public boolean hasCreateEdit() {
            return createEdit!=null && createEdit.equals("Yes");
        }

        public boolean hasBypassContent() {
            return bypassContent!=null && bypassContent.equals("Yes");
        }

        public boolean hasManage() {
            return manage!=null && manage.equals("Yes");
        }

        public boolean hasTranslateReview() {
            return translateReview!=null && translateReview.equals("Yes");
        }

        public boolean hasUpload() {
            return upload!=null && upload.equals("Yes");
        }

        public boolean hasQueryEdit() {
            return queryEdit!=null && queryEdit.equals("Yes");
        }

        public boolean hasDelete() {
            return delete!=null && delete.equals("Yes");
        }

        public boolean hasReview() {
            return review!=null && review.equals("Yes");
        }

        public boolean hasTranslate() {
            return translate!=null && translate.equals("Yes");
        }

        public boolean hasDisseminate() {
            return disseminate!=null && disseminate.equals("Yes");
        }

        public boolean hasSearch() {
            return search!=null && search.equals("Yes");
        }

        public  String createEdit;
        public  String bypassContent;
        public  String manage;
        public  String translateReview;
        public  String upload;
        public  String queryEdit;
        public  String delete;
        public  String review;
        public  String translate;
        public  String disseminate;
        public  String search;
    }
}



