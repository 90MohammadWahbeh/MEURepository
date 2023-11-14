package meu.edu.jo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meu.edu.jo.common.SystemMessages;
import meu.edu.jo.common.exceptions.CustomException;
import meu.edu.jo.entities.Menu;
import meu.edu.jo.repositories.MenuRepository;

@Service
public class MenuService {

	@Autowired
	private MenuRepository menuRepository;

	public List<Menu> getAllMenus() {
		List<Menu> menuList = menuRepository.findAll();

		if (!menuList.isEmpty()) {
			return menuList;
		} else {
			throw new CustomException(SystemMessages.NO_RECORDS);
		}
	}

	public Optional<Menu> getMenuById(Long id) {
		try {

			return menuRepository.findById(id);
		} catch (Exception e) {
			throw new CustomException(SystemMessages.NO_RECORDS + e.getMessage());
		}
	}

	public Menu createMenu(Menu menu) {
		try {
			return menuRepository.save(menu);
		} catch (Exception e) {
			throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
		}
	}

	public void deleteMenu(Long id) {
		try {
			menuRepository.deleteById(id);
		} catch (Exception e) {
			throw new CustomException(SystemMessages.OPERATION_FAILED  + e.getMessage());
		}
	}

	public Menu updateMenu(Long id, Menu updatedMenu) {
		try {
			Optional<Menu> optionalMenu = menuRepository.findById(id);

			if (optionalMenu.isPresent()) {
				Menu existingMenu = optionalMenu.get();
				existingMenu.setUserId(updatedMenu.getUserId());
				existingMenu.setArabicDescription(updatedMenu.getArabicDescription());
				existingMenu.setEnglishDescription(updatedMenu.getEnglishDescription());
				return menuRepository.save(existingMenu);
			} else {
				throw new CustomException(SystemMessages.NO_RECORDS + id);
			}
		} catch (Exception e) {
			throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
		}
	}
}
