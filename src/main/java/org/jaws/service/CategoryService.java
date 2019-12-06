package org.jaws.service;

import lombok.RequiredArgsConstructor;
import org.jaws.core.dto.CategoryDTO;
import org.jaws.core.exception.ResourceNotFoundException;
import org.jaws.core.mapper.CategoryMapper;
import org.jaws.core.mapper.DataMapper;
import org.jaws.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryDTO findById(Long id){
        return categoryRepository.findById(id)
                .map(categoryMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id" + id + "not found"));
    }
}
