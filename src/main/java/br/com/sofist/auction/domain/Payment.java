package br.com.sofist.auction.domain;

import java.util.Date;

public class Payment {

    private int id;
    private double value;
	private Date date;
    private final int bidId;

    public Payment(double value, Date date, int bidId) {
        this.value = value;
		this.date = date;
        this.bidId = bidId;
    }
	public double getValue() {
		return value;
	}
	public Date getDate() {
		return date;
	}
    public int getBidId() {
        return bidId;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
