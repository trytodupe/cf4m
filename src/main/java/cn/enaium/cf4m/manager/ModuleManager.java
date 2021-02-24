package cn.enaium.cf4m.manager;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.*;
import cn.enaium.cf4m.annotation.module.extend.Extend;
import cn.enaium.cf4m.annotation.module.extend.Value;
import cn.enaium.cf4m.annotation.module.*;
import cn.enaium.cf4m.event.events.KeyboardEvent;
import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.module.ValueBean;
import com.google.common.collect.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Project: cf4m
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@SuppressWarnings({"unchecked", "unused"})
public class ModuleManager {

    /**
     * <K> module
     * <V> values
     */
    private final HashMultimap<Object, ValueBean> modules = HashMultimap.create();

    public ModuleManager() {
        CF4M.INSTANCE.event.register(this);
        try {
            //Find Value
            Class<?> extend = null;//Extend class
            HashMap<String, Field> findFields = Maps.newHashMap();
            for (Class<?> type : CF4M.INSTANCE.type.getClasses()) {
                if (type.isAnnotationPresent(Extend.class)) {
                    extend = type;
                    for (Field field : type.getDeclaredFields()) {
                        field.setAccessible(true);
                        if (field.isAnnotationPresent(Value.class)) {
                            Value value = field.getAnnotation(Value.class);
                            findFields.put(value.value(), field);//Add value
                        }
                    }
                }
            }

            //Add Modules
            for (Class<?> type : CF4M.INSTANCE.type.getClasses()) {
                if (type.isAnnotationPresent(Module.class)) {
                    Object extendObject = extend != null ? extend.newInstance() : null;
                    Object moduleObject = type.newInstance();
                    for (Map.Entry<String, Field> entry : findFields.entrySet()) {
                        modules.put(moduleObject, new ValueBean(entry.getKey(), entry.getValue(), extendObject));
                    }
                }
            }
        } catch (Exception e) {
            e.getCause().printStackTrace();
        }
    }

    public String getName(Object object) {
        if (modules.containsKey(object)) {
            return object.getClass().getAnnotation(Module.class).value();
        }
        return null;
    }

    public boolean getEnable(Object object) {
        if (modules.containsKey(object)) {
            return object.getClass().getAnnotation(Module.class).enable();
        }
        return false;
    }

    public void setEnable(Object object, boolean value) {
        if (modules.containsKey(object)) {
            try {
                TypeAnnotation(Proxy.getInvocationHandler(object.getClass().getAnnotation(Module.class)), "enable", value);
            } catch (Exception e) {
                e.getCause().printStackTrace();
            }
        }
    }

    public int getKey(Object object) {
        if (modules.containsKey(object)) {
            return object.getClass().getAnnotation(Module.class).key();
        }
        return 0;
    }

    public void setKey(Object object, int value) {
        if (modules.containsKey(object)) {
            try {
                TypeAnnotation(Proxy.getInvocationHandler(object.getClass().getAnnotation(Module.class)), "key", value);
            } catch (Exception e) {
                e.getCause().printStackTrace();
            }
        }
    }

    public Category getCategory(Object object) {
        if (modules.containsKey(object)) {
            return object.getClass().getAnnotation(Module.class).category();
        }
        return Category.NONE;
    }

    public <T> T getValue(Object object, String name) {
        try {
            if (modules.containsKey(object)) {
                for (ValueBean valueBean : modules.get(object)) {
                    if (valueBean.getName().equals(name)) {
                        return (T) valueBean.getField().get(valueBean.getObject());
                    }
                }
            }
        } catch (Exception e) {
            e.getCause().printStackTrace();
        }
        return null;
    }

    public <T> void setValue(Object object, String name, T value) {
        try {
            if (modules.containsKey(object)) {
                for (ValueBean valueBean : modules.get(object)) {
                    if (valueBean.getName().equals(name)) {
                        valueBean.getField().set(valueBean.getObject(), value);
                    }
                }
            }
        } catch (Exception e) {
            e.getCause().printStackTrace();
        }
    }

    public void enable(Object object) {
        if (modules.containsKey(object)) {
            Class<?> type = object.getClass();
            setEnable(object, !getEnable(object));
            for (Method method : type.getDeclaredMethods()) {
                method.setAccessible(true);
                try {
                    if (getEnable(object)) {
                        CF4M.INSTANCE.event.register(object);
                        if (method.isAnnotationPresent(Enable.class)) {
                            method.invoke(object);
                        }
                    } else {
                        CF4M.INSTANCE.event.unregister(object);
                        if (method.isAnnotationPresent(Disable.class)) {
                            method.invoke(object);
                        }
                    }
                } catch (Exception e) {
                    e.getCause().printStackTrace();
                }
            }
        }
    }

    @Event
    private void onKey(KeyboardEvent keyboardEvent) {
        for (Object module : getModules()) {
            if (getKey(module) == keyboardEvent.getKey()) {
                enable(module);
            }
        }
    }

    private void TypeAnnotation(InvocationHandler invocationHandler, String name, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field memberValues = invocationHandler.getClass().getDeclaredField("memberValues");
        memberValues.setAccessible(true);
        Map<String, Object> map = (Map<String, Object>) memberValues.get(invocationHandler);
        map.put(name, value);
    }

    public Set<Object> getModules() {
        return modules.keySet();
    }

    public ArrayList<Object> getModules(Category category) {
        return getModules().stream().filter(module -> !getCategory(module).equals(category)).collect(Collectors.toCollection(Lists::newArrayList));
    }

    public Object getModule(String name) {
        for (Object module : getModules()) {
            if (getName(module).equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }
}
