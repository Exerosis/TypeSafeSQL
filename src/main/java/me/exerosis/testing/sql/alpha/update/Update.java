package me.exerosis.testing.sql.alpha.update;

import me.exerosis.testing.sql.alpha.Field;
import me.exerosis.testing.sql.alpha.generic.From;
import me.exerosis.testing.sql.alpha.generic.Where;

public interface Update {
	<First> In<Where<From.One<First>>> update(
			Field<First> first
	);
	
	<First, Second> In<Where<From.Two<First, Second>>> update(
			Field<First> first,
			Field<Second> second
	);
	
	<First, Second, Third> In<Where<From.Three<First, Second, Third>>> update(
			Field<First> first,
			Field<Second> second,
			Field<Third> third
	);
}
