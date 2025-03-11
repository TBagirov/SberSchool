package org.bagirov.twenty_second_hw.utill.Measurement;

import org.bagirov.twenty_second_hw.models.Measurement;
import org.bagirov.twenty_second_hw.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

// подумать, может стоит сделать единый валидатор Sensor с несколькими валидациями
// либо же этот валидатор сделать также валидатором Sensor и переименовать их как
// SensorExistValidator и SensorNotExistValidator
@Component
public class MeasurementValidator implements Validator {
    private SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;
        if(!sensorService.findByName(measurement.getSensor().getName()).isPresent()){
            // указываем наименование поля именно в DTO, т.к. мы знаем что валидатор
            // будет использоваться с BindingResult по MeasurementDto, м.б. костыль
            errors.rejectValue("sensorDto", "", "There is no sensor with this name");
        }
    }
}
