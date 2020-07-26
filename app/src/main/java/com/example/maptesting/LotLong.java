package com.example.maptesting;

class LotLong {

    double lag_point;
    double long_point;
    String title;

    public LotLong(double lag_point, double long_point, String title) {
        this.lag_point = lag_point;
        this.long_point = long_point;
        this.title = title;
    }

    public double getLag_point() {
        return lag_point;
    }

    public void setLag_point(double lag_point) {
        this.lag_point = lag_point;
    }

    public double getLong_point() {
        return long_point;
    }

    public void setLong_point(double long_point) {
        this.long_point = long_point;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
