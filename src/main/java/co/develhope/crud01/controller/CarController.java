package co.develhope.crud01.controller;

import co.develhope.crud01.entity.Car;
import co.develhope.crud01.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    CarRepository carRepository;

    //crea una nuova car nella tabella
    @PostMapping("/create")
    public Car createCar(@RequestBody Car car) {
        Car car1 = carRepository.save(car);
        return car;
    }

    //restituisce la lista di tutte le Car
    @GetMapping("/get/all")
    public Page<Car> getAllCars(@RequestParam(required = false) Optional<Integer> page, @RequestParam(required = false) Optional<Integer> size) {
        if (page.isPresent() && size.isPresent()) {
            Pageable pageable = PageRequest.of(page.get(), size.get());
            return carRepository.findAll(pageable);
        }
        else {
            return Page.empty();
        }
}

    //restituisce una singola Car -
    // se id non è presente in db,
    // restituisce Car vuota
    @GetMapping("/get/{id}")
    public Car getCarById(@PathVariable Long id) {
        return carRepository.findById(id).orElse(new Car());
        }

    //aggiorna type della Car specifica,
    // identificata da id e passando query param -
    // se id non è presente in db (usa existsById()),
    // restituisce Car vuota
    @PutMapping("/update/{id}")
    public ResponseEntity<Car> updateCarType(@PathVariable Long id, @RequestBody String type) { //aggiorna tipo auto con id
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            car.setType(type);
            carRepository.saveAndFlush(car);
            return new ResponseEntity<>(car, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //cancella la Car specifica -
    // se non presente, la risposta deve avere Conflict HTTP status

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteCarById(@PathVariable Long id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    //cancella tutte le Cars in db
    @DeleteMapping("delete/all")
    public ResponseEntity<String> deleteAllCars() {
        carRepository.deleteAll();
        return ResponseEntity.ok("successfully deleted all cars");
    }

}
