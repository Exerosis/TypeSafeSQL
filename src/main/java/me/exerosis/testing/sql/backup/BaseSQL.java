package me.exerosis.testing.sql.alpha;

import io.reactivex.functions.Function;
import me.exerosis.testing.sql.alpha.select.Select;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

@SuppressWarnings("unchecked")
public class BaseSQL implements Select {
	private final Dialect dialect;
	
	public BaseSQL(Dialect dialect) {
		this.dialect = dialect;
	}
	
	//TODO it would be faster to let the fields themselves drag values from the ResultSet
	private Iterator<Iterator<Object>> fetchSelect(Connection connection,
	                                               String table,
	                                               String condition,
	                                               Field<?>... fields
	) throws SQLException {
		String[] names = new String[fields.length];
		for (int i = 0; i < fields.length; i++)
			names[i] = fields[i].name();
		String query = dialect.select(names, table, condition);
		ResultSet results = connection.createStatement().executeQuery(query);
		ResultCursor cursor = new ResultCursor(results);
		return new Iterator<Iterator<Object>>() {
			boolean hasNext = getNext();
			
			@Override
			public boolean hasNext() {
				return hasNext;
			}
			
			@Override
			public Iterator<Object> next() {
				return new Iterator<Object>() {
					int index = 0;
					
					@Override
					public boolean hasNext() {
						return index < fields.length;
					}
					
					@Override
					public Object next() {
						Object result = fields[index].from(cursor);
						if (!hasNext())
							hasNext = getNext();
						return result;
					}
				};
			}
			
			boolean getNext() {
				try {
					return results.next();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		};
	}
	
	class ResultCursor implements Result {
		private final ResultSet set;
		
		public ResultCursor(ResultSet set) {
			this.set = set;
		}
	}
	
	@Override
	public <First> Step.From<Step.Where<Step.Into1<First>>> select(
			Field<First> first
	) {
		return table -> condition -> new Step.Into1<First>() {
			@Override
			public <Return> Function<Connection, Return> into(Transformer<First, Return> transformer) {
				return connection -> {
					Iterator<Object> row = fetchSelect(connection,
							table,
							condition,
							first
					).next();
					return transformer.transform(
							(First) row.next()
					);
				};
			}
		};
	}
	
	@Override
	public <First, Second> Step.From<Step.Where<Step.Into2<First, Second>>> select(
			Field<First> first,
			Field<Second> second
	) {
		return table -> condition -> new Step.Into2<First, Second>() {
			@Override
			public <Return> Function<Connection, Return> into(Transformer<First, Second, Return> transformer) {
				return connection -> {
					Iterator<Object> row = fetchSelect(connection,
							table,
							condition,
							first,
							second
					).next();
					return transformer.transform(
							(First) row.next(),
							(Second) row.next()
					);
				};
			}
		};
	}
	
	@Override
	public <First, Second, Third> Step.From<Step.Where<Step.Into3<First, Second, Third>>> select(
			Field<First> first,
			Field<Second> second,
			Field<Third> third
	) {
		return table -> condition -> new Step.Into3<First, Second, Third>() {
			@Override
			public <Return> Function<Connection, Return> into(Transformer<First, Second, Third, Return> transformer) {
				return connection -> {
					Iterator<Object> row = fetchSelect(connection,
							table,
							condition,
							first,
							second,
							third
					).next();
					return transformer.transform(
							(First) row.next(),
							(Second) row.next(),
							(Third) row.next()
					);
				};
			}
		};
	}
	
	@Override
	public <First, Second, Third, Fourth> Step.From<Step.Where<Step.Into4<First, Second, Third, Fourth>>> select(Field<First> first, Field<Second> second, Field<Third> third, Field<Fourth> fourth) {
		return table -> condition -> new Step.Into4<First, Second, Third, Fourth>() {
			@Override
			public <Return> Function<Connection, Return> into(Transformer<First, Second, Third, Fourth, Return> transformer) {
				return connection -> {
					Iterator<Object> row = fetchSelect(connection,
							table,
							condition,
							first,
							second,
							third,
							fourth
					).next();
					return transformer.transform(
							(First) row.next(),
							(Second) row.next(),
							(Third) row.next(),
							(Fourth) row.next()
					);
				};
			}
		};
	}
	
	@Override
	public <First, Second, Third, Fourth, Fifth> Step.From<Step.Where<Step.Into5<First, Second, Third, Fourth, Fifth>>> select(Field<First> first, Field<Second> second, Field<Third> third, Field<Fourth> fourth, Field<Fifth> fifth) {
		return table -> condition -> new Step.Into5<First, Second, Third, Fourth, Fifth>() {
			@Override
			public <Return> Function<Connection, Return> into(Transformer<First, Second, Third, Fourth, Fifth, Return> transformer) {
				return connection -> {
					Iterator<Object> row = fetchSelect(connection,
							table,
							condition,
							first,
							second,
							third,
							fourth,
							fifth
					).next();
					return transformer.transform(
							(First) row.next(),
							(Second) row.next(),
							(Third) row.next(),
							(Fourth) row.next(),
							(Fifth) row.next()
					);
				};
			}
		};
	}
	
	interface Dialect {
		String select(String[] fields, String table, String condition);
	}
}