package ru.clevertec.ecl.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.entities.Tag;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagDaoImpl implements TagDao {

    private static final String GET_ALL = "select * from tags";
    private static final String FIND_BY_ID = "select * from tags where id = ?";
    private static final String INSERT_INTO = "insert into tags (name) values (?)";
    private static final String UPDATE = "update tags set name = ? where id = ?";
    private static final String DELETE = "delete from tags where id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(GET_ALL, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Optional<Tag> findById(long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, new BeanPropertyRowMapper<>(Tag.class), id));
    }

    @Override
    public void add(Tag tag) {
        jdbcTemplate.update(INSERT_INTO, tag.getName());
    }

    @Transactional
    @Override
    public void update(long id, Tag tag) {
        jdbcTemplate.update(UPDATE, tag.getName(), id);
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update(DELETE, id);
    }
}
