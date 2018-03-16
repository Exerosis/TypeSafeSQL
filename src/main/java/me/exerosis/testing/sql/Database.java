package me.exerosis.testing.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.*;

import java.util.Optional;
import java.util.UUID;

import static me.exerosis.testing.sql.Database.Tables.COL_ID;
import static me.exerosis.testing.sql.Database.Tables.EXAMPLE;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;
import static org.jooq.impl.DSL.using;

public class Database {
	private final DSLContext database;
	
	public Database(HikariConfig config, SQLDialect dialect) {
		database = using(new HikariDataSource(config), dialect);
	}
	
	interface Tables {
		Table<Record> EXAMPLE = table("exer_example");
		Field<UUID> COL_ID = field("id", UUID.class);
		Field<String> COL_NAME = field("name", String.class);
		Field<Integer> COL_LEVEL = field("level", Integer.class);
	}
	
	class PlayerData {
		private final String name;
		private final int level;
		
		PlayerData(String name) {
			this(name, 0);
		}
		
		PlayerData(String name, int level) {
			this.name = name;
			this.level = level;
		}
	}
	
	public Optional<PlayerData> getPlayerData(UUID player) {
		return database.selectFrom(EXAMPLE)
				.where(COL_ID.eq(player))
				.fetchOptionalInto(PlayerData.class);
	}
}
