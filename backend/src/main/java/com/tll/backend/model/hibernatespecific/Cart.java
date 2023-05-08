package com.tll.backend.model.hibernatespecific;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Cart")
@NoArgsConstructor
public class Cart {

    @Id
    private Integer id;
    @Column(name = "id_barang")
    private Integer idBarang;
    @Column(name = "jumlah")
    private Integer jumlahBarang;
    @Column(name = "id_bill")
    private Integer idBill;

}
