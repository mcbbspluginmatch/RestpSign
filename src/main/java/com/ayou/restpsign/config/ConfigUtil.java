package com.ayou.restpsign.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * @Author: Ayou
 * @Date: 2019/7/2 14:43
 */
public class ConfigUtil {
    public static int setDefaultIfNotSet(final ConfigurationSection section, final String path, final Object value) {
        if (section != null && !section.isSet(path)) {
            section.set(path, value);
            return 1;
        }
        return 0;
    }

    public static void saveMainConfig(File file , YamlConfiguration configuration) {
        if (configuration != null) {
            try {
                configuration.save(file);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
