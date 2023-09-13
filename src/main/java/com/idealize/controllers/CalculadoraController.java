package com.idealize.controllers;

import com.idealize.exceptions.ResourceNotFoundException;
import com.idealize.services.CalcularService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.idealize.services.CalcudoraService.*;


@RestController
@RequestMapping("/idealize")
public class CalculadoraController {

    private CalcularService service = new CalcularService();

    @GetMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(@PathVariable String numberOne, @PathVariable String numberTwo) throws Exception {

        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new ResourceNotFoundException("Please set a numeric value");
        }

        return service.sum(convertToNumber(numberOne), convertToNumber(numberTwo));
    }

    @GetMapping("/sub/{numberOne}/{numberTwo}")
    public Double subtraction(@PathVariable String numberOne, @PathVariable String numberTwo) {

        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new ResourceNotFoundException("Please set a numeric value");
        }

        return service.subtraction(convertToNumber(numberOne), convertToNumber(numberTwo));
    }

    @GetMapping("/div/{numberOne}/{numberTwo}")
    public Double division(@PathVariable String numberOne, @PathVariable String numberTwo) {

        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new ResourceNotFoundException("Please set a numeric value");
        }

        return service.division(convertToNumber(numberOne), convertToNumber(numberTwo));
    }

    @GetMapping("/mult/{numberOne}/{numberTwo}")
    public Double multiplication(@PathVariable String numberOne, @PathVariable String numberTwo) {

        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new ResourceNotFoundException("Please set a numeric value");
        }

        return service.multiplication(convertToNumber(numberOne), convertToNumber(numberTwo));
    }

    @GetMapping("/square/{number}")
    public Double squareRoot(@PathVariable String number) {

        if (!isNumeric(number)) {
            throw new ResourceNotFoundException("Please set a numeric value");
        }

        return service.squareRoot(convertToNumber(number));
    }

}