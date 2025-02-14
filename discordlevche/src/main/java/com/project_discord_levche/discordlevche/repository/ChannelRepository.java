package com.project_discord_levche.discordlevche.repository;

import com.project_discord_levche.discordlevche.model.ChannelsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ChannelRepository  {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ChannelsModel> findAll() {
        return jdbcTemplate.query("SELECT * FROM \"CHANNELS\"", new ChannelRowMapper());
    }

    public ChannelsModel save(ChannelsModel channel) {
        if(channel.getId() == null){
            jdbcTemplate.update("INSERT INTO \"CHANNELS\" (name, description, owner_id) VALUES (?, ?, ?)",
                    channel.getName(), channel.getDescription(), channel.getOwnerId());
        }else{
            jdbcTemplate.update("UPDATE \"CHANNELS\" SET name = ?, description = ? WHERE id = ?",
                    channel.getName(), channel.getDescription(), channel.getId());
        }
        return channel;
    }

    public ChannelsModel findById(Long id){
        return jdbcTemplate.queryForObject("SELECT * FROM \"CHANNELS\" WHERE id = ?", new Object[]{id}, new ChannelRowMapper());
    }

    public void deleteById(Long id){
        jdbcTemplate.update("DELETE FROM \"CHANNELS\" WHERE id = ?", id);
    }

    public boolean isOwner(Long channelId, Long userId) {
        String sql = "SELECT COUNT(*) FROM channels WHERE id = ? AND owner_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{channelId, userId}, Integer.class);
        return count != null && count > 0;
    }

    public boolean hasRole(Long userId, String roleName) {
        String sql = "SELECT COUNT(*) FROM user_roles ur " +
                "JOIN roles r ON ur.role_id = r.id " +
                "WHERE ur.user_id = ? AND r.name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{userId, roleName}, Integer.class);
        return count != null && count > 0;
    }

    private static class ChannelRowMapper implements RowMapper<ChannelsModel> {
        @Override
        public ChannelsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            ChannelsModel channel = new ChannelsModel();
            channel.setId(rs.getLong("id"));
            channel.setName(rs.getString("name"));
            channel.setDescription(rs.getString("description"));
            channel.setOwnerId(rs.getLong("owner_id"));
            return channel;
        }
    }
}
