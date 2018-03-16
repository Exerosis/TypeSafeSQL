package me.exerosis.testing.sql.alpha;

public interface Field<Type> {
	String name();
	
	default String eq(Type value) {
		//FIXME Yeah obv this is temp
		return "'" + name() + "' = " + to(value).toString();
	}
	
	Type from(Result result);
	
	Result to(Type type);
}