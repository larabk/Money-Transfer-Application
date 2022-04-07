package com.techelevator;

import org.junit.After;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;
@RunWith(SpringRunner.class)


public abstract class BaseDaoTests {

    private static SingleConnectionDataSource dataSource;

    @BeforeClass
    public static void setupDataSource() {
        dataSource = new SingleConnectionDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:8080/");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        dataSource.setAutoCommit(false);
    }

    @After
    public void rollback() throws SQLException {
        dataSource.getConnection().rollback();
    }
//    @AfterClass
//    public static void closeDataSource() {
//        dataSource.destroy();
//    }
//    protected DataSource getDataSource() {
//        return dataSource;
//    }


}
