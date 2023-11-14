package meu.edu.jo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import meu.edu.jo.entities.Menu;
import meu.edu.jo.services.MenuService;
import meu.edu.jo.common.SystemMessages;
import meu.edu.jo.common.exceptions.CustomException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    public ResponseEntity<?> getAllMenus() {
        try {
            List<Menu> menus = menuService.getAllMenus();
            return ResponseEntity.ok(menus);
        } catch (CustomException e) {
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMenuById(@PathVariable Long id) {
        try {           
            Optional<Menu> menu = menuService.getMenuById(id);
            if(menu.isPresent()) {
                return ResponseEntity.ok(menu.orElse(null));
                
            }else {
                return new ResponseEntity<String>(SystemMessages.NO_RECORDS,HttpStatus.OK);
            }
        } catch (CustomException e) {
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<?> createMenu(@RequestBody Menu menu) {
        try {
            Menu createdMenu = menuService.createMenu(menu);
            return ResponseEntity.ok(createdMenu);
        } catch (CustomException e) {
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMenu(@PathVariable Long id, @RequestBody Menu menu) {
        try {
            Menu updatedMenu = menuService.updateMenu(id, menu);
            return ResponseEntity.ok(updatedMenu);
        } catch (CustomException e) {
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long id) {
        try {
            menuService.deleteMenu(id);
            return new ResponseEntity<String>(SystemMessages.DELETE_MSG,HttpStatus.OK);
        } catch (CustomException e) {
            throw e;
        }
    }
}
