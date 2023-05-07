package com.tll.backend.model.bill;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tll.backend.datastore.loader.helper.FixedBillDeserializer;
import com.tll.backend.datastore.loader.helper.FixedBillSerializer;
import com.tll.backend.model.barang.Barang;
import com.tll.backend.repository.CloneableObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.javatuples.Pair;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "FixedBill")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@JsonSerialize(using = FixedBillSerializer.class)
@JsonDeserialize(using = FixedBillDeserializer.class)
@NoArgsConstructor
public class FixedBill extends Bill implements CloneableObject<FixedBill> {
    @Getter
    @Setter(AccessLevel.PRIVATE)
    @Column(name = "id_user")
    private Integer userId;

    public FixedBill(Integer id, Integer userId, List<Pair<Barang, Integer>> listBarang) {
        super(id, listBarang.stream()
                .map(el -> Pair.with(el.getValue0().clone(), el.getValue1()))
                .collect(Collectors.toList()));
        this.userId = userId;
    }

    @Override
    public FixedBill clone() {
        try {
            var fixedCart = this.cart.stream()
                    .map(el -> Pair.with(el.getValue0().clone(), el.getValue1()))
                    .toList();

            return ((FixedBill) ((Bill) super.clone())
                    .setId(this.id)
                    .setCart(fixedCart))
                    .setUserId(this.userId);
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
