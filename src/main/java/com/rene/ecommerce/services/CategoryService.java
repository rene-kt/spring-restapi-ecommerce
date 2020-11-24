package com.rene.ecommerce.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.rene.ecommerce.domain.Category;
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
		return categoryRepo.findAll();
	}
	
	public List<Category> findAllByIds(List<Integer> ids){
		return categoryRepo.findAllById(ids);
	}
	
  

}
