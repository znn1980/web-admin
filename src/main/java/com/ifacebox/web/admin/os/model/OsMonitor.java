package com.ifacebox.web.admin.os.model;

import java.util.List;

/**
 * @author znn
 */
public class OsMonitor {
    private JvmMemory jvmMemory;
    private OsCpu osCpu;
    private OsMemory osMemory;
    private OsDiskSpace osDiskSpace;
    private List<OsDiskSpace> osDiskSpaceList;

    public JvmMemory getJvmMemory() {
        return jvmMemory;
    }

    public void setJvmMemory(JvmMemory jvmMemory) {
        this.jvmMemory = jvmMemory;
    }

    public OsCpu getOsCpu() {
        return osCpu;
    }

    public void setOsCpu(OsCpu osCpu) {
        this.osCpu = osCpu;
    }

    public OsMemory getOsMemory() {
        return osMemory;
    }

    public void setOsMemory(OsMemory osMemory) {
        this.osMemory = osMemory;
    }

    public OsDiskSpace getOsDiskSpace() {
        return osDiskSpace;
    }

    public void setOsDiskSpace(OsDiskSpace osDiskSpace) {
        this.osDiskSpace = osDiskSpace;
    }

    public List<OsDiskSpace> getOsDiskSpaceList() {
        return osDiskSpaceList;
    }

    public void setOsDiskSpaceList(List<OsDiskSpace> osDiskSpaceList) {
        this.osDiskSpaceList = osDiskSpaceList;
    }
}
