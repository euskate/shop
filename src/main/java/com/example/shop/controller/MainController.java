package com.example.shop.controller;

import com.example.shop.dto.ItemSearchDto;
import com.example.shop.entity.MainItemDto;
import com.example.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;
    @GetMapping(value = "/")
    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainItemDto> items =  itemService.getMainItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);

        int maxPage = 5;
        int startPage = (items.getNumber() / maxPage) * maxPage + 1;
        int endPage = 0;

        // 시작 페이지 마지막 페이지 구하기
        if (items.getTotalPages() == 0) {
            endPage = 1;
        } else if (startPage + (maxPage - 1) < items.getTotalPages()) {
            endPage = startPage + maxPage - 1;
        } else {
            endPage = items.getTotalPages();
        }

        model.addAttribute("maxPage", maxPage);
        model.addAttribute("start", startPage);
        model.addAttribute("end", endPage);

        return "main";
    }
}
