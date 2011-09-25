package net.mad.ads.manager.web.component.confirm;

import org.apache.wicket.markup.html.link.Link;

abstract public class ConfirmLink<T> extends Link<T> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3157325290629725543L;

	public ConfirmLink(String id, String msg) {
         super(id);
         add(new JavascriptEventConfirmation("onclick", msg));
    }

    @Override
    abstract public void onClick();
			
}
