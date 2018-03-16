package me.exerosis.testing.sql;

import com.zaxxer.hikari.HikariConfig;
import org.jooq.impl.DSL;

import static org.jooq.SQLDialect.MYSQL;

public class Main {
	
	public static void main(String[] args) {
		HikariConfig config = new HikariConfig("sql.properties");
		Database database = new Database(config, MYSQL);
		
		DSL.insertInto()
		
	}
}
