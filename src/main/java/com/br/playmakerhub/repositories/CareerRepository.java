package com.br.playmakerhub.repositories;

import com.br.playmakerhub.models.Career;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CareerRepository extends MongoRepository<Career, String> {
    @Override
    Optional<Career> findById(@NotNull String id);
}
