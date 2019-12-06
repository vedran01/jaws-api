package org.jaws.core.mapper;

import org.jaws.core.dto.CategoryDTO;
import org.jaws.model.CategoryDO;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements DataMapper<CategoryDO, CategoryDTO> {

    @Override
    public CategoryDO toDO(CategoryDTO dto, Object... args) {
        return null;
    }

    @Override
    public CategoryDTO toDTO(CategoryDO model, Object... args) {
        return null;
    }
}
