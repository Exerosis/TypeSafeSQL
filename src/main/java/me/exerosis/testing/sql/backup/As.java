package me.exerosis.testing.sql.backup;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function5;

import java.sql.Connection;

public interface As {
	interface One<First> {
		interface Transformer<First, Return> {
			Return transform(First first);
		}

		<Return> Function<Connection, Return> as(
				me.exerosis.testing.sql.alpha.select.As.One.Transformer<First, Return> transformer
		);
	}

	interface Two<First, Second> {
		interface Transformer<First, Second, Return> {
			Return transform(First first,
			                 Second second
			);
		}

		<Return> Function<Connection, Return> as(
				me.exerosis.testing.sql.alpha.select.As.Two.Transformer<First, Second, Return> transformer
		);
	}

	interface Three<First, Second, Third> {
		interface Transformer<First, Second, Third, Return> {
			Return transform(First first,
			                 Second second,
			                 Third third
			);
		}

		<Return> Function<Connection, Return> as(
				me.exerosis.testing.sql.alpha.select.As.Three.Transformer<First, Second, Third, Return> transformer
		);
	}

	interface Four<First, Second, Third, Fourth> {
		interface Transformer<First, Second, Third, Fourth, Return> {
			Return transform(First first,
			                 Second second,
			                 Third third,
			                 Fourth fourth
			);
		}

		<Return> Function<Connection, Return> as(
				me.exerosis.testing.sql.alpha.select.As.Four.Transformer<First, Second, Third, Fourth, Return> transformer
		);
	}

	interface Five<First, Second, Third, Fourth, Fifth> {
		interface Transformer<First, Second, Third, Fourth, Fifth, Return> {
			Return transform(First first,
			                 Second second,
			                 Third third,
			                 Fourth fourth,
			                 Fifth fifth
			);
		}

		<Return> Function<Connection, Return> as(
				me.exerosis.testing.sql.alpha.select.As.Five.Transformer<First, Second, Third, Fourth, Fifth, Return> transformer
		);
	}
}
