package edu.illinois.mdpnp.conditions;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public class AndCondition extends Condition {

	@JsonProperty(value="left")
	private Condition l;
	
	@JsonProperty(value="right")
	private Condition r;
	
	@JsonCreator
	public AndCondition(	@JsonProperty(value="left") Condition l, 	
							@JsonProperty(value="right") Condition r) {
		this.l = l;
		this.r = r;
	}
	
	@Override
	public boolean evaluate() {
		return l.evaluate() && r.evaluate();
	}

}
