package io.thorntail.migrate.maven.rules;

import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import io.thorntail.Info;
import io.thorntail.migrate.maven.ModelAction;

import static io.thorntail.Info.KEY;
import static io.thorntail.Info.ROOT_PACKAGE;

/**
 * Created by bob on 3/13/18.
 */
public class PluginAction implements ModelAction {

    public PluginAction(PluginRule rule, Plugin plugin) {
        this.rule = rule;
        this.plugin = plugin;
    }

    @Override
    public void apply(Model model) {
        model.getBuild().getPlugins().remove(this.plugin);
        Plugin replacement = new Plugin();
        replacement.setGroupId(ROOT_PACKAGE);
        replacement.setArtifactId(KEY + "-maven-plugin");
        replacement.setVersion("${version." + KEY + "}");

        Xpp3Dom config = new Xpp3Dom("configuration");
        Xpp3Dom format = new Xpp3Dom("format");
        format.setValue("jar");
        config.addChild(format);
        replacement.setConfiguration(config);
        PluginExecution exec = new PluginExecution();
        exec.getGoals().add("package");
        exec.setId("package");
        replacement.getExecutions().add(exec);
        model.getBuild().getPlugins().add(replacement);
    }

    @Override
    public PluginRule getRule() {
        return this.rule;
    }

    private final PluginRule rule;

    private final Plugin plugin;
}
