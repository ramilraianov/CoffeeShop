package ru.ufanet.coffeeshop.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ufanet.coffeeshop.models.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

}
