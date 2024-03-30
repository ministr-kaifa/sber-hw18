package ru.zubkoff.sber.hw18.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.zubkoff.sber.hw18.domain.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

}
