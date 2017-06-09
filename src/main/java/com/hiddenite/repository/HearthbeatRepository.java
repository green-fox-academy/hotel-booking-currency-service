package com.hiddenite.repository;

import com.hiddenite.model.Hearthbeat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface HearthbeatRepository extends CrudRepository<Hearthbeat, Long> {

  public ArrayList<Hearthbeat> findAll();
}
