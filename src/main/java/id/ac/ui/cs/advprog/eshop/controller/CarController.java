package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarReadService;
import id.ac.ui.cs.advprog.eshop.service.CarWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/car")
class CarController {
    private final CarReadService carReadService;
    private final CarWriteService carWriteService;

    @Autowired
    public CarController(CarReadService carReadService, CarWriteService carWriteService) {
        this.carReadService = carReadService;
        this.carWriteService = carWriteService;
    }

    @GetMapping("/createCar")
    public String createCarPage(Model model) {
        model.addAttribute("car", new Car());
        return "createCar";
    }

    @PostMapping("/createCar")
    public String createCarPost(@ModelAttribute Car car) {
        carWriteService.create(car);
        return "redirect:listCar";
    }

    @GetMapping("/listCar")
    public String carListPage(Model model) {
        model.addAttribute("cars", carReadService.findAll());
        return "carList";
    }

    @GetMapping("/editCar/{carId}")
    public String editCarPage(@PathVariable String carId, Model model) {
        Car car = carReadService.findById(carId);
        if (car == null) {
            return "redirect:listCar";
        }
        model.addAttribute("car", car);
        return "editCar";
    }

    @PostMapping("/editCar")
    public String editCarPost(@ModelAttribute Car car) {
        carWriteService.update(car.getCarId(), car);
        return "redirect:listCar";
    }

    @PostMapping("/deleteCar")
    public String deleteCar(@RequestParam("carId") String carId) {
        carWriteService.deleteCarById(carId);
        return "redirect:listCar";
    }
}
