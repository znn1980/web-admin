package com.ifacebox.web.admin.os.manager;

import com.ifacebox.web.admin.os.model.JvmMemory;
import com.ifacebox.web.admin.os.model.OsCpu;
import com.ifacebox.web.admin.os.model.OsDiskSpace;
import com.ifacebox.web.admin.os.model.OsMemory;
import com.ifacebox.web.common.model.*;
import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author znn
 */
public class OsMonitorManager {
    private static final OperatingSystemMXBean OS = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    public static OsCpu getOsCpu() {
        return new OsCpu(OS.getSystemCpuLoad() * 100);
    }

    public static List<OsDiskSpace> getDiskSpace() {
        List<OsDiskSpace> diskSpace = new ArrayList<>();
        for (File file : File.listRoots()) {
            diskSpace.add(new OsDiskSpace(file.toString(), file.getTotalSpace(), file.getFreeSpace()));
        }
        return diskSpace;
    }

    public static OsDiskSpace getDiskSpace(List<OsDiskSpace> diskSpaces) {
        OsDiskSpace osDiskSpace = new OsDiskSpace("", 0, 0);
        for (OsDiskSpace diskSpace : diskSpaces) {
            osDiskSpace.setName(osDiskSpace.getName() + diskSpace.getName() + " ");
            osDiskSpace.setTotal(osDiskSpace.getTotal() + diskSpace.getTotal());
            osDiskSpace.setFree(osDiskSpace.getFree() + diskSpace.getFree());
        }
        osDiskSpace.setRate(osDiskSpace.getTotal() == 0 ? 0 : (osDiskSpace.getTotal() - osDiskSpace.getFree()) / osDiskSpace.getTotal() * 100);
        return osDiskSpace;
    }

    public static OsMemory getOsMemory() {
        return new OsMemory(OS.getTotalPhysicalMemorySize(), OS.getFreePhysicalMemorySize());
    }

    public static JvmMemory getJvmMemory() {
        return new JvmMemory(Runtime.getRuntime());
    }

    public static List<SystemProperties> getSystemProperties() {
        List<SystemProperties> systemProperties = new ArrayList<>();
        systemProperties.add(new SystemProperties("运行环境名称", System.getProperty("java.runtime.name")));
        systemProperties.add(new SystemProperties("运行环境版本", System.getProperty("java.version")));
        systemProperties.add(new SystemProperties("运行环境供应商", System.getProperty("java.vendor")));
        systemProperties.add(new SystemProperties("虚拟机名称", System.getProperty("java.vm.name")));
        systemProperties.add(new SystemProperties("虚拟机版本", System.getProperty("java.vm.version")));
        systemProperties.add(new SystemProperties("虚拟机供应商", System.getProperty("java.vm.vendor")));
        systemProperties.add(new SystemProperties("操作系统名称", System.getProperty("os.name")));
        systemProperties.add(new SystemProperties("操作系统架构", System.getProperty("os.arch")));
        systemProperties.add(new SystemProperties("操作系统版本", System.getProperty("os.version")));
        return systemProperties;
    }
}
