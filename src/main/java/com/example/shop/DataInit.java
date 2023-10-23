package com.example.shop;

import com.example.shop.constant.ItemSellStatus;
import com.example.shop.constant.Role;
import com.example.shop.dto.MemberFormDto;
import com.example.shop.entity.Item;
import com.example.shop.entity.Member;
import com.example.shop.repository.ItemRepository;
import com.example.shop.repository.MemberRepository;
import com.example.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

// 임시 데이터
@RequiredArgsConstructor
//@Component
public class DataInit implements CommandLineRunner {

    private final ItemRepository itemRepository;


    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }
    @Override
    public void run(String... args) throws Exception {

        // Member Mock
        {
            Member admin = Member.createMember(MemberFormDto.builder()
                    .email("admin@test.com")
                    .name("관리자")
                    .password("1234")
                    .build(), passwordEncoder);
            admin.setRole(Role.ADMIN);

            memberService.saveMember(admin);

            Member test_user = Member.createMember(MemberFormDto.builder()
                    .email("user1@test.com")
                    .name("테스트사용자")
                    .password("1234")
                    .build(), passwordEncoder);

            memberService.saveMember(test_user);

        }
        // Item Mock
        {
            Item item = new Item();
            item.setItemNm("상품명");
            item.setItemDetail("상세");
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setPrice(1000);
            item.setStockNumber(100);

            itemRepository.save(item);

            Item item2 = new Item();
            item2.setItemNm("상품명 2");
            item2.setItemDetail("상세");
            item2.setItemSellStatus(ItemSellStatus.SELL);
            item2.setPrice(1000);
            item2.setStockNumber(100);
            item2.setRegTime(LocalDateTime.now());
            item2.setUpdateTime(LocalDateTime.now());

            itemRepository.save(item2);
        }
    }
}
