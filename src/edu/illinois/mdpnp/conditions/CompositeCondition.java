package edu.illinois.mdpnp.conditions;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public class CompositeCondition extends Condition {

	@JsonCreator
	public CompositeCondition(@JsonProperty(value="conditions") List<Condition> conditions)
	{
		this.conditions = conditions;
	}
	
	public CompositeCondition()
	{
		
	}
	
	@JsonProperty(value="conditions")
	private List<Condition> conditions = new ArrayList<Condition>();
	
	public List<Condition> addCondition(Condition cond) {
		conditions.add(cond);
		return conditions;
	}
	
	@Override
	public boolean evaluate() {
		boolean result = true;
		for (Condition cond : conditions) {
			result = result && cond.evaluate();
			if (!result) break;
		}
		return result;
	}

}
