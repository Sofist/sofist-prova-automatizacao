package br.com.sofist.auction.service;

import br.com.sofist.auction.domain.Auction;
import br.com.sofist.auction.domain.Payment;
import br.com.sofist.auction.infra.Calendar;
import br.com.sofist.auction.infra.dao.AuctionDAO;
import br.com.sofist.auction.infra.dao.PaymentDAO;
import br.com.sofist.auction.infra.email.*;

import java.util.Date;
import java.util.List;

public class PaymentGenerator {
    private final PaymentDAO paymentDAO;
    private final AuctionDAO auctionDAO;
    private final AuctionEvaluator auctionEvaluator;
    private final Notifier auctionNotifier;
    private final Calendar calendar;

    public PaymentGenerator(AuctionDAO auctionDAO,
            PaymentDAO paymentDAO,
            AuctionEvaluator auctionEvaluator, Notifier auctionNotifier, Calendar calendar) {
        this.auctionDAO = auctionDAO;
        this.paymentDAO = paymentDAO;
        this.auctionEvaluator = auctionEvaluator;
        this.auctionNotifier = auctionNotifier;
        this.calendar = calendar;
    }

    public void generate() {
        List<Auction> closedAuctions = auctionDAO.closed();
        for(Auction auction : closedAuctions) {
            auctionEvaluator.evaluate(auction);

            Payment newPayment =
                    new Payment(auctionEvaluator.getGreatestBid().getValue(), firstWorkday(),
                            auctionEvaluator.getGreatestBid().getId());
            paymentDAO.save(newPayment);
            try {
                auctionNotifier.send(auction, newPayment);
            } catch (Exception ignored) {
                //logger exception
            }
        }
    }

    private Date firstWorkday() {
        Date date = calendar.today();
        java.util.Calendar calendarDate = java.util.Calendar.getInstance();
        calendarDate.setTime(date);
        int weekDay = calendarDate.get(java.util.Calendar.DAY_OF_WEEK);

        if(weekDay == java.util.Calendar.SATURDAY) calendarDate.add(java.util.Calendar.DAY_OF_MONTH, 2);
        else if(weekDay == java.util.Calendar.SUNDAY) calendarDate.add(java.util.Calendar.DAY_OF_MONTH, 1);

        return calendarDate.getTime();
    }
}
