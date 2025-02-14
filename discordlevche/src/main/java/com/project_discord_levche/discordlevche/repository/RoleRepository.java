package com.project_discord_levche.discordlevche.repository;

import com.project_discord_levche.discordlevche.model.RoleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RoleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<RoleModel> findAll() {
        return jdbcTemplate.query("SELECT * FROM \"ROLES\"", new RoleRowMapper());
    }

    public RoleModel save(RoleModel role) {
        if (role.getId() == null) {
            jdbcTemplate.update("INSERT INTO \"ROLES\" (name) VALUES (?)",
                    role.getName());
        } else {
            jdbcTemplate.update("UPDATE \"ROLES\" SET name = ? WHERE id = ?",
                    role.getName(), role.getId());
        }
        return role;
    }

    public RoleModel findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM \"ROLES\" WHERE id = ?",
                new Object[]{id}, new RoleRowMapper());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM \"ROLES\" WHERE id = ?", id);
    }

    public boolean hasRole(Long userId, String roleName) {
        String sql = "SELECT COUNT(*) FROM user_roles ur " +
                "JOIN roles r ON ur.role_id = r.id " +
                "WHERE ur.user_id = ? AND r.name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{userId, roleName}, Integer.class);
        return count != null && count > 0;
    }

    public void assignRoleToUser(Long userId, String roleName) {
        String checkSql = "SELECT COUNT(*) FROM user_roles ur " +
                "JOIN roles r ON ur.role_id = r.id " +
                "WHERE ur.user_id = ? AND r.name = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, new Object[]{userId, roleName}, Integer.class);
        if (count == null || count == 0) {
            String insertSql = "INSERT INTO user_roles (user_id, role_id) VALUES (?, (SELECT id FROM roles WHERE name = ?))";
            jdbcTemplate.update(insertSql, userId, roleName);
        }
    }

    private static class RoleRowMapper implements RowMapper<RoleModel> {
        @Override
        public RoleModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            RoleModel role = new RoleModel();
            role.setId(rs.getLong("id"));
            role.setName(rs.getString("name"));
            return role;
        }
    }
}
