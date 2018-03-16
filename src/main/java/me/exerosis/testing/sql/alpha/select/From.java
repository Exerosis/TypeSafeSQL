package me.exerosis.testing.sql.alpha.select;

public interface From<Return> {
	Return from(String table);
}