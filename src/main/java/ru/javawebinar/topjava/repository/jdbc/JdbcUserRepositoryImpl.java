package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final RowMapper<User> ROW_MAPPER = new RowMapper<User>() {
        @Nullable
        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User u = new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
                    rs.getString("password"), Role.valueOf(rs.getString("role")));
            u.setEnabled(rs.getBoolean("enabled"));
            u.setCaloriesPerDay(rs.getInt("calories_per_day"));
            return u;
        }
    };

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private class UserBatchSetter implements BatchPreparedStatementSetter {
        User user;

        private UserBatchSetter(User user) {
            this.user = user;
        }

        @Override
        public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setInt(1, user.getId());
            ps.setString(2, user.getRoles().toArray()[i].toString());
        }

        @Override
        public int getBatchSize() {
            return user.getRoles().size();
        }
    }

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
            return null;
        } else jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
        final ArrayList<Role> roles = new ArrayList<>(user.getRoles());

        jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, user.getId());
                ps.setString(2, roles.get(i).toString());
            }

            @Override
            public int getBatchSize() {
                return roles.size();
            }
        });
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users JOIN user_roles ON users.id=user_roles.user_id WHERE id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(deleteCopiesAndCollectRoles(users));
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users JOIN user_roles ON users.id=user_roles.user_id WHERE email=?", ROW_MAPPER, email);
        return DataAccessUtils.singleResult(deleteCopiesAndCollectRoles(users));
    }

    @Override
    public List<User> getAll() {
        return deleteCopiesAndCollectRoles(jdbcTemplate.query("SELECT * FROM users JOIN user_roles ON users.id=user_roles.user_id ORDER BY name, email", ROW_MAPPER));
    }

    private List<User> deleteCopiesAndCollectRoles(List<User> users) {
        if (users.size()==0) return users;
        Map<Integer,User> res=new LinkedHashMap<>();
        users.forEach(user -> res.compute(user.getId(),(k, v)->{
           if (v==null) return user;
           v.getRoles().add(user.getRoles().iterator().next());
           return v;
        }));
        return new ArrayList<>(res.values());
    }
}
