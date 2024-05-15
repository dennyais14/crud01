package co.develhope.crud01.repository;

import co.develhope.crud01.entity.Car;
import co.develhope.crud01.entity.CarType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByType(CarType type);
}
