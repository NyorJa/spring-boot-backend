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

package org.trebol.testhelpers;

import org.trebol.jpa.entities.Product;
import org.trebol.pojo.ProductPojo;

/**
 * Builds & caches reusable instances of Product and ProductPojo
 */
public class ProductsTestHelper {
  public static final long PRODUCT_ID = 1L;
  public static final String PRODUCT_NAME = "test product name";
  public static final String PRODUCT_BARCODE = "TESTPROD1";
  public static final String PRODUCT_DESCRIPTION = "test product description";
  public static final int PRODUCT_PRICE = 100;
  public static final int PRODUCT_STOCK = 10;
  public static final int PRODUCT_STOCK_CRITICAL = 1;
  private ProductPojo pojoForFetch;
  private ProductPojo pojoBeforeCreation;
  private ProductPojo pojoAfterCreation;
  private Product entityBeforeCreation;
  private Product entityAfterCreation;

  public void resetProducts() {
    this.pojoForFetch = null;
    this.pojoBeforeCreation = null;
    this.pojoAfterCreation = null;
    this.entityBeforeCreation = null;
    this.entityAfterCreation = null;
  }

  public ProductPojo productPojoForFetch() {
    if (this.pojoForFetch == null) {
      this.pojoForFetch = ProductPojo.builder().barcode(PRODUCT_BARCODE).build();
    }
    return this.pojoForFetch;
  }

  public ProductPojo productPojoBeforeCreation() {
    if (this.pojoBeforeCreation == null) {
      this.pojoBeforeCreation = ProductPojo.builder()
        .name(PRODUCT_NAME)
        .barcode(PRODUCT_BARCODE)
        .description(PRODUCT_DESCRIPTION)
        .price(PRODUCT_PRICE)
        .currentStock(PRODUCT_STOCK)
        .criticalStock(PRODUCT_STOCK_CRITICAL)
        .build();
    }
    return this.pojoBeforeCreation;
  }

  public ProductPojo productPojoAfterCreation() {
    if (this.pojoAfterCreation == null) {
      this.pojoAfterCreation = ProductPojo.builder()
        .id(PRODUCT_ID)
        .name(PRODUCT_NAME)
        .barcode(PRODUCT_BARCODE)
        .description(PRODUCT_DESCRIPTION)
        .price(PRODUCT_PRICE)
        .currentStock(PRODUCT_STOCK)
        .criticalStock(PRODUCT_STOCK_CRITICAL)
        .build();
    }
    return pojoAfterCreation;
  }

  public Product productEntityBeforeCreation() {
    if (this.entityBeforeCreation == null) {
      this.entityBeforeCreation = new Product(PRODUCT_NAME, PRODUCT_BARCODE, PRODUCT_DESCRIPTION, PRODUCT_PRICE,
        PRODUCT_STOCK, PRODUCT_STOCK_CRITICAL);
    }
    return this.entityBeforeCreation;
  }

  public Product productEntityAfterCreation() {
    if (this.entityAfterCreation == null) {
      this.entityAfterCreation = new Product(PRODUCT_ID, PRODUCT_NAME, PRODUCT_BARCODE, PRODUCT_DESCRIPTION,
        PRODUCT_PRICE, PRODUCT_STOCK, PRODUCT_STOCK_CRITICAL, null);
    }
    return this.entityAfterCreation;
  }
}
