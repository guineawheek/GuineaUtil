package io.github.guineawheek.guineautil;

import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Config {

    private static class Defaults {
        public static final int indentLevel = 0;
        public static final boolean dumpAllTemplates = false;
    }

    private static class Categories {
        public static final String general = "general";
    }

    public static int indentLevel = Defaults.indentLevel;
    public static boolean dumpAllTemplates = false;

    public static void syncronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);
        configuration.load();

        Property indentProperty = configuration.get(
                Categories.general,
                "indent",
                Defaults.indentLevel,
                "JSON export indent level.\n0 removes all formatting, 2 is good for readability.");
        indentLevel = Math.max(indentProperty.getInt(), 0);

        Property dumpAllTemplatesProperty = configuration.get(
                Categories.general,
                "dumpAllTemplates",
                Defaults.dumpAllTemplates,
                "Whether to enable dumping NEI handlers subclassing the base TemplateRecipeHandlerDumper if no other dumpers claim it.\n"
                        + "Enable to try to dump handlers that are not explicitly supported, or dump facade and firework recipes for some reason");
        dumpAllTemplates = dumpAllTemplatesProperty.getBoolean();

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
