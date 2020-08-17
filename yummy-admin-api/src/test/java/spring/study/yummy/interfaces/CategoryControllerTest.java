package spring.study.yummy.interfaces;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import spring.study.yummy.application.CategoryService;
import spring.study.yummy.application.RegionService;
import spring.study.yummy.domain.Category;
import spring.study.yummy.domain.Region;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void list() throws Exception {
        List<Category> categories = new ArrayList<>();
        categories.add(Category.builder().name("Korean Food").build());

        given(categoryService.getCategories()).willReturn(categories);

        mvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Korean Food")));
    }

    @Test
    public void create() throws Exception {
        given(categoryService.addCategory(any())).will(invocation -> {
            Category category = invocation.getArgument(0);

            return Category.builder()
                    .id(1L)
                    .name(category.getName())
                    .build();
        });

        mvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Korean Food\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("{}"))
                .andExpect(header().string("location", "/categories/1"));

        verify(categoryService).addCategory(any());
    }
}