package com.example.shop.entity;

import com.example.shop.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@TestPropertySource("classpath:application.properties")
class MemberTest {

    @Autowired
    MemberRepository memberRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    @WithMockUser(username = "glidong", roles = "USER")
    void name() {
        Member member = new Member();
        memberRepository.save(member);

        em.flush();
        em.clear();

        Member findMember = memberRepository.findById(member.getId()).orElseThrow(EntityNotFoundException::new);

        System.out.println(findMember.toString());
        System.out.println(findMember.getCreatedBy());
        System.out.println(findMember.getModifiedBy());
        System.out.println(findMember.getRegTime());
        System.out.println(findMember.getUpdateTime());
    }
}