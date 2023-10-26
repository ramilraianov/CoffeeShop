package ru.ufanet.coffeeshop.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ufanet.coffeeshop.models.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

}
