package com.br.playmakerhub.repositories;

import com.br.playmakerhub.models.Coach;
import com.br.playmakerhub.models.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRepository extends MongoRepository<Coach, String> {
}
