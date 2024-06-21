package com.isljes.shipments.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shipments")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "recipient_index")
    private String recipientIndex;

    @Column(name = "recipient_address")
    private String recipientAddress;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ShipmentType type;

    @OneToOne
    @JoinColumn(name = "status")
    private ShipmentHistory status;


    public enum ShipmentType {
        LETTER,
        PARCEL,
        PACKAGE,
        POSTCARD
    }


}
