package cn.enaium.cf4m.container;

import cn.enaium.cf4m.provider.ConfigProvider;

import java.util.ArrayList;

/**
 * Project: cf4m
 *
 * @author Enaium
 */
public interface ConfigContainer {
    /**
     * NotNull
     *
     * @return config list
     */
    ArrayList<ConfigProvider> getAll();

    /**
     * Nullable
     *
     * @param name config name
     * @return config
     */
    ConfigProvider getByName(String name);

    /**
     * Nullable
     *
     * @param instance config
     * @return config
     */
    ConfigProvider getByInstance(Object instance);

    /**
     * load config
     */
    void load();

    /**
     * save config
     */
    void save();
}
