package com.projects.qoderoom.business.logic;

import com.projects.qoderoom.business.model.Device;

import java.util.LinkedList;
import java.util.List;

/**
 * Validates devices. Checks if every device name contains a valueToFilter word
 */
public class DeviceValidator {

    private static String valueToFilter;

    public static List<Device> validate(List<Device> devices){
        List<Device> validatedDevices = new LinkedList<>();
        for(Device d : devices){
            if(isValid(d)){
                validatedDevices.add(d);
            }
        }

        return validatedDevices;
    }

    public static boolean isValid(Device device){
        return device.getName().toLowerCase().contains(valueToFilter);
    }

    public static void setValueToFilter(String valueToFilter) {
        DeviceValidator.valueToFilter = valueToFilter;
    }
}
