package org.bagirov.twenty_second_hw.services;

import org.bagirov.twenty_second_hw.models.Measurement;
import org.bagirov.twenty_second_hw.models.Sensor;
import org.bagirov.twenty_second_hw.repositories.MeasurementRepository;
import org.bagirov.twenty_second_hw.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementService(final MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    public Measurement findOne(int id) {
        return measurementRepository.findById(id).orElse(null);
    }

    public List<Measurement> findByRainyDaysCount() {
        return measurementRepository.findByRainingEquals(true);
    }

    @Transactional
    public int save(Measurement measurement) {
        Sensor sensor = sensorRepository.findByName(measurement.getSensor().getName());

        measurement.setSensor(sensor);
        enrichMeasurement(measurement);

        Measurement meas = measurementRepository.save(measurement);
        sensor.getMeasurements().add(meas);
        return meas.getId();
    }

    public void enrichMeasurement(Measurement measurement) {
        measurement.setCreatedAt(LocalDateTime.now());
    }
}
