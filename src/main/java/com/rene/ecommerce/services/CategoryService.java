package com.rene.ecommerce.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rene.ecommerce.domain.Category;
import com.rene.ecommerce.domain.dto.CategoryDTO;
import com.rene.ecommerce.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired	private CategoryRepository categoryRepo;
	

	public Category findById(Integer id) {
		Optional<Category> obj = categoryRepo.findById(id);

		try {
			return obj.get();
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException();
		}

	}

	@Transactional
	public Category insert(Category obj) {
		obj.setId(null);
		return categoryRepo.save(obj);

	}

	@Transactional
	public Category update(Category obj) {
		return categoryRepo.save(obj);
	}

	public void delete(Integer id) {
		findById(id);

		try {
			categoryRepo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("You can't delete this object");
		}
	}

	public List<Category> findAll() {
		CategoryDTO dto = new CategoryDTO();
		
		return categoryRepo.findAll();
	}
	
	public List<Category> findAllByIds(List<Integer> ids){
		return categoryRepo.findAllById(ids);
	}
	
    public Page<Category> findPage(Integer page, Integer line_per_page, String orderBy, String direction){
        PageRequest page_request = PageRequest.of(page, line_per_page, Direction.valueOf(direction), orderBy);
        
        return categoryRepo.findAll(page_request);
    }

}
