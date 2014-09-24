package br.com.sofist.auction.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Auction {

	private String description;
	private Calendar date;
	private List<Bid> bids;
	private boolean closed;
	private int id;
	
	public Auction(String description) {
		this(description, Calendar.getInstance());
	}
	
	public Auction(String description, Calendar date) {
		this.description = description;
		this.date = date;
		this.bids = new ArrayList<Bid>();
	}
	
	public void propose(Bid auction) {
		if(bids.isEmpty() || canGiveBid(auction.getUser())) {
			bids.add(auction);
		}
	}

	private boolean canGiveBid(User user) {
		return !lastBidGiven().getUser().equals(user) && bidsByUser(user) <5;
	}

	private int bidsByUser(User user) {
		int total = 0;
		for(Bid bid : bids) {
			if(bid.getUser().equals(user)) total++;
		}
		return total;
	}

	private Bid lastBidGiven() {
		return bids.get(bids.size()-1);
	}

	public String getDescription() {
		return description;
	}

	public List<Bid> getBids() {
		return Collections.unmodifiableList(bids);
	}

	public Calendar getDate() {
		return (Calendar) date.clone();
	}

	public void close() {
		this.closed = true;
	}
	
	public boolean isClosed() {
		return closed;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
