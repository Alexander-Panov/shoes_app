package com.example.shoes.backend.controllers;

import com.example.shoes.backend.model.Shoes;
import com.example.shoes.backend.repositories.ShoesRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoes")
public class ShoesController {
    private ShoesRepository shoesRepository;

    public ShoesController(ShoesRepository shoesRepository) {
        this.shoesRepository = shoesRepository;
    }

    @PostMapping
    public void storeShoes(@RequestBody Shoes shoes) {
        shoesRepository.saveShoes(shoes);
    }

    @GetMapping
    public List<Shoes> findAllShoes() {
        return shoesRepository.findAllShoes();
    }

    @DeleteMapping
    public void deleteShoes(@RequestBody Shoes shoes) {
        shoesRepository.deleteShoes(shoes);
    }

    @GetMapping
    @RequestMapping("/count")
    public int countShoes() {
        return shoesRepository.countShoes();
    }

    @GetMapping
    @RequestMapping("/search")
    public List<Shoes> searchShoes(@RequestHeader String stringFilter) {
        return shoesRepository.searchShoes(stringFilter);
    }
}
