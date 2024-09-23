package com.br.playmakerhub.repositories;

import com.br.playmakerhub.models.League;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends MongoRepository<League, String> {
}
