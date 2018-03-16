package me.exerosis.testing.sql.alpha;


import java.util.function.Function;

public class SQLDSL {
	public static Field<Number> numericField(Object name) {
		return field(name, null, result -> new Number() {
			@Override
			public int intValue() {
				return result.asInt();
			}
			
			@Override
			public long longValue() {
				return result.asLong();
			}
			
			@Override
			public float floatValue() {
				return result.asFloat();
			}
			
			@Override
			public double doubleValue() {
				return result.asDouble();
			}
		});
	}
	
	public static Field<String> stringField(Object name) {
		return field(name, null, Result::asString);
	}
	
	public static <Type> Field<Type> field(Object name, Function<Type, Result> to, Function<Result, Type> from) {
		return new Field<Type>() {
			@Override
			public String name() {
				return name.toString();
			}
			
			@Override
			public Type from(Result result) {
				return from.apply(result);
			}
			
			@Override
			public Result to(Type type) {
				return to.apply(type);
			}
		};
	}
	
	
}
