insert into ADMIN_USER(ID, PASSWORD, USERNAME) values(1, '$2a$10$msRdCsQU1WaNm.iU8Y3uL.95rcGpA96Aoi/BpwDgMTLQCP24v57s2', 'liudonghua');
insert into ADMIN_USER_ROLE(ID, ROLE) values(1, 'ADMIN');
insert into ADMIN_USER_ROLE(ID, ROLE) values(2, 'USER');
insert into ADMIN_USER_ROLE_JOIN(ADMIN_USER_ID, ADMIN_USER_ROLE_ID) values(1, 1);
insert into ADMIN_USER_ROLE_JOIN(ADMIN_USER_ID, ADMIN_USER_ROLE_ID) values(1, 2);
