package com.alkemy.ong.service.impl;
import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private MessageSource messageSource;

    private List<Category> getALLCategories(){
        return categoryRepository.findAll();
    }
    private List<CategoryDto> listAllCategoryDto() {
        List<Category> categories = getALLCategories();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            categoryDtos.add(categoryMapper.categoryEntity2Dto(category));
        }
        return categoryDtos;
    }
    public List<String> getAllCategoriesByName() {
        List<CategoryDto> listAllCategoryDto = listAllCategoryDto();
        return listAllCategoryDto.stream()
                .map(categoryDto ->  categoryDto.getName())
                .collect(Collectors.toList());
    }
    public Category save(Category category) throws DataAlreadyExistException {
        Category categorySaved = null;
        try{
            if(categoryRepository.findByName(category.getName()) == null){
                categorySaved = categoryRepository.save(category);
            }
        }catch (Exception ex) {
            throw new DataAlreadyExistException(messageSource.getMessage("category.already.exist", null, Locale.ENGLISH));
        }
        return categorySaved;
    }
    @Override
    public Optional<Category> findById(Long id) {
        if (categoryRepository.findById(id).isEmpty()) {
            throw new NotFoundException(messageSource.getMessage("not.found", null, Locale.ENGLISH));
        }
        return categoryRepository.findById(id);
    }
    public Category updateCategory(Long id, Category category ){
        Category categoryExist=categoryRepository.findById(id).get();

        if (categoryExist != null){
            category.setIdCategories(categoryExist.getIdCategories());

            return categoryRepository.save(category);
        }else{
            throw new NotFoundException(messageSource.getMessage("category.not.found", null,Locale.ENGLISH));
        }
    }
    @Override
    public void deleteCategoryById(Long id) {
        Optional<Category> category = Optional.ofNullable(categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(messageSource
                .getMessage("category.not.found", new Object[]{id.toString()}, Locale.ENGLISH))));
        categoryRepository.delete(category.get());
    }
}
