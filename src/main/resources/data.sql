insert into clinic_center (id) values
('b3627b32-6ce7-4fcd-a29a-980c188f42d4');

insert into clinic (id, clinic_center_id, name, address, description, is_deleted) values
('eaf7dd54-e805-4250-9f79-9baae8866ad6', 'b3627b32-6ce7-4fcd-a29a-980c188f42d4', 'Ime 1', 'Adresa 1', 'Opis 1', false),
('af37e667-a484-4c8b-8ff9-a4e44b57fde3', 'b3627b32-6ce7-4fcd-a29a-980c188f42d4', 'Ime 2', 'Adresa 2', 'Opis 2', false),
('188c43b3-454b-4070-91d4-982b711faff7', 'b3627b32-6ce7-4fcd-a29a-980c188f42d4', 'Ime 3', 'Adresa 3', 'Opis 3', false);



insert into admin (id, admin_type, clinic_id) values
('917a4080-375a-4da3-955f-5dfa68ed1dcb', 'SUPER_ADMIN', 'eaf7dd54-e805-4250-9f79-9baae8866ad6'),
('2e1cf001-1778-4791-a95b-4928eb692729', 'ADMIN', 'eaf7dd54-e805-4250-9f79-9baae8866ad6'),
('933c01e6-2c79-401d-8ffd-125f20e3cfff', 'ADMIN', 'af37e667-a484-4c8b-8ff9-a4e44b57fde3'),
('24d7671e-c8aa-47dc-8cc6-a50d3c875da1', 'ADMIN', '188c43b3-454b-4070-91d4-982b711faff7');
