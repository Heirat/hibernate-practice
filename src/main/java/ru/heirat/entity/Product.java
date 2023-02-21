package ru.heirat.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "catalog_table", schema = "public")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer id;
    @NaturalId
    private String name;
    private Double weight;
    private Double price;

    @Override
    public String toString() {
        return name + " вес = " + weight + ", цена = " + price;
    }
}
