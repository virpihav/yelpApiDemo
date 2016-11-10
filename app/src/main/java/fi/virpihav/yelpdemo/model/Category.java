package fi.virpihav.yelpdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {
    private String alias;
    private String title;

    protected Category(Parcel in) {
        alias = in.readString();
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(alias);
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public String getAlias() {
        return alias;
    }

    public String getTitle() {
        return title;
    }
}
