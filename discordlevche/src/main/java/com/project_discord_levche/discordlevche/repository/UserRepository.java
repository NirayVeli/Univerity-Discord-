package com.project_discord_levche.discordlevche.repository;

import com.project_discord_levche.discordlevche.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {
    @Autowired
private JdbcTemplate jdbcTemplate;

    public List<UserModel> findAll() {
        return jdbcTemplate.query("SELECT * FROM \"USERS\"", new UserRowMapper());
    }

    public UserModel save(UserModel user) {
        if(user.getId() == null){
            jdbcTemplate.update("INSERT INTO \"USERS\" (username, email) VALUES (?, ?)", user.getUsername(), user.getEmail());
        }else{
            jdbcTemplate.update("UPDATE \"USERS\" SET username = ?, email = ? WHERE id = ?", user.getUsername(), user.getEmail(), user.getId());
        }
        return user;
    }

    public UserModel findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM \"USERS\" WHERE id = ?", new Object[]{id}, new UserRowMapper());
    }

    public void deleteById(Long id){
        jdbcTemplate.update("DELETE FROM \"USERS\" WHERE id = ?", id);
    }

    public List<UserModel> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.query(sql, new Object[]{username}, new UserRowMapper());
    }

    public List<UserModel> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        return jdbcTemplate.query(sql, new Object[]{email}, new UserRowMapper());
    }

    public List<UserModel> findFriendsByUserId(Long userId) {
        String sql = "SELECT u.* FROM users u " +
                "JOIN friendships f ON u.id = f.friend_id " +
                "WHERE f.user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, new UserRowMapper());
    }
    private static class UserRowMapper implements RowMapper<UserModel> {
        @Override
        public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserModel user = new UserModel();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    }
}
