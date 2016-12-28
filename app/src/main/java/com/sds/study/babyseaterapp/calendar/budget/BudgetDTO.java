package com.sds.study.babyseaterapp.calendar.budget;

import android.os.Parcel;
import android.os.Parcelable;

public class BudgetDTO implements Parcelable{
    int cost_id;
    String cardCompany;
    String regdate;
    String category;
    String cost;
    String content;

    public int getCost_id() {
        return cost_id;
    }

    public void setCost_id(int cost_id) {
        this.cost_id = cost_id;
    }

    public String getCardCompany() {
        return cardCompany;
    }

    public void setCardCompany(String cardCompany) {
        this.cardCompany = cardCompany;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.cost_id);
        dest.writeString(this.cardCompany);
        dest.writeString(this.regdate);
        dest.writeString(this.category);
        dest.writeString(this.cost);
        dest.writeString(this.content);
    }

    public BudgetDTO() {
    }

    protected BudgetDTO(Parcel in) {
        this.cost_id = in.readInt();
        this.cardCompany = in.readString();
        this.regdate = in.readString();
        this.category = in.readString();
        this.cost = in.readString();
        this.content = in.readString();
    }

    public static final Creator<BudgetDTO> CREATOR = new Creator<BudgetDTO>() {
        @Override
        public BudgetDTO createFromParcel(Parcel source) {
            return new BudgetDTO(source);
        }

        @Override
        public BudgetDTO[] newArray(int size) {
            return new BudgetDTO[size];
        }
    };
}
