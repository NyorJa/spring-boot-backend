package org.trebol.jpa.services.crud;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.trebol.exceptions.BadInputException;
import org.trebol.jpa.entities.ProductCategory;
import org.trebol.jpa.repositories.IProductsCategoriesJpaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.trebol.testhelpers.ProductCategoriesTestHelper.*;

@ExtendWith(MockitoExtension.class)
class ProductCategoriesJpaCrudServiceImplTest {
  @InjectMocks ProductCategoriesJpaCrudServiceImpl instance;
  @Mock IProductsCategoriesJpaRepository categoriesRepositoryMock;

  @Test
  void sanity_check() {
    assertNotNull(instance);
  }

  @Test
  void finds_by_code() throws BadInputException {
    resetProductCategories();
    when(categoriesRepositoryMock.findByCode(
        productCategoryPojoForFetch().getCode())).thenReturn(Optional.of(productCategoryEntityAfterCreation()));

    Optional<ProductCategory> match = instance.getExisting(productCategoryPojoForFetch());

    assertTrue(match.isPresent());
    verify(categoriesRepositoryMock).findByCode(productCategoryPojoForFetch().getCode());
    assertEquals(match.get().getId(), productCategoryEntityAfterCreation().getId());
    assertEquals(match.get().getCode(), productCategoryEntityAfterCreation().getCode());
    assertEquals(match.get().getName(), productCategoryEntityAfterCreation().getName());
  }
}