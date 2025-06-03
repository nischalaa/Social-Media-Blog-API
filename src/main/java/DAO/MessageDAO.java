package DAO;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MessageDAO {
    public Message insertMessage(Message message) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, message.getPosted_by());
            stmt.setString(2, message.getMessage_text());
            stmt.setLong(3, message.getTime_posted_epoch());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()) {
                int id = rs.getInt(1);
                message.setMessage_id(id);
                return message;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
        }

    public List<Message> getAllMessages() {
         List<Message> messages = new ArrayList<>();
        try( Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM message";
            Statement stmt = conn.createStatement();
            ResultSet rs =  stmt.executeQuery(sql);

            while (rs.next()) {
                messages.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                ));
            }
        }
            catch (SQLException e) {
                e.printStackTrace();
        }
        return messages;
        }
    

    public Message getMessageById(int messageId) {
            try ( Connection conn = ConnectionUtil.getConnection()) {
                String sql = "SELECT * FROM messsge WHERE message_id=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, messageId);

                ResultSet rs = stmt.executeQuery();
                if(rs.next()) {
                    return new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                    );
                }
            }
            catch (SQLException e ) {
                e.printStackTrace();
            }
            return null;
        } 

    public void deleteMessage( int messageId) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql ="DELETE FROM message WHERE message_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, messageId);
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        }

    public void updateMessage(Message message) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "UPDATE message SET message_test = ? WHERE message_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, message.getMessage_text());
            stmt.setInt(2, message.getMessage_id());
            stmt.executeQuery();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Message> getMessagesByAccountId(int accountId) {
        List<Message> messages = new ArrayList<>();
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM messageWHERE posted_by =?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountId);
        
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                messages.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public boolean isValidUser(int userId) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT account_id FROM account WHERE account_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    }
    
    

