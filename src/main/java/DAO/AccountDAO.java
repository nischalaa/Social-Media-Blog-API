package DAO;
import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;


public class AccountDAO {
    public Account insertAccount(Account account) {
        Connection conn = ConnectionUtil.getConnection();
        try  {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, account.getUsername());
            stmt.setString(2, account.getPassword());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()) {
                int id = rs.getInt(1);
                return new Account(id, account.getUsername(), account.getPassword());
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account getAccountByUsername(String username) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account getAccountByUsernameAndPassword(String username, String password) {
        try ( Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
           

        
    

