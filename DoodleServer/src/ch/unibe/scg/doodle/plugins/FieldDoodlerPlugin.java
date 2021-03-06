package ch.unibe.scg.doodle.plugins;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import ch.unibe.scg.doodle.api.FieldDoodler;
import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;
import ch.unibe.scg.doodle.rendering.FieldDoodlerRendering;
import ch.unibe.scg.doodle.server.util.EclipseIconUtil;

/**
 * Plugin to print all fields of a class.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class FieldDoodlerPlugin extends AbstractPlugin {

	@Inject
	FieldDoodlerRendering fieldDoodlerRendering;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(FieldDoodler.class);
		return hs;
	}

	@Override
	public void render(Object object, Tag tag) throws DoodleRenderException {
		fieldDoodlerRendering.render(object, tag);
	}

	@Override
	public void renderSimplified(Object object, Tag tag) {
		fieldDoodlerRendering.renderSimplified(object, tag);
	}

	@Override
	public String getCSS() {
		return titleCSS() + fieldCSS() + scopeCSS("FieldDoodlerPlugin")
				+ smallCSS();
	}

	private String titleCSS() {
		return ".FieldDoodlerPlugin .title {font-size: 12pt; margin: 4px;}";
	}

	private String fieldCSS() {
		return ".FieldDoodlerPlugin .fieldWrapper {margin: 3px 0; white-space: nowrap;}"
				+ ".FieldDoodlerPlugin .field {border: 1px dotted #ccc; display: inline-block; padding: 1px; background-color: whitesmoke;}"
				+ ".FieldDoodlerPlugin .content {margin-left: 0.5em;}"
				+ ".FieldDoodlerPlugin .content, .FieldDoodlerPlugin .scope, .FieldDoodlerPlugin .name"
				+ "{display: inline-block;}"
				+ ".FieldDoodlerPlugin .name {float: left; margin: 1px 2px; font-weight: 500;}"
				+ ".FieldDoodlerPlugin .noFieldsMessage {color: rgba(0,0,0,0.5);}";
	}

	static String scopeCSS(String pluginClass) {
		return "."
				+ pluginClass
				+ " .scope "
				+ "{float: left; margin-left: 1px; height: 1em; width: 1em; position: relative; right: -1px;"
				+ "border-width: 1px 0 1px 1px; border-style: solid; border-color: #DDD; padding: 3px;"
				+ "background-color: whitesmoke; background-position: center; background-repeat: no-repeat;}"
				+ "."
				+ pluginClass
				+ " .scope.field"
				+ "{border-style: dotted;}"
				+ "."
				+ pluginClass
				+ " .scope.method"
				+ "{border-style: dashed;}"
				+ publicScopeCSS(pluginClass)
				+ protectedScopeCSS(pluginClass)
				+ privateScopeCSS(pluginClass)
				+ defaultScopeCSS(pluginClass)
				+ staticPropertyCSS(pluginClass)
				+ "."
				+ pluginClass
				+ " .scope p {position: relative; bottom: 0.75em; margin-right: 1px;}";
	}

	private static String publicScopeCSS(String pluginClass) {
		return "."
				+ pluginClass
				+ " .scope.field.public {background-image: url('"
				+ EclipseIconUtil.getIcon(EclipseIconUtil.PUBLIC_FIELD).toURI()
				+ "')}"
				+ "."
				+ pluginClass
				+ " .scope.method.public {background-image: url('"
				+ EclipseIconUtil.getIcon(EclipseIconUtil.PUBLIC_METHOD)
						.toURI() + "')}" + "." + pluginClass
				+ " .scope.class.public {background-image: url('"
				+ EclipseIconUtil.getIcon(EclipseIconUtil.PUBLIC_CLASS).toURI()
				+ "')}";
	}

	private static String protectedScopeCSS(String pluginClass) {
		return "."
				+ pluginClass
				+ " .scope.field.protected {background-image:url('"
				+ EclipseIconUtil.getIcon(EclipseIconUtil.PROTECTED_FIELD)
						.toURI()
				+ "')}"
				+ "."
				+ pluginClass
				+ " .scope.method.protected {background-image:url('"
				+ EclipseIconUtil.getIcon(EclipseIconUtil.PROTECTED_METHOD)
						.toURI()
				+ "')}"
				+ "."
				+ pluginClass
				+ " .scope.class.protected {background-image:url('"
				+ EclipseIconUtil.getIcon(EclipseIconUtil.PROTECTED_CLASS)
						.toURI() + "')}";
	}

	private static String privateScopeCSS(String pluginClass) {
		return "."
				+ pluginClass
				+ " .scope.field.private {background-image:url('"
				+ EclipseIconUtil.getIcon(EclipseIconUtil.PRIVATE_FIELD)
						.toURI()
				+ "')}"
				+ "."
				+ pluginClass
				+ " .scope.method.private {background-image:url('"
				+ EclipseIconUtil.getIcon(EclipseIconUtil.PRIVATE_METHOD)
						.toURI()
				+ "')}"
				+ "."
				+ pluginClass
				+ " .scope.class.private {background-image:url('"
				+ EclipseIconUtil.getIcon(EclipseIconUtil.PRIVATE_CLASS)
						.toURI() + "')}";
	}

	private static String defaultScopeCSS(String pluginClass) {
		return "."
				+ pluginClass
				+ " .scope.field.default {background-image:url('"
				+ EclipseIconUtil.getIcon(EclipseIconUtil.DEFAULT_FIELD)
						.toURI()
				+ "')}"
				+ "."
				+ pluginClass
				+ " .scope.method.default {background-image:url('"
				+ EclipseIconUtil.getIcon(EclipseIconUtil.DEFAULT_METHOD)
						.toURI()
				+ "')}"
				+ "."
				+ pluginClass
				+ " .scope.class.default {background-image:url('"
				+ EclipseIconUtil.getIcon(EclipseIconUtil.DEFAULT_CLASS)
						.toURI() + "')}";
	}

	private static String staticPropertyCSS(String pluginClass) {
		return "."
				+ pluginClass
				+ " .scope .static {background-image:url('"
				+ EclipseIconUtil
						.getIcon(EclipseIconUtil.STATIC_MEMBER_OVERLAY).toURI()
				+ "'); background-repeat: no-repeat; background-position: top right;"
				+ "height: 100%; width: 6px; position: relative; left: 70%;}";
		// XXX: width is a hack for black line in static icon
	}

	private String smallCSS() {
		return ".FieldDoodlerPlugin.smallRendering .scope {height: 1em; width: 1em; border-width: 1px; box-shadow: none}"
				+ ".FieldDoodlerPlugin.smallRendering .scope p {bottom: 1.1em;}";
	}

}
