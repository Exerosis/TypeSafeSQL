package me.exerosis.testing.sql.alpha.select;

import io.reactivex.functions.*;

import java.sql.Statement;

public interface As {
	interface One<First> {
		<Return> Function<Statement, Return> as(
				Function<First, Return> transformer
		);
	}
	
	interface Two<First, Second> {
		<Return> Function<Statement, Return> as(
				BiFunction<First, Second, Return> transformer
		);
	}
	
	interface Three<First, Second, Third> {
		<Return> Function<Statement, Return> as(
				Function3<First, Second, Third, Return> transformer
		);
	}
	
	interface Four<First, Second, Third, Fourth> {
		<Return> Function<Statement, Return> as(
				Function4<First, Second, Third, Fourth, Return> transformer
		);
	}
	
	interface Five<First, Second, Third, Fourth, Fifth> {
		<Return> Function<Statement, Return> as(
				Function5<First, Second, Third, Fourth, Fifth, Return> transformer
		);
	}
}
