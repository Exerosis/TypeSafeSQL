package me.exerosis.testing.sql.alpha;

@SuppressWarnings("unchecked")
public interface Tuple {
	
	static <First, Second, Third> Tuple.Three<First, Second, Third> all(First first,
	                                                                    Second second,
	                                                                    Third third
	) {
		return index -> {
			switch (index) {
				case 0: return first;
				case 1: return second;
				default: return third;
			}
		};
		
	}
	
	Object at(int index);
	
	interface One<First> extends Tuple {
		default First first() {
			return (First) at(0);
		}
	}
	
	interface Two<First, Second> extends One<First> {
		default Second second() {
			return (Second) at(1);
		}
	}
	
	interface Three<First, Second, Third> extends Two<First, Second> {
		default Third third() {
			return (Third) at(2);
		}
	}
	
	interface Four<First, Second, Third, Fourth> extends Three<First, Second, Third> {
		default Fourth fourth() {
			return (Fourth) at(3);
		}
	}
	
	interface Five<First, Second, Third, Fourth, Fifth> extends Four<First, Second, Third, Fourth> {
		default Fifth fifth() {
			return (Fifth) at(4);
		}
	}
	
	interface Six<First, Second, Third, Fourth, Fifth, Sixth> extends Five<First, Second, Third, Fourth, Fifth> {
		default Sixth sixth() {
			return (Sixth) at(5);
		}
	}
}