/**
 * Author:  Benjamin La Madrid <bg.lamadrid at gmail.com>
 */

INSERT INTO `sales_statuses`
(`sell_status_id`, `sell_status_name`)
VALUES
(-6, 'Returned'),
(-5, 'Delivery Failed'),
(-4, 'Delivery Cancelled'),
(-3, 'Rejected'),
(-2, 'Payment Failed'),
(-1, 'Payment Cancelled'),
(01, 'Pending'),
(02, 'Payment Started'),
(03, 'Paid, Unconfirmed'),
(04, 'Paid, Confirmed'),
(05, 'Delivery On Route'),
(06, 'Delivery Complete');

INSERT INTO `billing_types`
(`billing_type_id`, `billing_type_name`)
VALUES
(01, 'Bill'),
(02, 'Enterprise Invoice');

INSERT INTO `app_users_roles`
(`user_role_id`, `user_role_name`)
VALUES 
(01, 'Administrator'),
(02, 'Manager'),
(03, 'Salesperson'),
(04, 'Customer');

INSERT INTO `people`
(`person_id`, `person_name`, `person_id_number`, `person_email`)
VALUES
(01, 'Test',  '1111111', 'test@example.com'),
(02, 'Test2', '2222222', 'test2@example.com'),
(03, 'Test3', '3333333', 'test3@example.com'),
(04, 'Test4', '4444444', 'test4@example.com'),
(05, 'Test5', '5555555', 'test5@example.com');

INSERT INTO `app_permissions`
(`permission_id`, `permission_code`)
VALUES
(01, 'product_categories:delete'),
(02, 'product_categories:create'),
(03, 'product_categories:update'),
(04, 'product_categories:read'),
(05, 'sell_statuses:delete'),
(06, 'sell_statuses:create'),
(07, 'sell_statuses:update'),
(08, 'sell_statuses:read'),
(09, 'sell_types:delete'),
(10, 'sell_types:create'),
(11, 'sell_types:update'),
(12, 'sell_types:read'),
(13, 'customers:delete'),
(14, 'customers:create'),
(15, 'customers:update'),
(16, 'customers:read'),
(17, 'products:delete'),
(18, 'products:create'),
(19, 'products:update'),
(20, 'products:read'),
(21, 'sales:delete'),
(22, 'sales:create'),
(23, 'sales:update'),
(24, 'sales:read'),
(25, 'salespeople:delete'),
(26, 'salespeople:create'),
(27, 'salespeople:update'),
(28, 'salespeople:read'),
(29, 'users:delete'),
(30, 'users:create'),
(31, 'users:update'),
(32, 'users:read'),
(33, 'user_roles:delete'),
(34, 'user_roles:create'),
(35, 'user_roles:update'),
(36, 'user_roles:read'),
(37, 'people:delete'),
(38, 'people:create'),
(39, 'people:update'),
(40, 'people:read');

INSERT INTO `app_users_roles_permissions`
(`user_role_permission_id`, `permission_id`, `user_role_id`)
VALUES
(01, 01, 01),
(02, 02, 01),
(03, 03, 01),
(04, 04, 01),
(05, 05, 01),
(06, 06, 01),
(07, 07, 01),
(08, 08, 01),
(09, 09, 01),
(10, 10, 01),
(11, 11, 01),
(12, 12, 01),
(13, 13, 01),
(14, 14, 01),
(15, 15, 01),
(16, 16, 01),
(17, 17, 01),
(18, 18, 01),
(19, 19, 01),
(20, 20, 01),
(21, 21, 01),
(22, 22, 01),
(23, 23, 01),
(24, 24, 01),
(25, 25, 01),
(26, 26, 01),
(27, 27, 01),
(28, 28, 01),
(29, 29, 01),
(30, 30, 01),
(31, 31, 01),
(32, 32, 01),
(33, 33, 01),
(34, 34, 01),
(35, 35, 01),
(36, 36, 01),
(37, 37, 01),
(38, 38, 01),
(39, 39, 01),
(40, 40, 01),
(45, 01, 02),
(46, 02, 02),
(47, 03, 02),
(48, 04, 02),
(55, 12, 02),
(57, 16, 02),
(58, 17, 02),
(59, 18, 02),
(60, 19, 02),
(61, 20, 02),
(63, 23, 02),
(64, 24, 02),
(66, 26, 02),
(67, 27, 02),
(68, 28, 02),
(69, 36, 02),
(70, 40, 02),
(75, 03, 03),
(76, 04, 03),
(77, 08, 03),
(78, 12, 03),
(79, 16, 03),
(80, 17, 03),
(81, 18, 03),
(82, 19, 03),
(83, 20, 03),
(84, 22, 03),
(85, 24, 03),
(86, 28, 03),
(87, 40, 03);

INSERT INTO `app_users`
(`user_id`, `user_name`, `user_password`, `user_role_id`, `person_id`)
VALUES
(01, 'admin',    '$2a$10$U4vUIIlZDBvpwT.KkQuQHOfJrbGpGBol3WT9ryASiwPfYAX1bEq0K', 01, 01),
(02, 'manager',  '$2a$10$qgNTjPGucz6/ul9/GfVwvO.cWo9.lDokYi6GQ3H4bwvAVG49uV/1K', 02, 02),
(03, 'vendedor', '$2a$10$moc0.bFZsTztrgLrNvGC4uApdkBFihA6PlQsuy.dZ0k4A6eVhLwdu', 03, 03),
(04, 'cliente',  '$2a$10$Mi39GIZtguqvm2aLl0JpHOm0WcPoN5aMYlbD.L6IymAEBOQU/6m6u', 04, 04);
-- passwords equal usernames
