package com.isljes.shipments.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shipments_history")
public class ShipmentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "shipment_id")
    @JsonIgnore
    private Shipment shipment;

    @OneToOne
    @JoinColumn(name = "office_id")
    private PostOffice postOffice;

    @Enumerated(EnumType.STRING)
    private Event event;

    private Timestamp time;

}
