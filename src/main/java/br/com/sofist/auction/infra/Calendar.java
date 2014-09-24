package br.com.sofist.auction.infra;

import java.util.Date;

public class Calendar {
    public Date today() {
        return java.util.Calendar.getInstance().getTime();
    }
}
