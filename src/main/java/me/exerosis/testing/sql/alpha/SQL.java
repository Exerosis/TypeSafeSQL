package me.exerosis.testing.sql.alpha;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import me.exerosis.testing.sql.alpha.delete.Delete;
import me.exerosis.testing.sql.alpha.insert.Insert;
import me.exerosis.testing.sql.alpha.select.Select;
import me.exerosis.testing.sql.alpha.update.Update;

import javax.sql.DataSource;
import java.sql.Statement;
import java.util.UUID;

import static io.reactivex.Observable.fromCallable;
import static io.reactivex.Observable.interval;
import static io.reactivex.internal.functions.Functions.identity;
import static io.reactivex.schedulers.Schedulers.io;
import static java.util.concurrent.TimeUnit.SECONDS;
import static me.exerosis.testing.sql.alpha.Tuple.all;

public class SQL {
	
	public static <Return extends Select & Insert & Update & Delete> Return mySQL() {
		throw new UnsupportedOperationException("Just for testing :)");
	}
	
	public static Field<Double> doubleField(Object name) {
		return field(name, null, Result::asDouble);
	}
	
	public static Field<Integer> intField(Object name) {
		return field(name, null, Result::asInt);
	}
	
	public static Field<String> stringField(Object name) {
		return stringField(name, identity(), identity());
	}
	
	public static <Type> Field<Type> stringField(
			Object name,
			Function<Type, String> to,
			Function<String, Type> from
	) {
		return field(name, null, result -> from.apply(result.asString()));
	}
	
	public static <Type> Field<Type> field(
			Object name,
			Function<Type, Result> to,
			Function<Result, Type> from
	) {
		return new Field<Type>() {
			@Override
			public String name() {
				return name.toString();
			}
			
			@Override
			public Type from(Result result) {
				try {
					return from.apply(result);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			
			@Override
			public Result to(Type type) {
				try {
					return to.apply(type);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};
	}
	
	
	interface Transformer<Value, First, Second, Third> extends
			Function3<First, Second, Third, Value>,
			Function<Value, Tuple.Three<First, Second, Third>> {
		
	}
	
	public static <Value, First, Second, Third> Transformer<Value, First, Second, Third> transformer(
			Function3<First, Second, Third, Value> from,
			Function<Value, Tuple.Three<First, Second, Third>> to
	) {
		return new Transformer<Value, First, Second, Third>() {
			@Override
			public Tuple.Three<First, Second, Third> apply(Value value) throws Exception {
				return to.apply(value);
			}
			
			@Override
			public Value apply(First first, Second second, Third third) throws Exception {
				return from.apply(first, second, third);
			}
		};
	}
	
	public static void main(String[] args) {
		DataSource source = new HikariDataSource(new HikariConfig("sql.properties"));
		Observable<Statement> pool = fromCallable(() ->
				source.getConnection().createStatement()
		);
		
		String players = "players";
		Field<String> name = stringField("name");
		Field<Integer> exp = intField("exp");
		Field<Double> boost = doubleField("boost");
		Field<UUID> id = stringField("id", UUID::toString, UUID::fromString);
		
		class PlayerData {
			String name;
			Integer exp;
			Double boost;
			
			PlayerData(String name, Integer exp, Double boost) {
				this.name = name;
				this.exp = exp;
				this.boost = boost;
			}
		}
		
		Transformer<PlayerData, String, Integer, Double> playerData =
				transformer(PlayerData::new, data ->
						all(data.name, data.exp, data.boost)
				);
		
		
		Function<Statement, PlayerData> select = mySQL()
				.select(name, exp, boost)
				.from(players)
				.where(name.eq("Exerosis"))
				.as(playerData);
		
		
		BiFunction<Statement, PlayerData, Integer> insert =
				mySQL().insert(name, exp, boost)
						.into(players)
						.from(playerData);
		
		BiFunction<Statement, Integer, Integer> update = mySQL().update(exp)
				.in(players)
				.where(name.eq("Exerosis"))
				.functionally();
		
		//JavaFx way.
		pool.map(select)
				.subscribeOn(io())
				.subscribe(data -> {
					System.out.println("Name: " + data.name);
					System.out.println("Experience: " + data.exp);
					System.out.println("Boost: " + data.boost);
				}, Throwable::printStackTrace);
		
		//GABOOM >:)
		interval(5, SECONDS)
				.map($ -> 10) //10 exp every 5 seconds, keep the DB up to date tho.
				.compose(subZip(pool, update))
				.filter(result -> result == 0) //We only need to know if it fails.
				.subscribe($ ->
						System.err.println("Failed to insert this periods exp!")
				);
	}
	
	public static <Up, From, To> ObservableTransformer<Up, To> subZip(
			Observable<From> source,
			BiFunction<From, Up, To> combiner
	) {
		return upstream -> upstream.map(up -> {
			From from = source.blockingFirst();
			return combiner.apply(from, up);
		});
	}
	
	public static class Pool {
		private final DataSource pool;
		
		public Pool(DataSource pool) {
			this.pool = pool;
		}
	}
}
