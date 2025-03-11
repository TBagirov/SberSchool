package org.bagirov.twenty_second_hw.repositories;

import org.bagirov.twenty_second_hw.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

    public List<Measurement> findByRainingEquals(boolean rain);
}
