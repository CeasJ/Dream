package com.backend.dream.service.imp;

import com.backend.dream.dto.CategoryDTO;
import com.backend.dream.entity.Category;
import com.backend.dream.mapper.CategoryMapper;
import com.backend.dream.repository.CategoryRepository;
import com.backend.dream.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImp(CategoryRepository categoryRepository, CategoryMapper categoryMapper){
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDTO findById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return category != null ? categoryMapper.categoryToCategoryDTO(category) : null;
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {
        Category category = categoryMapper.categoryDTOToCategory(categoryDTO);
        Category createdCategory = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryDTO(createdCategory);
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO) {
        Category category = categoryMapper.categoryDTOToCategory(categoryDTO);
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryDTO(updatedCategory);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }


//    @Override
//    public Category createCategory(JsonNode cate) {
//        ObjectMapper mapper = new ObjectMapper();
//        Category newCategory = mapper.convertValue(cate, Category.class);
//        String name = cate.get("name").asText();
//        newCategory.setName(name);
//        return newCategory;
//    }


//    @Override
//    public boolean checknameExists(String namecategory) {
//        return categoryRepository.findByname("name");
//    }


    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList());
    }
}
