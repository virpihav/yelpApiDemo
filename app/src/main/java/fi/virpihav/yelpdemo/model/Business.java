package fi.virpihav.yelpdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Business implements Parcelable {

    private String name;
    private String distance;
    private String rating;
    private String price;
    private List<Category> categories;

    @SerializedName("image_url")
    private String imageUrl;

    protected Business(Parcel in) {
        name = in.readString();
        distance = in.readString();
        rating = in.readString();
        price = in.readString();
        categories = in.createTypedArrayList(Category.CREATOR);
        imageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(distance);
        dest.writeString(rating);
        dest.writeString(price);
        dest.writeTypedList(categories);
        dest.writeString(imageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Business> CREATOR = new Creator<Business>() {
        @Override
        public Business createFromParcel(Parcel in) {
            return new Business(in);
        }

        @Override
        public Business[] newArray(int size) {
            return new Business[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getDistance() {
        String[] split = distance.split("\\.");
        return split[0] + " m";
    }

    public String getRating() {
        return rating;
    }

    public String getPrice() {
        return price;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public String getImageurl() {
        return imageUrl;
    }
}
