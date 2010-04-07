package edu.illinois.mdpnp.conditions;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public class GreaterThanOrEquals extends BinaryCondition {

	@JsonCreator
	public GreaterThanOrEquals(	@JsonProperty(value="source") Comparable source, 
								@JsonProperty(value="target") Comparable target)
	{
		super(source, target);
		this.source = source;
		this.target = target;
	}

	@Override
	public boolean evaluate() {
		return getSource().compareTo(getTarget()) >= 0;
	}

	@Override
	public String getOperator() {
		return ">=";
	}
}