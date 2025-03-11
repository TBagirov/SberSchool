package org.bagirov.twenty_second_hw.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.bagirov.twenty_second_hw.dto.SensorDto;
import org.bagirov.twenty_second_hw.models.Sensor;
import org.bagirov.twenty_second_hw.services.SensorService;
import org.bagirov.twenty_second_hw.utill.Sensor.SensorErrorResponse;
import org.bagirov.twenty_second_hw.utill.Sensor.SensorNotCreatedException;
import org.bagirov.twenty_second_hw.utill.Sensor.SensorValidator;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
@Tag(name = "Sensor Controller", description = "Контроллер для взаимодействия с сенсором")
public class SensorController {
    private final SensorService sensorService;
    SensorValidator sensorValidator;
    private final ModelMapper modelMapper;

    private static final Logger log = LoggerFactory.getLogger(SensorController.class);

    @Autowired
    public SensorController(SensorService sensorService, SensorValidator sensorValidator, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    @Operation(
            summary = "Регистрация сенсора",
            description = "Регистрация сенсора с указанным наименованием"
    )
    public ResponseEntity<?> registerSensor(
            @RequestBody @Valid @Parameter(description = "Наименование сенсора") SensorDto sensorDto,
            BindingResult bindingResult) {

        log.info("Получен запрос на регистрацию сенсора: {}", sensorDto);

        if (bindingResult.hasErrors()) {
            StringBuilder errMsg = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> {
                errMsg.append(error.getDefaultMessage()).append("; ");
                log.error("Ошибка валидации: {}", error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(new SensorErrorResponse(errMsg.toString(), LocalDateTime.now()));
        }

        Sensor sensor = convertToSensor(sensorDto);

        if (sensorService.findByName(sensor.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new SensorErrorResponse("Сенсор с таким именем уже существует.", LocalDateTime.now()));
        }

        sensorService.save(sensor);
        log.info("Сенсор '{}' успешно зарегистрирован!", sensor.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    @Operation(
            summary = "Получение всех сенсоров",
            description = "Получение данных о всех сенсорах имеющихся в БД"
    )
    public List<SensorDto> getSensors(){
        return sensorService.findAll().stream()
                .map(this::convertToSensorDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение сенсора по id",
            description = "Получение данных о конекртном сенсоре по указанному id"
    )
    public SensorDto getSensor(
            @PathVariable("id") @Parameter(description = "id сенсора чьи данные хотим получить") int id
    ){
        return convertToSensorDto(sensorService.findOne(id));
    }

    public Sensor convertToSensor(SensorDto sensorDto){
        return modelMapper.map(sensorDto, Sensor.class);
    }

    public SensorDto convertToSensorDto(Sensor sensor){
        return modelMapper.map(sensor, SensorDto.class);
    }

    @ExceptionHandler(SensorNotCreatedException.class)
    public ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException ex) {
        SensorErrorResponse sensorErrorResponse = new SensorErrorResponse(
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(sensorErrorResponse, HttpStatus.CONFLICT); // Изменили на 409
    }
}
