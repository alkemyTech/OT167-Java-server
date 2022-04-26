package com.alkemy.ong.service.impl;
import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.exception.IncorrectPatternExeption;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.exception.MessagePag;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.exception.PaginationMessage;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.context.request.WebRequest;

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
    private static final int SIZE_PAG_10 = 10;
    @Autowired
    private PaginationMessage paginationMessage;

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
    public CategoryDto save(CategoryDto categoryDto) throws DataAlreadyExistException, IncorrectPatternExeption {
        Category categorySaved = null;
        Category entity = categoryMapper.categoryDto2Entity(categoryDto);
        try{
            if(categoryRepository.findByName(entity.getName()) == null){
                this.validate(entity.getName());
                categorySaved = categoryRepository.save(entity);
            }
        }catch (Exception ex) {
            throw new DataAlreadyExistException(messageSource.getMessage("category.already.exist", null, Locale.ENGLISH));
        }
        return categoryMapper.categoryEntity2Dto(categorySaved);
    }
    @Override
    public Optional<Category> findById(Long id) {
        if (categoryRepository.findById(id).isEmpty()) {
            throw new NotFoundException(messageSource.getMessage("not.found", null, Locale.ENGLISH));
        }
        return categoryRepository.findById(id);
    }
    public Category updateCategory(Long id, CategoryDto categoryDto ) throws IncorrectPatternExeption {
        Category categoryExist = categoryRepository.findById(id).get();
        Category categoryEdited;

        if (categoryExist != null){
            categoryDto.setId(categoryExist.getIdCategories());
            categoryEdited = categoryMapper.categoryDto2Entity(categoryDto);
            return categoryRepository.save(categoryEdited);
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

    public String validate(String parameter) throws IncorrectPatternExeption {
        boolean valid = parameter.matches("^[a-zA-Z]+$");
        if(!valid) {
            throw new IncorrectPatternExeption(messageSource.getMessage("category.data.incorrect", new Object[]{parameter}, Locale.ENGLISH));
        }
        return parameter;
    }

    @Override
    public MessagePag findAllPag(int page, WebRequest request) {
         Page categoryPage = categoryRepository.findAll(PageRequest.of(page, SIZE_PAG_10));
        return paginationMessage.messageInfo(categoryPage, categoryMapper.categoryList2CategoryDTOList(categoryPage.getContent()), request);
    }
}
