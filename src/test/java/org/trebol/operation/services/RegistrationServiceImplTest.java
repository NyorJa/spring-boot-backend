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

package org.trebol.operation.services;

import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.trebol.exceptions.BadInputException;
import org.trebol.jpa.entities.Customer;
import org.trebol.jpa.entities.Person;
import org.trebol.jpa.entities.User;
import org.trebol.jpa.entities.UserRole;
import org.trebol.jpa.repositories.CustomersRepository;
import org.trebol.jpa.repositories.PeopleRepository;
import org.trebol.jpa.repositories.UserRolesRepository;
import org.trebol.jpa.repositories.UsersRepository;
import org.trebol.jpa.services.conversion.PeopleConverterService;
import org.trebol.pojo.PersonPojo;
import org.trebol.pojo.RegistrationPojo;

import javax.persistence.EntityExistsException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {
  @InjectMocks RegistrationServiceImpl instance;
  @Mock UsersRepository usersRepositoryMock;
  @Mock PeopleConverterService peopleConverterService;
  @Mock PeopleRepository peopleRepositoryMock;
  @Mock UserRolesRepository rolesRepositoryMock;
  @Mock PasswordEncoder passwordEncoderMock;
  @Mock CustomersRepository customerRepositoryMock;
  PersonPojo personPojoMock;
  RegistrationPojo regPojoMock;
  Person personMock;
  Optional<UserRole> customerRoleMock;

  @BeforeEach
  void beforeEach() {
    // Default mock objects
    personPojoMock = PersonPojo.builder()
      .id(1L)
      .firstName("firstName")
      .lastName("lastName")
      .idNumber("1")
      .email("email@example.com")
      .phone1("+123 456")
      .phone2("+123 456")
      .build();
    regPojoMock = RegistrationPojo.builder()
      .name("name")
      .password("password")
      .profile(personPojoMock)
      .build();
    personMock = new Person(1L, "firstName", "lastName", "1", "email@example.com", "+123 456", "+123 456");
    customerRoleMock = Optional.of(new UserRole(1L, "Customer"));

    // Reset mocks
    reset(usersRepositoryMock);
    reset(peopleRepositoryMock);
    reset(rolesRepositoryMock);
  }

  @DisplayName("User and Id doesn't already exists, should pass")
  @Test
  void EverythingCorrect_NoException() throws BadInputException {
    when(usersRepositoryMock.exists(any(Predicate.class))).thenReturn(false);
    when(peopleConverterService.convertToNewEntity(any(PersonPojo.class))).thenReturn(personMock);
    when(peopleRepositoryMock.exists(any(Predicate.class))).thenReturn(false);
    when(peopleRepositoryMock.saveAndFlush(any(Person.class))).thenReturn(personMock);
    // inside convertToUser method
    when(rolesRepositoryMock.findByName(anyString())).thenReturn(customerRoleMock);

    assertDoesNotThrow(() -> instance.register(regPojoMock));
  }

  @DisplayName("Save and flush must be called on repositories")
  @Test
  void SaveCalledOnRepository() throws EntityExistsException, BadInputException {
    when(usersRepositoryMock.exists(any(Predicate.class))).thenReturn(false);
    when(peopleConverterService.convertToNewEntity(any(PersonPojo.class))).thenReturn(personMock);
    when(peopleRepositoryMock.exists(any(Predicate.class))).thenReturn(false);
    when(peopleRepositoryMock.saveAndFlush(any(Person.class))).thenReturn(personMock);
    // inside convertToUser method
    when(rolesRepositoryMock.findByName(anyString())).thenReturn(customerRoleMock);

    instance.register(regPojoMock);

    verify(peopleRepositoryMock, times(1)).saveAndFlush(any(Person.class));
    verify(usersRepositoryMock, times(1)).saveAndFlush(any(User.class));
    verify(customerRepositoryMock, times(1)).saveAndFlush(any(Customer.class));
  }

  @DisplayName("User with same name already exists, EntityExistsException")
  @Test
  void NameAlreadyExists_EntityExistsException() {
    when(usersRepositoryMock.exists(any(Predicate.class))).thenReturn(true); // Name already exists

    assertThrows(EntityExistsException.class, () -> instance.register(regPojoMock));
  }

  @DisplayName("Person with same ID already exists, EntityExistsException")
  @Test
  void IdAlreadyExists_EntityExistsException() throws BadInputException {
    when(usersRepositoryMock.exists(any(Predicate.class))).thenReturn(false);
    when(peopleConverterService.convertToNewEntity(any(PersonPojo.class))).thenReturn(personMock);
    when(peopleRepositoryMock.exists(any(Predicate.class))).thenReturn(true); // ID already exists

    assertThrows(EntityExistsException.class, () -> instance.register(regPojoMock));
  }

  @DisplayName("Customer Role not found, IllegalStateException")
  @Test
  void CustomerRoleNotFound_IllegalStateException() throws BadInputException {
    when(usersRepositoryMock.exists(any(Predicate.class))).thenReturn(false);
    when(peopleConverterService.convertToNewEntity(any(PersonPojo.class))).thenReturn(personMock);
    when(peopleRepositoryMock.exists(any(Predicate.class))).thenReturn(false);
    when(peopleRepositoryMock.saveAndFlush(any(Person.class))).thenReturn(personMock);
    // inside convertToUser method
    when(rolesRepositoryMock.findByName(anyString())).thenReturn(Optional.empty());

    assertThrows(IllegalStateException.class, () -> instance.register(regPojoMock));
  }

}
