package com.example.shoes.backend.services;

import com.example.shoes.backend.model.Shoes;
import com.example.shoes.backend.repositories.ShoesRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class ShoesService {
    private ShoesRepository shoesRepository;

    public ShoesService(ShoesRepository shoesRepository) {
        this.shoesRepository = shoesRepository;
    }

    public void storeShoes(@RequestBody Shoes shoes) {
        shoesRepository.storeShoes(shoes);
    }

    public void saveShoes(@RequestBody Shoes shoes) {
        shoesRepository.saveShoes(shoes);
    }

    public List<Shoes> findAllShoes() {
        return shoesRepository.findAllShoes();
    }

    public List<Shoes> findAllShoes(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty())
            return shoesRepository.findAllShoes();
        else
            return shoesRepository.searchShoes(stringFilter);
    }

    public void deleteShoes(@RequestBody Shoes shoes) {
        shoesRepository.deleteShoes(shoes);
    }

    public int countShoes() {
        return shoesRepository.countShoes();
    }
}
