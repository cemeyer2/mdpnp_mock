package edu.illinois.mdpnp.conditions;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public abstract class BinaryCondition extends Condition {

	@JsonProperty(value="source")
	protected Comparable source;
	@JsonProperty(value="target")
	protected Comparable target;
	
	@JsonCreator
	public BinaryCondition(	@JsonProperty(value="source") Comparable source, 
							@JsonProperty(value="target") Comparable target)
	{
		this.source = source;
		this.target = target;
	}
	
	@JsonProperty(value="operator")
	public abstract String getOperator();
	
	public Comparable getSource()
	{
		return source;
	}
	
	public Comparable getTarget()
	{
		return target;
	}

}
