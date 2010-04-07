package edu.illinois.mdpnp.mock.models;

import java.util.Map;

import edu.illinois.mdpnp.conditions.AndCondition;
import edu.illinois.mdpnp.conditions.Condition;
import edu.illinois.mdpnp.conditions.Equals;
import edu.illinois.mdpnp.conditions.GreaterThan;
import edu.illinois.mdpnp.conditions.GreaterThanOrEquals;
import edu.illinois.mdpnp.conditions.LessThan;
import edu.illinois.mdpnp.conditions.LessThanOrEquals;

public class DeviceData {
	private int port;
	private String _id;
	private String _rev;
	private String[] attributes;
	private ConditionBuilder conditions;

	public ConditionBuilder getConditions() {
		return conditions;
	}

	public void setConditions(ConditionBuilder conditions) {
		this.conditions = conditions;
	}

	public String[] getAttributes() {
		return attributes;
	}

	public void setAttributes(String[] attributes) {
		this.attributes = attributes;
	}

	public String get_rev() {
		return _rev;
	}

	public void set_rev(String rev) {
		_rev = rev;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String id) {
		_id = id;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public static class ConditionBuilder {
		private int lessThan = -1;
		private int greaterThan = -1;
		private int equals = -1;
		private int lessThanOrEquals = -1;
		private int greaterThanOrEquals = -1;
		
		private Condition condition;
		private Integer source;
		

		private void buildCondition() {
			condition = null;
			if (lessThan >= 0) {
				setCondition(condition, new LessThan(source, lessThan));
			}
			if (greaterThan >= 0) {
				setCondition(condition, new GreaterThan(source, greaterThan));
			}
			if (equals >= 0) {
				setCondition(condition, new Equals(source, equals));
			}
			if (lessThanOrEquals >= 0) {
				setCondition(condition, new LessThanOrEquals(source, lessThanOrEquals));
			}
			if (greaterThanOrEquals >= 0) {
				setCondition(condition, new GreaterThanOrEquals(source, greaterThanOrEquals));
			}
		}
		
		private void setCondition(Condition cond1, Condition cond2) {
			if (cond1 == null) {
				condition = cond2;
			} else if (cond2 == null) {
				condition = cond1;
			} else {
				condition = new AndCondition(cond1, cond2);
			}
		}
		
		public Condition getCondition() {
			return condition;
		}

		public void setSource(int source) {
			this.source = source;
			buildCondition();
		}

		public int getLessThan() {
			return lessThan;
		}

		public void setLessThan(int lessThan) {
			this.lessThan = lessThan;
		}

		public int getGreaterThan() {
			return greaterThan;
		}

		public void setGreaterThan(int greaterThan) {
			this.greaterThan = greaterThan;
		}

		public int getEquals() {
			return equals;
		}

		public void setEquals(int equals) {
			this.equals = equals;
		}

		public int getLessThanOrEquals() {
			return lessThanOrEquals;
		}

		public void setLessThanOrEquals(int lessThanOrEquals) {
			this.lessThanOrEquals = lessThanOrEquals;
		}

		public int getGreaterThanOrEquals() {
			return greaterThanOrEquals;
		}

		public void setGreaterThanOrEquals(int greaterThanOrEquals) {
			this.greaterThanOrEquals = greaterThanOrEquals;
		}
	}
}
