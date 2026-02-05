package com.task.itemapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.task.itemapi.model.Item;

@Service
public class ItemService {
	private final List<Item> itemlist=new ArrayList<>();
	
	private Long idCounter = 1L;

    public Item addItem(Item item) {
        item.setId(idCounter++);
        itemlist.add(item);
        return item;
    }

    public Optional<Item> getItemById(Long id) {
        return itemlist.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }
}
	

