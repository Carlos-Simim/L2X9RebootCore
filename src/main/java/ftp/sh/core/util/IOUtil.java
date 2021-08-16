package ftp.sh.core.util;

import ftp.sh.core.L2X9RebootCore;
import ftp.sh.core.util.ConfigCreator.ConfigurationWrapper;


import java.io.File;

public class IOUtil {
    public static ConfigurationWrapper createConfig(L2X9RebootCore plugin, String dirName, String fileName, String resource) {
        File dataFolder = new File(plugin.getCreator().getDataFolder(), dirName);
        if (!dataFolder.exists()) dataFolder.mkdir();
        plugin.getCreator().makeConfig(dataFolder, resource, fileName);
       return plugin.getCreator().getConfigs().get(fileName);
    }
}
