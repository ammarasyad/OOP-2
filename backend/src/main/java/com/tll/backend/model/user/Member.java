package com.tll.backend.model.user;

import com.tll.backend.model.bill.FixedBill;
import com.tll.backend.repository.StorableObject;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Member implements Serializable, StorableObject<Integer> {
    private final Integer id;
    private String type;
    private boolean activeStatus;
    private String name;
    private String phone;
    private List<FixedBill> bills;
    private long point;

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", orders=" + bills +
                '}';
    }

    public void updateMember(String name, String phone, String type) {
        if (!name.isEmpty()) {
            setName(name);
        }
        if (!phone.isEmpty()) {
            setPhone(phone);
        }
        if (!type.isEmpty()) {
            setType(type);
        }
    }

    public void changeActivation() {
        setActiveStatus(!isActiveStatus());
    }

    public void addBill(FixedBill bill) {
        this.bills.add(bill);
        if (isActiveStatus()) {
            long totalAmount = bill.getTotalPrice().longValue();
            if (getType().equals("Vip")) {
                totalAmount -= totalAmount*0.1;
            }

            if (getPoint() > totalAmount) {
                setPoint(getPoint() - totalAmount);
            }
            else {
                long temp = (totalAmount - getPoint())/100;
                setPoint(temp);
            }
        }

    }
    public void printInfo() {
        System.out.println(getId() + " " + getType() + " " + getName() + " " + getPoint() + " " + activeStatus);
        getBills().forEach(order -> System.out.println("Bill: " ));
    }

}
