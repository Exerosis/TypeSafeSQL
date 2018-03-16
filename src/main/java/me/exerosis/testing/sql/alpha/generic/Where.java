package me.exerosis.testing.sql.alpha.generic;

public interface Where<Return> {
	Return where(String condition);
}