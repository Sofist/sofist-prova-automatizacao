package util;

import br.com.sofist.auction.domain.Auction;
import br.com.sofist.auction.domain.Bid;
import br.com.sofist.auction.domain.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AuctionBuilder {

	private String description;
	private Calendar date;
	private List<Bid> bids;
	private boolean closed;

	public AuctionBuilder() {
		this.date = Calendar.getInstance();
		bids = new ArrayList<Bid>();
	}
	
	public AuctionBuilder to(String description) {
		this.description = description;
		return this;
	}
	
	public AuctionBuilder onDate(Calendar date) {
		this.date = date;
		return this;
	}

	public AuctionBuilder bid(User user, double value) {
		bids.add(new Bid(user, value));
		return this;
	}

	public AuctionBuilder closed() {
		this.closed = true;
		return this;
	}

	public Auction build() {
		Auction auction = new Auction(description, date);
		for(Bid bidGiven : bids) auction.propose(bidGiven);
		if(closed) auction.close();
				
		return auction;
	}

}
