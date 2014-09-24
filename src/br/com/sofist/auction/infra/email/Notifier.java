package br.com.sofist.auction.infra.email;

import br.com.sofist.auction.domain.Auction;
import br.com.sofist.auction.domain.Payment;

public interface Notifier {
    void send(Auction auction) throws Exception;
    void send(Auction auction, Payment payment) throws Exception;
}
