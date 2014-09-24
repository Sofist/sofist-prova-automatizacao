package br.com.sofist.auction.service;

import br.com.sofist.auction.domain.Auction;
import br.com.sofist.auction.infra.dao.AuctionDAO;
import br.com.sofist.auction.infra.email.Notifier;
import java.util.Calendar;
import java.util.List;

public class AuctionTerminator {

    private int total = 0;
    private final AuctionDAO auctiondDAO;
    private final Notifier auctionNotifier;

    public AuctionTerminator(AuctionDAO dao, Notifier auctionNotifier) {
        this.auctiondDAO = dao;
        this.auctionNotifier = auctionNotifier;
    }

    public void close() {
        List<Auction> currentAuctions = auctiondDAO.current();

        for (Auction auction : currentAuctions) {
            try {
                if (startedLastWeek(auction)) {
                    auction.close();
                    total++;
                    auctiondDAO.update(auction);
                    auctionNotifier.send(auction);
                }
            } catch (Exception ignored) {
                //logger exception
            }
        }
    }

    private boolean startedLastWeek(Auction auction) {
        return daysBetween(auction.getDate(), Calendar.getInstance()) >= 7;
    }

    private int daysBetween(Calendar start, Calendar end) {
        Calendar date = (Calendar) start.clone();
        int daysInterval = 0;
        while (date.before(end)) {
            date.add(Calendar.DAY_OF_MONTH, 1);
            daysInterval++;
        }

        return daysInterval;
    }

    public int getTotalClosed() {
        return total;
    }
}
