package br.com.sofist.auction.service;

import br.com.sofist.auction.domain.Auction;
import br.com.sofist.auction.domain.Bid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AuctionEvaluator {

	private Bid greaterThanAll ;
	private Bid lessThanAll;
	private List<Bid> greatest;

	public void evaluate(Auction auction) {
		
		if(auction.getBids().size() == 0) {
			throw new RuntimeException("Isn't possible to evaluate an auction without bids!");
		}
		
		for(Bid bid : auction.getBids()) {
			if(bid.getValue() > greaterThanAll.getValue()) greaterThanAll = bid;
			if (bid.getValue() < lessThanAll.getValue()) lessThanAll = bid;
		}
		
		threeGreatest(auction);
	}

	private void threeGreatest(Auction auction) {
		greatest = new ArrayList<Bid>(auction.getBids());
		Collections.sort(greatest, new Comparator<Bid>() {

			public int compare(Bid o1, Bid o2) {
				if(o1.getValue() < o2.getValue()) return 1;
				if(o1.getValue() > o2.getValue()) return -1;
				return 0;
			}
		});
		greatest = greatest.subList(0, greatest.size() > 3 ? 3 : greatest.size());
	}

	public List<Bid> getThreeGreatest() {
		return greatest;
	}
	
	public Bid getGreatestBid() {
		return greaterThanAll;
	}
	
	public Bid getLesserBid() {
		return lessThanAll;
	}
}
