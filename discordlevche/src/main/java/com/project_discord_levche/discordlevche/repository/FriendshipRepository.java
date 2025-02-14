package com.project_discord_levche.discordlevche.repository;

import com.project_discord_levche.discordlevche.model.FriendModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FriendshipRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public FriendModel save(FriendModel friendship) {
        String sql = "INSERT INTO friendships (user_id, friend_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, friendship.getUserId(), friendship.getFriendId());
        return friendship;
    }

    public List<FriendModel> findByUserId(Long userId) {
        String sql = "SELECT * FROM friendships WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, new FriendshipRowMapper());
    }

    public List<FriendModel> findByFriendId(Long friendId) {
        String sql = "SELECT * FROM friendships WHERE friend_id = ?";
        return jdbcTemplate.query(sql, new Object[]{friendId}, new FriendshipRowMapper());
    }

    private static class FriendshipRowMapper implements RowMapper<FriendModel> {

        @Override
        public FriendModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            FriendModel friendship = new FriendModel();
            friendship.setId(rs.getLong("id"));
            friendship.setUserId(rs.getLong("user_id"));
            friendship.setFriendId(rs.getLong("friend_id"));
            return friendship;
        }
    }
}
