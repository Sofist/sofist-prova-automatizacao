package br.com.sofist.auction.infra.dao;

import br.com.sofist.auction.domain.Auction;
import br.com.sofist.auction.domain.Bid;
import br.com.sofist.auction.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AuctionDAO {

	private Connection connection;

	public AuctionDAO() {
		try {
			this.connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/mysql", "root", "");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Calendar date(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	public void save(Auction auction) {
		try {
			String sql = "INSERT INTO AUCTION (DESCRIPTION, \"DATE\", CLOSED) VALUES (?,?,?);";
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, auction.getDescription());
			ps.setDate(2, new java.sql.Date(auction.getDate().getTimeInMillis()));
			ps.setBoolean(3, auction.isClosed());
			
			ps.execute();
			
			ResultSet generatedKeys = ps.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            auction.setId(generatedKeys.getInt(1));
	        }
			
			for(Bid bid : auction.getBids()) {
				sql = "INSERT INTO BIDS (AUCTION_ID, USER_ID, \"VALUE\") VALUES (?,?,?);";
				PreparedStatement ps2 = connection.prepareStatement(sql);
				ps2.setInt(1, auction.getId());
				ps2.setInt(2, bid.getUser().getId());
				ps2.setDouble(3, bid.getValue());
				
				ps2.execute();
				ps2.close();
				
			}
			
			ps.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public List<Auction> closed() {
		return areClosed(true);
	}
	
	public List<Auction> current() {
		return areClosed(false);
	}
	
	private List<Auction> areClosed(boolean status) {
		try {
			String sql = "SELECT * FROM AUCTION WHERE CLOSED = " + status + ";";
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			List<Auction> bids = new ArrayList<Auction>();
			while(rs.next()) {
				Auction auction = new Auction(rs.getString("DESCRIPTION"), date(rs.getDate("DATE")));
				auction.setId(rs.getInt("id"));
				if(rs.getBoolean("CLOSED")) auction.close();
				
				String sql2 = "SELECT B.VALUE, U.NAME, U.ID AS USER_ID, B.ID AS BID_ID FROM BIDS B INNER JOIN \"USER\" U " +
                        "ON U.ID = B.USUARIO_ID WHERE B.AUCTION_ID = " + rs.getInt("id");
				PreparedStatement ps2 = connection.prepareStatement(sql2);
				ResultSet rs2 = ps2.executeQuery();
				
				while(rs2.next()) {
					User usuario = new User(rs2.getInt("id"), rs2.getString("NAME"));
					Bid lance = new Bid(usuario, rs2.getDouble("VALUE"));
					
					auction.propose(lance);
				}
				rs2.close();
				ps2.close();
				
				bids.add(auction);
				
			}
			rs.close();
			ps.close();
			
			return bids;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void update(Auction auction) {
		
		try {
			String sql = "UPDATE AUCTION SET DESCRIPTION=?, \"DATE\"=?, CLOSED=? WHERE ID = ?;";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, auction.getDescription());
			ps.setDate(2, new java.sql.Date(auction.getDate().getTimeInMillis()));
			ps.setBoolean(3, auction.isClosed());
			ps.setInt(4, auction.getId());

			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
