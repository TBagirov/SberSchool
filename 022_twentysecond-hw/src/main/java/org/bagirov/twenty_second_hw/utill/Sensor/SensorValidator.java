package org.bagirov.twenty_second_hw.utill.Sensor;

import org.bagirov.twenty_second_hw.models.Sensor;
import org.bagirov.twenty_second_hw.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {
    private SensorService service;

    @Autowired
    public SensorValidator(SensorService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if(service.findByName(sensor.getName()).isPresent())
            errors.rejectValue("name","",
                    "This name is already in use");

    }
}
