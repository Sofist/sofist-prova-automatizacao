package br.com.sofist.auction.domain;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 Test class to Auction
 @see br.com.sofist.auction.domain.Auction
 **/
public class AuctionTest {

	@Test
	public void shouldReceiveABid() {
		Auction auction = new Auction("Xbox One");
		assertEquals(0, auction.getBids().size());
		
		auction.propose(new Bid(new User("Bill Gates"), 2000));
		
		assertEquals(1, auction.getBids().size());
		assertEquals(2000, auction.getBids().get(0).getValue(), 0.00001);
	}


    //TODO more tests

}
