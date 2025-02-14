package com.project_discord_levche.discordlevche.repository;

import com.project_discord_levche.discordlevche.model.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MessageRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<MessageModel> findAll() {
        return jdbcTemplate.query("SELECT * FROM \"MESSAGES\"", new MessageRowMapper());
    }

    public MessageModel save(MessageModel message) {
        if(message.getId() == null){

            jdbcTemplate.update("INSERT INTO \"MESSAGES\" (content, sender_id, channel_id, receiver_id VALUES (?, ?, ?, ?, ?)",
                    message.getContent(), message.getSenderId(), message.getChannelId(), message.getReceiverId());
        }else {
            jdbcTemplate.update("UPDATE \"MESSAGES\" SET content = ?, sender_id = ?, channel_id = ?, receiver_id = ? WHERE id = ?",
                    message.getContent(), message.getSenderId(), message.getChannelId(), message.getReceiverId(), message.getId());
        }
        return message;
    }

    public MessageModel findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM \"MESSAGES\" WHERE id = ?",
                new Object[]{id}, new MessageRowMapper());
    }
    public void deleteById(Long id){
        jdbcTemplate.update("DELETE FROM \"MESSAGES\" WHERE id = ?", id);
    }

    public List<MessageModel> findByReceiverId(Long receiverId) {
        String sql = "SELECT * FROM \"MESSAGES\" WHERE receiver_id = ?";
        return jdbcTemplate.query(sql, new Object[]{receiverId}, new MessageRowMapper());
    }

    public List<MessageModel> findMessagesBetweenUsers(Long userId1, Long userId2) {
        String sql = "SELECT * FROM \"MESSAGES\" WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?)";
        return jdbcTemplate.query(sql, new Object[]{userId1, userId2, userId2, userId1}, new MessageRowMapper());
    }

    public List<MessageModel> findMessagesByChannelId(Long channelId) {
        String sql = "SELECT * FROM \"MESSAGES\" WHERE channel_id = ?";
        return jdbcTemplate.query(sql, new Object[]{channelId}, new MessageRowMapper());
    }
    private static class MessageRowMapper implements RowMapper<MessageModel> {
        @Override
        public MessageModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            MessageModel message = new MessageModel();
            message.setId(rs.getLong("id"));
            message.setContent(rs.getString("content"));
            message.setSenderId(rs.getLong("sender_id"));
            message.setChannelId(rs.getLong("channel_id"));
            message.setReceiverId(rs.getLong("receiver_id"));
            return message;
        }
    }
}
