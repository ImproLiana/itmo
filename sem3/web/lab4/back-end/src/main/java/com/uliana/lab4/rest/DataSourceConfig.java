package com.uliana.lab4.rest;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
@DataSourceDefinition(
        name = "java:app/jdbc/Lab4DS",
        className = "org.hsqldb.jdbc.JDBCDataSource",
        url = "jdbc:hsqldb:file:${jboss.server.data.dir}/lab4db;shutdown=true",
        user = "sa",
        password = ""
)
public class DataSourceConfig {
    // пусто, это просто триггер для создания datasource
}
