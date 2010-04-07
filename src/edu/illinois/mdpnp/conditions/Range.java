/**
 * 
 */
package edu.illinois.mdpnp.conditions;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author lee467
 * attempt at defining a condition/rule helper class
 */
public class Range extends Condition{
	
	@JsonProperty(value="minimum")
	private BigDecimal minimum;
	
	@JsonProperty(value="maximum")
	private BigDecimal maximum;
	
	@JsonProperty(value="value")
	private BigDecimal value;

	@JsonCreator
	public Range(	@JsonProperty(value="value") BigDecimal value, 
					@JsonProperty(value="minimum") BigDecimal minimum, 
					@JsonProperty(value="maximum") BigDecimal maximum) {
		this.minimum = minimum;
		this.maximum = maximum;
		this.value = value;
	}

	@Override
	public boolean evaluate() {
		return new GreaterThanOrEquals(value, minimum).evaluate() 
			&& new LessThanOrEquals(value, maximum).evaluate();
	}

	public BigDecimal getMinimum() {
		return minimum;
	}

	public BigDecimal getMaximum() {
		return maximum;
	}
	
	public BigDecimal getValue() {
		return value;
	}

}
