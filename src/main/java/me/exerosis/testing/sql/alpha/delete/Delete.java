package me.exerosis.testing.sql.alpha.delete;

import io.reactivex.functions.Function;
import me.exerosis.testing.sql.alpha.generic.Where;

import java.sql.Connection;

public interface Delete {
	Where<Function<Connection, Integer>> delete(String table);
}