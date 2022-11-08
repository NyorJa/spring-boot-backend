/*
 * Copyright (c) 2022 The Trebol eCommerce Project
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
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.trebol.exceptions.BadInputException;
import org.trebol.jpa.entities.Person;
import org.trebol.jpa.entities.Salesperson;
import org.trebol.jpa.services.ITwoWayConverterJpaService;
import org.trebol.pojo.PersonPojo;
import org.trebol.pojo.SalespersonPojo;

@Service
public class SalespeopleConverterJpaServiceImpl
  implements ITwoWayConverterJpaService<SalespersonPojo, Salesperson> {

  private final ITwoWayConverterJpaService<PersonPojo, Person> peopleService;

  @Autowired
  public SalespeopleConverterJpaServiceImpl(ITwoWayConverterJpaService<PersonPojo, Person> peopleService) {
    this.peopleService = peopleService;
  }

  @Override
  @Nullable
  public SalespersonPojo convertToPojo(Salesperson source) {
    PersonPojo targetPerson = peopleService.convertToPojo(source.getPerson());
    return SalespersonPojo.builder()
      .id(source.getId())
      .person(targetPerson)
      .build();
  }

  @Override
  public Salesperson convertToNewEntity(SalespersonPojo source) throws BadInputException {
    Salesperson target = new Salesperson();
    Person targetPerson = peopleService.convertToNewEntity(source.getPerson());
    target.setPerson(targetPerson);
    return target;
  }
}
