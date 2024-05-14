package co.develhope.crud01.repository;

import co.develhope.crud01.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
