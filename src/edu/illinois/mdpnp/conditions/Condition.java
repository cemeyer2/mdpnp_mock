package edu.illinois.mdpnp.conditions;

public abstract class Condition {
/*	public enum ConditionOperator {
		greaterThanOrEquals() {
			boolean evaluate(Comparable src, Comparable target){ 
				return src.compareTo(target) >= 0; 
			}
		},
		lessThanOrEquals {
			boolean evaluate(Comparable src, Comparable target) {
				return src.compareTo(target) <= 0;
			}
		},
		greaterThan {
			boolean evaluate(Comparable src, Comparable target){ 
				return src.compareTo(target) > 0; 
			}
		},
		lessThan {
			boolean evaluate(Comparable src, Comparable target) {
				return src.compareTo(target) < 0;
			}
		},
		equals {
			boolean evaluate(Comparable src, Comparable target) {
				return src.compareTo(target) == 0;
			}
		};
		abstract boolean evaluate(Comparable src, Comparable target);
	}
*/	
	/**
	 * src is domain object e.g. heartrate, target is constant e.g. Integer 
	 */
	public abstract boolean evaluate();	
}
