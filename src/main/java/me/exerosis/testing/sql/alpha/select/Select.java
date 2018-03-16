package me.exerosis.testing.sql.alpha.select;

import me.exerosis.testing.sql.alpha.Field;
import me.exerosis.testing.sql.alpha.generic.Where;

public interface Select {
	<First> From<Where<As.One<First>>> select(
			Field<First> first
	);
	
	<First, Second> From<Where<As.Two<First, Second>>> select(
			Field<First> first,
			Field<Second> second
	);
	
	<First, Second, Third> From<Where<As.Three<First, Second, Third>>> select(
			Field<First> first,
			Field<Second> second,
			Field<Third> third
	);
	
	<First, Second, Third, Fourth> From<Where<As.Four<First, Second, Third, Fourth>>> select(
			Field<First> first,
			Field<Second> second,
			Field<Third> third,
			Field<Fourth> fourth
	);
	
	<First, Second, Third, Fourth, Fifth> From<Where<As.Five<First, Second, Third, Fourth, Fifth>>> select(
			Field<First> first,
			Field<Second> second,
			Field<Third> third,
			Field<Fourth> fourth,
			Field<Fifth> fifth
	);
}