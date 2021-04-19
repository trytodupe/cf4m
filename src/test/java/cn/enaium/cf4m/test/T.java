package cn.enaium.cf4m.test;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.test.config.ConfigTest;
import cn.enaium.cf4m.test.event.EventCancelTest;
import cn.enaium.cf4m.test.event.EventPriorityTest;
import cn.enaium.cf4m.test.event.EventRegisterUnregisterTest;
import cn.enaium.cf4m.test.module.ModuleBeanTest;
import org.junit.jupiter.api.Test;

/**
 * @author Enaium
 */
public class T {
    @Test
    public void test() {
        CF4M.run(this, System.getProperty("user.dir") + "/build/configTest");
        System.out.println("Check ModuleBeanTest Start");
        new ModuleBeanTest();
        System.out.println("Check ModuleBeanTest End");
        System.out.println();
        System.out.println("Check EventRegisterUnregister Start");
        new EventRegisterUnregisterTest();
        System.out.println("Check EventRegisterUnregister End");
        System.out.println();
        System.out.println("Check EventPriorityTest Start");
        new EventPriorityTest();
        System.out.println("Check EventPriorityTest End");
        System.out.println();
        System.out.println("Check EventCancelTest Start");
        new EventCancelTest();
        System.out.println("Check EventCancelTest End");
        System.out.println();
        System.out.println("Check ModuleEnableDisableTest Start");
        CF4M.INSTANCE.getModule().onKey(1);
        CF4M.INSTANCE.getModule().onKey(1);
        System.out.println("Check ModuleEnableDisableTest End");
        System.out.println();
        System.out.println("Check ModuleExtendTest Start");
        CF4M.INSTANCE.getModule().onKey(2);
        CF4M.INSTANCE.getModule().onKey(2);
        System.out.println("Check ModuleExtendTest1 End");
        System.out.println();
        CF4M.INSTANCE.getModule().onKey(3);
        CF4M.INSTANCE.getModule().onKey(3);
        System.out.println("Check ModuleExtendTest1 End");
        System.out.println();
        System.out.println("Check ModuleSettingTest Start");
        CF4M.INSTANCE.getModule().onKey(4);
        System.out.println("Check ModuleSettingTest End");
        System.out.println();
        System.out.println("Check ModuleType Start");
        CF4M.INSTANCE.getModule().onKey(5);
        System.out.println("Check ModuleType End");
        System.out.println();
        System.out.println("Check ModuleAutowiredTest Start");
        CF4M.INSTANCE.getModule().onKey(6);
        System.out.println("Check ModuleAutowiredTest End");
        System.out.println();
        System.out.println("Check CommandTest Start");
        System.out.println(CF4M.INSTANCE.getCommand().execCommand("-t"));
        System.out.println(CF4M.INSTANCE.getCommand().execCommand("-t var1"));
        System.out.println(CF4M.INSTANCE.getCommand().execCommand("-t var1 1"));
        System.out.println("Check CommandTest End");
        System.out.println();
        System.out.println("Check ConfigTest Start");
        new ConfigTest();
        System.out.println("Check ConfigTest End");
    }
}
