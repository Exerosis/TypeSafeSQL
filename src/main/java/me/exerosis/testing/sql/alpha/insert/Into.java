package me.exerosis.testing.sql.alpha.insert;

public interface Into<Return> {
	Return into(String table);
}