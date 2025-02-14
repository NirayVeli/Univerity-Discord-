package com.project_discord_levche.discordlevche.repository;

import com.project_discord_levche.discordlevche.model.ChannelsModel;
import com.project_discord_levche.discordlevche.model.UserChannelModel;
import com.project_discord_levche.discordlevche.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserChannelRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ChannelsModel> findChannelsByUserId(Long userId) {
        String sql = "SELECT c.* FROM channels c JOIN user_channels uc ON c.id = uc.channel_id WHERE uc.user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
            ChannelsModel channel = new ChannelsModel();
            channel.setId(rs.getLong("id"));
            channel.setName(rs.getString("name"));
            channel.setDescription(rs.getString("description"));
            channel.setOwnerId(rs.getLong("owner_id"));
            return channel;
        });
    }

    public List<UserChannelModel> findAll() {
        return jdbcTemplate.query("SELECT * FROM user_channels", new UserChannelRowMapper());
    }

    public UserChannelModel save(UserChannelModel userChannel) {
        if (userChannel.getId() == null) {
            jdbcTemplate.update("INSERT INTO user_channels (user_id, channel_id) VALUES (?, ?)",
                    userChannel.getUser().getId(), userChannel.getChannel().getId());
        } else {
            jdbcTemplate.update("UPDATE user_channels SET user_id = ?, channel_id = ? WHERE id = ?",
                    userChannel.getUser().getId(), userChannel.getChannel().getId(), userChannel.getId());
        }
        return userChannel;
    }

    public UserChannelModel findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM user_channels WHERE id = ?",
                new Object[]{id}, new UserChannelRowMapper());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM user_channels WHERE id = ?", id);
    }

    public void removeUserFromChannel(Long channelId, Long userId) {
        String sql = "DELETE FROM user_channels WHERE channel_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, channelId, userId);
    }

    private static class UserChannelRowMapper implements RowMapper<UserChannelModel> {

        @Override
        public UserChannelModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserChannelModel userChannel = new UserChannelModel();
            userChannel.setId(rs.getLong("id"));

            UserModel user = new UserModel();
            user.setId(rs.getLong("user_id"));
            userChannel.setUser(user);

            ChannelsModel channel = new ChannelsModel();
            channel.setId(rs.getLong("channel_id"));
            userChannel.setChannel(channel);

            return userChannel;
        }
    }
}
