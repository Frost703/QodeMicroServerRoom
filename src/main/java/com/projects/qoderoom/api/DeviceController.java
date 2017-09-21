package com.projects.qoderoom.api;

import com.projects.qoderoom.business.logic.DeviceValidator;
import com.projects.qoderoom.business.model.Device;
import com.projects.qoderoom.business.model.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Provides limited features through api
 */

@RestController
@CrossOrigin
@Transactional
public class DeviceController {

    private static final Logger log = LoggerFactory.getLogger(DeviceController.class);

    private static DeviceRepository deviceRepository;

    public static void setDeviceRepository(DeviceRepository deviceRepository) {
        DeviceController.deviceRepository = deviceRepository;
    }

    /**
     * Provides a device object
     *
     * @param id of the device
     * @return BAD_REQUEST when id < 1, OK when success
     */
    @RequestMapping(path = "/device", method = RequestMethod.GET)
    public ResponseEntity<?> getDevice(@RequestParam int id){
        if(id < 1){
            log.debug("Received a bad id={} in get request on /device endpoint", id);
            return new ResponseEntity<>("id < 1", HttpStatus.BAD_REQUEST);
        }

        System.out.println("Getting device with id=" + id);

        log.debug("Returning a device with id={}", id);
        Device device = deviceRepository.getAll().get(1);

        System.out.println("Received a device with name="+device.getName());
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    /**
     * Inserts new valid device into DB
     *
     * @param price of the device
     * @param reviews amount on the device
     * @param name of the device
     * @param category of the device
     * @return BAD_REQUEST when parameters are missing, UNPROCESSABLE_ENTITY when device is not valid, OK when success
     */
    @RequestMapping(path = "/device", method= RequestMethod.POST)
    public ResponseEntity<?> putDevice(@RequestParam double price,
                                    @RequestParam int reviews,
                                    @RequestParam String name,
                                    @RequestParam String category){

        if(price < 1d || reviews < 1 || name == null || category == null){
            log.debug("Received a bad post request with missing parameters on /device endpoint");
            return new ResponseEntity<>("Requested parameters are missing", HttpStatus.BAD_REQUEST);
        }

        Device device = new Device(price, reviews, name, category);

        if(DeviceValidator.isValid(device)){
            deviceRepository.put(device);
        } else {
            log.debug("Refused to accept device. Device is not valid");
            return new ResponseEntity<>("This device is not valid", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        log.debug("Added a new device with name={}", device.getName());
        return new ResponseEntity<>("Device was added", HttpStatus.OK);
    }

    /**
     * Deletes the device from DB
     *
     * @param price of the device
     * @param reviews amount on the device
     * @param name of the device
     * @param category of the device
     * @return BAD_REQUEST when parameters are missing, OK when success
     */
    @RequestMapping(path = "/device", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDevice(@RequestParam double price,
                                          @RequestParam int reviews,
                                          @RequestParam String name,
                                          @RequestParam String category){
        if(price < 1d || reviews < 1 || name == null || category == null){
            log.debug("Received a bad delete request with missing parameters on /device endpoint");
            return new ResponseEntity<>("Requested parameters are missing", HttpStatus.BAD_REQUEST);
        }

        log.debug("Successfully deleted a device with name={}", name);
        return new ResponseEntity<>(deviceRepository.delete(new Device(price, reviews, name, category)), HttpStatus.OK);
    }

        /**
     * Updates a device in DB
     *
     * @param price of the device
     * @param reviews amount on the device
     * @param name of the device
     * @param category of the device
     * @return BAD_REQUEST when parameters are missing, OK when success
     */
    @RequestMapping(path="/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateDevice(@RequestParam double price,
                                          @RequestParam int reviews,
                                          @RequestParam String name,
                                          @RequestParam String category){
        if(price < 1d || reviews < 1 || name == null || category == null){
            log.debug("Received a bad post request with missing parameters on /update endpoint");
            return new ResponseEntity<>("Requested parameters are missing", HttpStatus.BAD_REQUEST);
        }

        log.debug("Updated a device with name={}", name);
        return new ResponseEntity<>(deviceRepository.update(new Device(price, reviews, name, category)), HttpStatus.OK);
    }

}
