package br.com.sofist.auction.infra.dao;

import br.com.sofist.auction.domain.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    private Connection connection;

    public PaymentDAO() {
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/mysql", "root", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void save(Payment payment) {
        try {
            String sql = "INSERT INTO PAYMENT (\"VALUE\", \"DATE\", BID) VALUES (?,?,?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDouble(1, payment.getValue());
            ps.setDate(2, new java.sql.Date(payment.getDate().getTime()));
            ps.setInt(3, payment.getBidId());

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Payment> getByBidId(int bidId) {
        try {
            String sql = "SELECT * FROM PAYMENT WHERE BID = " + bidId + ";";

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Payment> payments = new ArrayList<Payment>();
            while(rs.next()) {
                Payment payment = new Payment(rs.getDouble("VALUE"), rs.getDate("DATE"), rs.getInt("BID"));
                payment.setId(rs.getInt("id"));

                payments.add(payment);

            }
            rs.close();
            ps.close();

            return payments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void update(Payment payment) {

        try {
            String sql = "UPDATE PAYMENT SET \"VALUE\"=?, \"DATE\"=?, BID=? WHERE ID = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDouble(1, payment.getValue());
            ps.setDate(2, new java.sql.Date(payment.getDate().getTime()));
            ps.setInt(3, payment.getBidId());

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
