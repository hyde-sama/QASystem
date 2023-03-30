package org.example;

import org.example.entity.Qa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


import javax.sql.DataSource;
import java.util.List;

@Component
public class QaRepository   {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final RowMapper<Qa> rowmapper= new BeanPropertyRowMapper<>(Qa.class);






    public List<Qa>  findAll(){
        String findAllSql = "select id,question,answer,pattern from qa";
        return jdbcTemplate.query(findAllSql,rowmapper);
    }
}
