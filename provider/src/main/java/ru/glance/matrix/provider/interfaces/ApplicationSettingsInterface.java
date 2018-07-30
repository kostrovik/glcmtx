package ru.glance.matrix.provider.interfaces;

import java.util.List;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    27/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public interface ApplicationSettingsInterface {
    List<ServerConnectionAddressInterface> getHosts();

    ServerConnectionAddressInterface getDefaultHost();

    void saveHostsList(List<ServerConnectionAddressInterface> hosts);

    void updateHostLastUsage();

    String getDetaultTheme();

    void saveDefaultColorTheme(String theme);
}
