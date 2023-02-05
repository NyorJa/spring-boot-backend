/*
 * Copyright (c) 2023 The Trebol eCommerce Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished
 * to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.trebol.jpa.services.conversion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trebol.api.models.ProductCategoryPojo;
import org.trebol.common.exceptions.BadInputException;
import org.trebol.jpa.entities.ProductCategory;

@Transactional
@Service
public class ProductCategoriesConverterServiceImpl
  implements ProductCategoriesConverterService {

  @Autowired
  public ProductCategoriesConverterServiceImpl() {
  }

  @Override
  public ProductCategoryPojo convertToPojo(ProductCategory source) {
    return ProductCategoryPojo.builder()
      .id(source.getId())
      .code(source.getCode())
      .name(source.getName())
      .build();
  }

  @Override
  public ProductCategory convertToNewEntity(ProductCategoryPojo source) {
    ProductCategory target = new ProductCategory();
    target.setCode(source.getCode());
    target.setName(source.getName());
    return target;
  }

  @Override
  public ProductCategory applyChangesToExistingEntity(ProductCategoryPojo source, ProductCategory target) throws BadInputException {
    throw new UnsupportedOperationException("This method is deprecated");
  }
}
