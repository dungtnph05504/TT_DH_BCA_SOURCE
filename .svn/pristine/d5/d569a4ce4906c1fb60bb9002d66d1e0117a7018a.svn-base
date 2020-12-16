package com.nec.asia.nic.comp.trans.utils;

/**
 * The Passport Type.
 * 
 * @author khang
 *
 */
/*
 * Modification History:
 * 31 May 2016 (khang): Update passport type to support:
 *                      PS-MRCTD Stateless
 *                      PR-MRCTD Refugees
 * 
 */
public enum PassportType {
    REGULAR("P"), OFFICIAL("PO"), DIPLOMATIC("PD"), MRP("MRP-P"), HAJJ("HAJI-P"), MRCTDS("PS"), MRCTDR("PR");
    private final String value;

    PassportType(String v) {
        value = v;
    }
    
    public String value() {
        return value;
    }
    
    public static PassportType fromValue(String v) {
        for (PassportType c : PassportType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
    
}
