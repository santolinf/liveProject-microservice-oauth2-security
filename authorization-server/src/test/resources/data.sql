-- this is data required by the tests
insert into users (username, password) values ('user', '$2a$10$Hym706T60aw3D5DGqvEYxe00ULUzz6qwZ6hjD9NH79RCd/NmufWNu');

insert into client (client_id, secret, scope, redirect_uri)
values ('client', '$2a$10$2ZKjVqtpUJiuckq2urVG9OIwF5RVILPJbaXuwiwKxFdEVrBInCed.', 'read', 'http://localhost:3002/login');

insert into grant_type (grant_type, client_id)
values ('authorization_code', select max(id) from client);

insert into grant_type (grant_type, client_id)
values ('password', select max(id) from client);

insert into grant_type (grant_type, client_id)
values ('refresh_token', select max(id) from client);

insert into client (client_id, secret, scope)
values ('client1', '$2a$10$2ZKjVqtpUJiuckq2urVG9OIwF5RVILPJbaXuwiwKxFdEVrBInCed.', 'info');

insert into grant_type (grant_type, client_id)
values ('client_credentials', select max(id) from client);
