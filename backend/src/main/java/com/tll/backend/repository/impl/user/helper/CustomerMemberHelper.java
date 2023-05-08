package com.tll.backend.repository.impl.user.helper;

import com.tll.backend.repository.impl.user.CustomerRepository;
import com.tll.backend.repository.impl.user.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerMemberHelper {

    private final CustomerRepository customerRepository;
    private final MemberRepository memberRepository;

    public int getLargestId() {
        return Math.max(customerRepository.getLargestId(), memberRepository.getLargestId());
    }


}
