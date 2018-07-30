package ru.glance.matrix.provider.interfaces;

import java.time.LocalDateTime;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    27/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public interface ServerConnectionAddressInterface {
    String getUrl();

    LocalDateTime getLastUsage();

    void setLastUsage(LocalDateTime lastUsage);

    Boolean isDefault();

    void setDefault(boolean isDefault);
}
