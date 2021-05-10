package com.example.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.backend.models.Review;

public interface ReviewRepository extends CrudRepository<Review, Integer> {

}
