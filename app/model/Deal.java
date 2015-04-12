package model;

import java.util.List;
import java.util.Optional;

public class Deal {

    long id;
    public String closeTime;
    public String product;
    public String title;
    public String description;
    public DealPrices prices;
    public String businessName;
    public String categoryId;
    public String scheduledLocationName;
    public Optional<String> finePrint ;
    public List<String> highlights;
    public String emailSubject;
    public int purchaseCount;
    public int soldOrLeftCount;
    public int minLiveDeals;
    public String soldOrLeftText;
    public List<Address> addresses;
    public List<Location> locations;
    public ImageLinks imageLinks;
    public DealConditions conditions;


    public SimpleDeal toSimpleDeal() {
        return new SimpleDeal(
                this.id,
                this. title,
                this. product,
                this. locations);
    }
}
