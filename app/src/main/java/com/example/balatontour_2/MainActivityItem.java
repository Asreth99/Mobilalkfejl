package com.example.balatontour_2;

public class MainActivityItem {
    private String eventDate;
    private String eventDate2;
    private String eventDateHypen;
    private String eventTitle;
    private int imageResource;

    public MainActivityItem() {
    }

    public MainActivityItem(String eventDate, String eventDate2, String eventDateHypen, String eventTitle, int imageResource) {
        this.eventDate = eventDate;
        this.eventDate2 = eventDate2;
        this.eventDateHypen = eventDateHypen;
        this.eventTitle = eventTitle;
        this.imageResource = imageResource;
    }

    public String getEventDate2() {
        return eventDate2;
    }

    public String getEventDateHypen() {
        return eventDateHypen;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public int getImageResource() {
        return imageResource;
    }
}
