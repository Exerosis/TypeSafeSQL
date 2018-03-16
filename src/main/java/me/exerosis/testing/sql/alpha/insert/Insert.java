package me.exerosis.testing.sql.alpha.insert;

import me.exerosis.testing.sql.alpha.Field;
import me.exerosis.testing.sql.alpha.generic.From;

public interface Insert {
	<First> Into<From.One<First>> insert(
			Field<First> first
	);
	
	<First, Second> Into<From.Two<First, Second>> insert(
			Field<First> first,
			Field<Second> second
	);
	
	<First, Second, Third> Into<From.Three<First, Second, Third>> insert(
			Field<First> first,
			Field<Second> second,
			Field<Third> third
	);
}
