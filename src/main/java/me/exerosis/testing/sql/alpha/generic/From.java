package me.exerosis.testing.sql.alpha.generic;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import me.exerosis.testing.sql.alpha.Tuple;

import java.sql.Statement;

public interface From<To> {
	interface One<First> {
		BiFunction<Statement, First, Integer> functionally();
		
		default <Value> BiFunction<Statement, Value, Integer> from(
				Function<Value, First> transformer
		) {
			return (statement, value) -> functionally()
					.apply(statement, transformer.apply(value));
		}
	}
	
	interface Two<First, Second> {
		Function3<Statement, First, Second, Integer> functionally();
		
		default <Value> BiFunction<Statement, Value, Integer> from(
				Function<Value, Tuple.Two<First, Second>> transformer
		) {
			return (statement, value) -> {
				Tuple.Two<First, Second> values = transformer.apply(value);
				return functionally()
						.apply(statement,
								values.first(),
								values.second()
						);
			};
		}
	}
	
	interface Three<First, Second, Third> {
		Function4<Statement, First, Second, Third, Integer> functionally();
		
		default <Value> BiFunction<Statement, Value, Integer> from(
				Function<Value, Tuple.Three<First, Second, Third>> transformer
		) {
			return (statement, value) -> {
				Tuple.Three<First, Second, Third> values = transformer.apply(value);
				return functionally()
						.apply(statement,
								values.first(),
								values.second(),
								values.third()
						);
			};
		}
	}
}