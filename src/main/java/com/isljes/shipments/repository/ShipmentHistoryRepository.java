package com.isljes.shipments.repository;

import com.isljes.shipments.model.ShipmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentHistoryRepository extends JpaRepository<ShipmentHistory, Long> {

    List<ShipmentHistory> findAllByShipmentIdOrderById(Long id);
}
