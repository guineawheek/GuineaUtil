package io.github.guineawheek.guineautil;

import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Config {

    private static class Defaults {
        public static final int indentLevel = 0;
    }

    private static class Categories {
        public static final String general = "general";
    }

    public static int indentLevel = Defaults.indentLevel;

    public static void syncronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);
        configuration.load();

        Property indentProperty =
                configuration.get(Categories.general, "indent", Defaults.indentLevel, "JSON export indent level -- 0 removes all formatting");
        indentLevel = indentProperty.getInt();

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
