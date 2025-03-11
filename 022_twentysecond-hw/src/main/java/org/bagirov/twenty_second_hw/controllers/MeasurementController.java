package org.bagirov.twenty_second_hw.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.bagirov.twenty_second_hw.dto.MeasurementDto;
import org.bagirov.twenty_second_hw.dto.SensorDto;
import org.bagirov.twenty_second_hw.models.Measurement;
import org.bagirov.twenty_second_hw.models.Sensor;
import org.bagirov.twenty_second_hw.utill.Measurement.MeasurementErrorResponse;
import org.bagirov.twenty_second_hw.utill.Measurement.MeasurementNotCreatedException;
import org.bagirov.twenty_second_hw.services.MeasurementService;
import org.bagirov.twenty_second_hw.utill.Measurement.MeasurementValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/measurements")
@Tag(name = "Measurement Controller", description = "Контроллер для взаимодействия с измерениями сенсоров")
public class MeasurementController {

    private MeasurementService measurementService;
    MeasurementValidator measurementValidator;
    private ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService,
                                 MeasurementValidator measurementValidator,
                                 ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.measurementValidator = measurementValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    @Operation(
            summary = "Добавление измерения сенсора",
            description = "Добавление измерения сенсора по заданному наименованию"
    )
    public ResponseEntity<HttpStatus> addMeasurement(
            @RequestBody @Valid @Parameter(description = "Данные измерения включающие наименование сенсора, который произвел измерение") MeasurementDto measurementDto,
            BindingResult bindingResult) {

        Measurement measurement = convertToMeasurement(measurementDto);

        measurementValidator.validate(measurement, bindingResult);

        if(bindingResult.hasErrors()){
            StringBuilder errMsg = new StringBuilder();
            for(FieldError fieldError : bindingResult.getFieldErrors()) {
                errMsg.append(fieldError.getField())
                        .append(" - ")
                        .append(fieldError.getDefaultMessage())
                        .append("; ");
            }
            throw new MeasurementNotCreatedException(errMsg.toString());
        }

        measurementService.save(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping()
    @Operation(
            summary = "Получение всех измерений",
            description = "Получение данных о всех добавленных измерениях"
    )
    public List<MeasurementDto> getAllMeasurements() {
        return measurementService.findAll().stream().map(this::convertToMeasurementDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    @Operation(
            summary = "Получение количества дождливых дней",
            description = "Получение количества дней, в которые какой-либо сенсор зарегестрировал дождь"
    )
    public int getRainyDaysCount() {
        return measurementService.findByRainyDaysCount().size();
    }

    public Measurement convertToMeasurement(MeasurementDto measurementDto){
        Measurement measurement = modelMapper.map(measurementDto, Measurement.class);
        Sensor sensor = modelMapper.map(measurementDto.getSensorDto(), Sensor.class);

        measurement.setSensor(sensor);

        return measurement;
    }

    public MeasurementDto convertToMeasurementDto(Measurement measurement){
        MeasurementDto measurementDto = modelMapper.map(measurement, MeasurementDto.class);
        SensorDto sensorDto = modelMapper.map(measurement.getSensor(), SensorDto.class);

        measurementDto.setSensorDto(sensorDto);

        return measurementDto;
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> exceptionHandler(MeasurementNotCreatedException ex){
        MeasurementErrorResponse measurementErrorResponse = new MeasurementErrorResponse(
            ex.getMessage(),
            LocalDateTime.now()
        );

        return new ResponseEntity<>(measurementErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
