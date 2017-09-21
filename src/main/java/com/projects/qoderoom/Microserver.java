package com.projects.qoderoom;

import com.projects.qoderoom.api.DeviceController;
import com.projects.qoderoom.business.logic.DeviceValidator;
import com.projects.qoderoom.business.logic.parser.DeviceParser;
import com.projects.qoderoom.business.model.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.GenericXmlApplicationContext;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class Microserver {

    private static final Logger log = LoggerFactory.getLogger(Microserver.class);
    public static GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:Beans.xml");

    public static void main(String[] args) {
        init();

        SpringApplication.run(Microserver.class, args);
    }

    private static void init(){
        log.info("Initializing application");
        DeviceValidator.setValueToFilter(ctx.getBean("valueToFilter", String.class));

        DeviceParser parser = ctx.getBean("rozetkaDeviceParser", DeviceParser.class);
        DeviceRepository dao = ctx.getBean("deviceRepository", DeviceRepository.class);

        dao.putAll(DeviceValidator.validate(parser.parse()));
        DeviceController.setDeviceRepository(dao);
    }
}
