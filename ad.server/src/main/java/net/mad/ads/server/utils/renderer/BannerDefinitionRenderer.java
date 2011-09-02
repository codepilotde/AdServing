package net.mad.ads.server.utils.renderer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.mad.ads.db.definition.BannerDefinition;

public interface BannerDefinitionRenderer<T extends BannerDefinition> {

	public String render(T banner, HttpServletRequest request);
}