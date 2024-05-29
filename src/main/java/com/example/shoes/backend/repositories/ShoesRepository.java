package com.example.shoes.backend.repositories;

import com.example.shoes.backend.model.Shoes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShoesRepository {
    private final JdbcTemplate jdbc;

    public ShoesRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void storeShoes(Shoes shoes) {
        String sql = "INSERT INTO shoes (id, name, size) VALUES (NULL, ?, ?)";

        jdbc.update(sql,
                shoes.getName(),
                shoes.getSize());
    }

    public void saveShoes(Shoes shoes) {
        if (shoes.getId() == 0) {
            String sql = "INSERT INTO shoes (id, name, size) VALUES (NULL, ?, ?)";
            jdbc.update(sql,
                    shoes.getName(),
                    shoes.getSize());
        }
        else {
            String sql = "UPDATE shoes SET name = ?, size = ? WHERE id = ?";
            jdbc.update(sql,
                    shoes.getName(),
                    shoes.getSize(),
                    shoes.getId());
        }
    }

    public List<Shoes> findAllShoes() {
        String sql = "SELECT * from shoes";

        RowMapper<Shoes> shoesRowMapper = (r, i) -> {
            Shoes rowObject = new Shoes();
            rowObject.setId(r.getInt("id"));
            rowObject.setName(r.getString("name"));
            rowObject.setSize(r.getDouble("size"));
            return rowObject;
        };

        return jdbc.query(sql, shoesRowMapper);
    }

    public List<Shoes> searchShoes(String stringFilter) {
        String sql = "SELECT * from shoes WHERE name LIKE ?";

        RowMapper<Shoes> shoesRowMapper = (r, i) -> {
            Shoes rowObject = new Shoes();
            rowObject.setId(r.getInt("id"));
            rowObject.setName(r.getString("name"));
            rowObject.setSize(r.getDouble("size"));
            return rowObject;
        };

        return jdbc.query(sql, shoesRowMapper, '%' + stringFilter + '%');
    }

    public int countShoes() {
        return jdbc.queryForObject("SELECT COUNT(*) FROM shoes", Integer.class);
    }

    public void deleteShoes(Shoes shoes) {
        String sql = "DELETE FROM shoes WHERE id = ?";

        jdbc.update(sql,
                shoes.getId());
    }
}
