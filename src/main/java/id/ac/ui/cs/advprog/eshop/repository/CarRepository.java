package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.utils.CarIdGenerator;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CarRepository {
    private List<Car> carData = new ArrayList<>();

    public Car create(Car car) {
        if (car.getCarId() == null) {
            car.setCarId(CarIdGenerator.generateId()); // Made CarIdGenerator in utils (SRP)
        }
        if (car.getCarQuantity() < 0) {
            car.setCarQuantity(0);
        }
        if ("".equals(car.getCarName())) {
            car.setCarName("Car name input is empty");
        }
        if ("".equals(car.getCarColor())) {
            car.setCarColor("Car color input is empty");
        }
        carData.add(car);
        return car;
    }

    public List<Car> findAll() {
        return new ArrayList<>(carData); // Return list instead of iterator (SRP)
    }

    public Car findById(String id) {
        for (Car car : carData) {
            if (car.getCarId().equals(id)) {
                return car;
            }
        }
        return null;
    }

    public Car update(String id, Car updatedCar) {
        for (int i = 0; i < carData.size(); i++) {
            Car car = carData.get(i);
            if (car.getCarId().equals(id)) {
                // Update the existing car with the new information
                car.setCarName(updatedCar.getCarName());
                car.setCarColor(updatedCar.getCarColor());
                car.setCarQuantity(updatedCar.getCarQuantity());

                if (updatedCar.getCarQuantity() < 0) {
                    car.setCarQuantity(0);
                }
                if ("".equals(updatedCar.getCarName())) {
                    car.setCarName("Car name input is empty");
                }
                if ("".equals(updatedCar.getCarColor())) {
                    car.setCarColor("Car color input is empty");
                }
                return car;
            }
        }
        return null; // Handle the case where the car is not found
    }

    public void delete(String id) {
        carData.removeIf(car -> car.getCarId().equals(id));
    }
}
