package com.hiddenite.repository;

import com.hiddenite.model.Hearthbeat;
import java.util.ArrayList;
import org.springframework.data.repository.CrudRepository;

public interface HearthbeatRepository extends CrudRepository<Hearthbeat, Long> {

  public ArrayList<Hearthbeat> findAll();
}
