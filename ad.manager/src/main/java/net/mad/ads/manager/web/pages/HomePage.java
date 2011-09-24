/*
 * Mad-Advertisement 
 * Copyright (C) 2011 Thorsten Marx
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mad.ads.manager.web.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.odlabs.wiquery.ui.button.ButtonBehavior;

/**
 * The startpage of the manager
 * 
 */
@AuthorizeInstantiation("ADMIN")
public class HomePage extends BasePage {

	private static final long serialVersionUID = 7275625409857017786L;
	
	public HomePage () {
		super();

		add(new AjaxLink<Void>("firstButton"){

			@Override
			public void onClick(AjaxRequestTarget target) {
				System.out.println("firstbutton clicked");
			}

			
		}.add(new ButtonBehavior()));
	}
}
