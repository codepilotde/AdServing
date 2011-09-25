package net.mad.ads.manager.web.component.confirm;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.Model;

public class JavascriptEventConfirmation extends AttributeModifier {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3493727994650457706L;

	public JavascriptEventConfirmation(String event, String msg) {
		super(event, new Model<String>(msg));
	}

	protected String newValue(final String currentValue, final String replacementValue) {
		String prefix = "var conf = confirm('" + replacementValue + "'); " +
			"if (!conf) return false; ";
		String result = prefix;
		if (currentValue != null) {
			result = prefix + currentValue;
		}
		return result;
	}
}
