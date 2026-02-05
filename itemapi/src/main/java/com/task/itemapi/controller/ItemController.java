package com.task.itemapi.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.itemapi.model.Item;
import com.task.itemapi.service.ItemService;

@RestController
@RequestMapping("/users")
public class ItemController {
	
	@Autowired
	private  ItemService itemservice;
	
	public ItemController(ItemService itemservice) {
		this.itemservice=itemservice;
	}
	
	
	@PostMapping()
    public ResponseEntity<?> addItem(@RequestBody Item item) {

        // STEP 5 — Manual Validation
        Map<String, String> errors = validateItem(item);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Item savedItem = itemservice.addItem(item);
        return ResponseEntity.ok(savedItem);
    }

    // 🔍 Get item by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getItem(@PathVariable Long id) {
        Optional<Item> item = itemservice.getItemById(id);

        if (item.isPresent()) {
            return ResponseEntity.ok(item.get());
        } else {
            return ResponseEntity.status(404).body("Item not found with id: " + id);
        }
    }

    // 🔎 Manual Validation Method
    private Map<String, String> validateItem(Item item) {
        Map<String, String> errors = new HashMap<>();

        if (item.getName() == null || item.getName().trim().isEmpty()) {
            errors.put("name", "Name is required");
        }

        if (item.getDescription() == null || item.getDescription().trim().isEmpty()) {
            errors.put("description", "Description is required");
        }

        if (item.getPrice() == null) {
            errors.put("price", "Price is required");
        } else if (item.getPrice() <= 0) {
            errors.put("price", "Price must be greater than 0");
        }

        return errors;
    }
}
	

