package com.example.shop.service;

import com.example.shop.dto.ItemFormDto;
import com.example.shop.dto.ItemImgDto;
import com.example.shop.dto.ItemSearchDto;
import com.example.shop.entity.Item;
import com.example.shop.entity.ItemImg;
import com.example.shop.repository.ItemImgRepository;
import com.example.shop.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Transactional
@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemImgService itemImgService;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws IOException {
        // 상품등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if (i == 0) {
                itemImg.setRepImgYn("Y");
            } else {
                itemImg.setRepImgYn("N");
            }
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));

        }
        return item.getId();

    }

    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId) {

        // 아이템 이미지 조회 및 아이템 이미지 dto 생성
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }
        // 아이템 조회 및 아이템 dto 생성
        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }

    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
        //상품 수정
        Item item = itemRepository.findById(itemFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);
        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        //이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i),
                    itemImgFileList.get(i));
        }

        return item.getId();
    }

    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }
}
