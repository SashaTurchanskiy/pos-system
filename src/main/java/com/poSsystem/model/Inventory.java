package com.poSsystem.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventories")
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Branch branch;
    @ManyToOne
    private Product product;
    @Column(nullable = false)
    private Integer quantity;
    @LastModifiedDate
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    @PrePersist
    @PreUpdate
    protected void OnUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }

}
