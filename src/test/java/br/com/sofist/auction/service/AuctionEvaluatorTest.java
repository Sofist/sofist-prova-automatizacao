package br.com.sofist.auction.service;

import util.AuctionBuilder;
import br.com.sofist.auction.domain.Auction;
import br.com.sofist.auction.domain.User;
import org.junit.Before;
import org.junit.Test;

/**
 Test class to AuctionEvaluator
 @see br.com.sofist.auction.service.AuctionEvaluator
 **/
public class AuctionEvaluatorTest {
	
	private AuctionEvaluator evaluator;
	private User anna;
	private User joseph;
	private User james;

	@Before
	public void setUp() {
		this.evaluator = new AuctionEvaluator();
		this.james = new User("James");
		this.joseph = new User("Joseph");
		this.anna = new User("Anna");
	}
	
	@Test(expected=RuntimeException.class)
	public void shouldNotEvaluateAuctionWithoutBid() {
		Auction auction = new AuctionBuilder().to("Game of Thrones Box").build();
		
		evaluator.evaluate(auction);
	}

    //TODO more tests

     
}
