package br.com.sofist.auction.infra.email;

import br.com.sofist.auction.domain.Auction;
import br.com.sofist.auction.domain.Bid;
import br.com.sofist.auction.domain.Payment;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import java.util.Date;

public class NotifierImpl implements Notifier {
    @Override
    public void send(Auction auction) throws Exception {
        for (Bid bid : auction.getBids()) {
            Email email = new SimpleEmail();
            try {
                email.addTo(bid.getUser().getEmail());
                StringBuilder msg;
                msg = new StringBuilder();
                msg.append("The following auction was closed: ").append(auction.getDescription());
                msg.append(" on date: ").append(new Date().toString());
                email.setMsg(msg.toString());
                email.send();
            } catch (EmailException e) {
                throw new Exception("Error on send email {}", e);
            }

        }
    }

    @Override
    public void send(Auction auction, Payment payment) throws Exception {
            Email email = new SimpleEmail();
            try {
                StringBuilder msg;
                msg = new StringBuilder();
                msg.append("The following auction: ").append(auction.getDescription());
                msg.append(" received a new payment: ").append(payment.getValue());
                msg.append(" from bid: ").append(payment.getBidId());
                msg.append(" on date: ").append(new Date().toString());
                email.setMsg(msg.toString());
                email.send();
            } catch (EmailException e) {
                throw new Exception("Error on send email {}", e);
            }

    }
}
