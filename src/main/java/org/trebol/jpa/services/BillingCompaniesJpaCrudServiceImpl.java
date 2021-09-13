package org.trebol.jpa.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.trebol.api.pojo.BillingCompanyPojo;
import org.trebol.exceptions.BadInputException;
import org.trebol.jpa.GenericJpaCrudService;
import org.trebol.jpa.entities.BillingCompany;
import org.trebol.jpa.entities.QBillingCompany;
import org.trebol.jpa.repositories.IBillingCompaniesJpaRepository;

import java.util.Map;

/**
 *
 * @author Benjamin La Madrid <bg.lamadrid at gmail.com>
 */
@Service
public class BillingCompaniesJpaCrudServiceImpl
  extends GenericJpaCrudService<BillingCompanyPojo, BillingCompany> {

  private static final Logger logger = LoggerFactory.getLogger(BillingCompaniesJpaCrudServiceImpl.class);
  private final IBillingCompaniesJpaRepository billingTypesRepository;
  private final ConversionService conversion;

  @Autowired
  public BillingCompaniesJpaCrudServiceImpl(IBillingCompaniesJpaRepository repository, ConversionService conversion) {
    super(repository);
    this.billingTypesRepository = repository;
    this.conversion = conversion;
  }

  @Override
  public boolean itemExists(BillingCompanyPojo input) throws BadInputException {
    String idNumber = input.getIdNumber();
    if (idNumber == null || idNumber.isBlank()) {
      throw new BadInputException("Billing company has no id number");
    } else {
      return (billingTypesRepository.findByIdNumber(idNumber).isPresent());
    }
  }

  @Override
  public BillingCompanyPojo convertToPojo(BillingCompany source) {
    return conversion.convert(source, BillingCompanyPojo.class);
  }

  @Override
  public BillingCompany convertToNewEntity(BillingCompanyPojo source) {
    return conversion.convert(source, BillingCompany.class);
  }

  @Override
  public void applyChangesToExistingEntity(BillingCompanyPojo source, BillingCompany target) throws BadInputException {
    String name = source.getName();
    if (name != null && !name.isBlank() && !target.getName().equals(name)) {
      target.setName(name);
    }
  }

  @Override
  public Predicate parsePredicate(Map<String, String> queryParamsMap) {
    QBillingCompany qBillingCompany = QBillingCompany.billingCompany;
    BooleanBuilder predicate = new BooleanBuilder();
    for (String paramName : queryParamsMap.keySet()) {
      String stringValue = queryParamsMap.get(paramName);
      try {
        switch (paramName) {
          case "id":
            return predicate.and(qBillingCompany.id.eq(Long.valueOf(stringValue))); // id matching is final
          case "idNumber":
            predicate.and(qBillingCompany.idNumber.eq(stringValue));
            break;
          case "name":
            predicate.and(qBillingCompany.name.eq(stringValue));
            break;
          case "idNumberLike":
            predicate.and(qBillingCompany.idNumber.likeIgnoreCase("%" + stringValue + "%"));
            break;
          case "nameLike":
            predicate.and(qBillingCompany.name.likeIgnoreCase("%" + stringValue + "%"));
            break;
          default:
            break;
        }
      } catch (NumberFormatException exc) {
        logger.info("Param '{}' couldn't be parsed as number (value: '{}')", paramName, stringValue);
      }
    }

    return predicate;
  }
}