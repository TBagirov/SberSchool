package org.bagirov.twenty_second_hw.services;

import org.bagirov.twenty_second_hw.models.Sensor;
import org.bagirov.twenty_second_hw.repositories.SensorRepository;
import org.bagirov.twenty_second_hw.utill.Sensor.SensorNotCreatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository){
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> findAll(){
        return sensorRepository.findAll();
    }

    public Sensor findOne(int id){
        return sensorRepository.findById(id).orElse(null);
    }

    public Optional<Sensor> findByName(String name){
        return Optional.ofNullable(sensorRepository.findByName(name));
    }

    @Transactional
    public void save(Sensor sensor){
        if (sensorRepository.findByName(sensor.getName()) != null) {
            throw new SensorNotCreatedException("Сенсор с именем '" + sensor.getName() + "' уже существует.");
        }
        sensorRepository.save(sensor);
    }



}
