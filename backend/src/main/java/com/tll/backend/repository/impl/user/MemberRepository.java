package com.tll.backend.repository.impl.user;

import com.tll.backend.model.bill.FixedBill;
import com.tll.backend.model.user.Customer;
import com.tll.backend.model.user.Member;
import com.tll.backend.model.user.Order;
import com.tll.backend.repository.impl.InMemoryCrudRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MemberRepository extends InMemoryCrudRepository<Integer, Member> {

    public MemberRepository() {
        super(new HashMap<>());
    }

    public List<String> getAllMemberName() {
        return storage.keySet().stream().map(el -> storage.get(el).getName()).toList();
    }

    public int getLargestId() {
        Member largestMember = storage.keySet().stream()
                .map(el -> storage.get(el))
                .max(Comparator.comparing(Member::getId)).orElse(new Member(0));

        return largestMember.getId();
    }

    public Member searchMember(String name) {
        return storage.keySet().stream().map(el -> storage.get(el)).filter(member -> name.equals(member.getName())).findAny().get();
    }

    public void updateMember(String memberName, String newName, String newPhone, String newType) {
        searchMember(memberName).updateMember(newName, newPhone, newType);
    }

    public void addMember(Customer customer, String name, String phone, String type) {
        ArrayList<FixedBill> custBill= new ArrayList<>();
        custBill.add(customer.getBill());
        Member member = new Member(getLargestId()+1, type, true, name, phone, custBill, 0);

        super.save(member);
    }

    public void memberPay(String name, FixedBill bill) {
        searchMember(name).addBill(bill);
    }

    public void changeActivationStatus(String name) {
        searchMember(name).changeActivation();
    }

    public void printAll() {
        storage.keySet().stream()
                .map(el -> storage.get(el)).forEach(el -> el.printInfo());
    }
}
