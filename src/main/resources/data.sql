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

insert into appointment_type (id, name, price, is_deleted) values
('f364f24d-8af4-438b-a858-98b5bec6d797', 'Kardio', 100, false),
('7d28aa78-c314-4f4b-9a43-ad70e1b7f06a', 'Neuro', 200, false),
('73a9efe1-4a87-4b52-b283-9da99b2ff531', 'Fizio', 300, false);

insert into medical_staff (id, medical_type, clinic_id, start_work_at, end_work_at, appointment_type_id) values
('887b6506-6998-4db7-84b9-a9a5f24fb430', 'DOCTOR', 'eaf7dd54-e805-4250-9f79-9baae8866ad6', '08:00:00', '20:00:00', 'f364f24d-8af4-438b-a858-98b5bec6d797'),
('e06fcaca-2ec9-4791-890d-db74a83d6102', 'DOCTOR', 'eaf7dd54-e805-4250-9f79-9baae8866ad6', '09:00:00', '20:00:00', 'f364f24d-8af4-438b-a858-98b5bec6d797'),
('34b105b1-0e15-4284-8489-c695085d6e96', 'DOCTOR', 'af37e667-a484-4c8b-8ff9-a4e44b57fde3', '07:00:00', '20:00:00', '7d28aa78-c314-4f4b-9a43-ad70e1b7f06a'),
('ef639bfd-3f28-42e9-8cd4-049623ca6838', 'NURSE', '188c43b3-454b-4070-91d4-982b711faff7', '15:00:00', '23:00:00', '73a9efe1-4a87-4b52-b283-9da99b2ff531');



insert into medical_record(height,weight,diopter,alergies,blood_type) values
(176,77,3.2,'polen mleko','O'),
(176,77,3.2,'polen mleko','O'),
(176,77,3.2,'polen mleko','O'),
(176,77,3.2,'polen mleko','O');

insert into patient (id, active,medical_record_id) values
('11a66abc-f934-4d45-aaec-2060e802a431', true,1),
('da125e3c-0a23-479f-a270-d8c4ec283d64', true,2),
('df736dc5-662a-4bae-a6cf-3e85f1eeabe2', true,3),
('f66a7d1a-c3bc-484e-a93c-3dbad8da713a', true,4);

insert into medicine(id,name) values ('ace48cf6-4b4f-11ea-b77f-2e728ce88125','lek');
insert into diagnosis(id,name) values ('c40abdc4-4b4f-11ea-b77f-2e728ce88125','bolest');


insert into clinic_patient (clinic_id, patient_id) values
('eaf7dd54-e805-4250-9f79-9baae8866ad6', '11a66abc-f934-4d45-aaec-2060e802a431'),
('af37e667-a484-4c8b-8ff9-a4e44b57fde3', 'da125e3c-0a23-479f-a270-d8c4ec283d64'),
('188c43b3-454b-4070-91d4-982b711faff7', 'df736dc5-662a-4bae-a6cf-3e85f1eeabe2'),
('188c43b3-454b-4070-91d4-982b711faff7', 'f66a7d1a-c3bc-484e-a93c-3dbad8da713a');

insert into medical_patient (medical_id, patient_id) values
('887b6506-6998-4db7-84b9-a9a5f24fb430', '11a66abc-f934-4d45-aaec-2060e802a431'),
('e06fcaca-2ec9-4791-890d-db74a83d6102', 'da125e3c-0a23-479f-a270-d8c4ec283d64'),
('34b105b1-0e15-4284-8489-c695085d6e96', 'df736dc5-662a-4bae-a6cf-3e85f1eeabe2'),
('34b105b1-0e15-4284-8489-c695085d6e96', 'f66a7d1a-c3bc-484e-a93c-3dbad8da713a');

insert into registration_request (id, address, city, country, email, first_name, last_name, password, phone, ssn, status) values
('f4e88471-2963-43b8-bf62-99cde9a62b9f', 'address', 'city', 'country', 'user6@test.com', 'firstName1', 'lastName1', 'password', '1241241', '2141241', 'PENDING');

--insert into appointment_request (id, appointment_type_id, medical_staff_id, patient_id, status, appointment_date) values
--('6a7c951e-26fe-44e3-8dd0-6dce920f815c', 'f364f24d-8af4-438b-a858-98b5bec6d797', '887b6506-6998-4db7-84b9-a9a5f24fb430', '11a66abc-f934-4d45-aaec-2060e802a431', 'DENIED', '12-12-2001'),
--('0fa2a851-d373-4fd3-b1e2-49297cb5c6b6', '7d28aa78-c314-4f4b-9a43-ad70e1b7f06a', 'e06fcaca-2ec9-4791-890d-db74a83d6102', 'da125e3c-0a23-479f-a270-d8c4ec283d64', 'DENIED', '02-02-2002'),
--('3ea6af75-70dd-4203-b74f-ab40e9d670a6', '73a9efe1-4a87-4b52-b283-9da99b2ff531', '34b105b1-0e15-4284-8489-c695085d6e96', 'df736dc5-662a-4bae-a6cf-3e85f1eeabe2', 'DENIED', '03-03-2003');

insert into er(id, number, name, clinic_id, is_deleted) values
('7a66517d-8465-4016-8700-9de19d41c4c9', '1', 'SalaA', 'eaf7dd54-e805-4250-9f79-9baae8866ad6', false),
('ffef321d-d36d-432e-b510-a215e9bc1615', '2', 'SalaB', 'af37e667-a484-4c8b-8ff9-a4e44b57fde3', false),
('6ed6b30b-3f98-4919-bc5d-1b6c59d28995', '3', 'SalaC', '188c43b3-454b-4070-91d4-982b711faff7', false),
('627da023-30b8-486f-805b-7f5bb2b6758f', '4', 'SalaD', '188c43b3-454b-4070-91d4-982b711faff7', false),
('3ff3dee8-d11b-42b0-855b-05369daef498', '5', 'SalaE', 'af37e667-a484-4c8b-8ff9-a4e44b57fde3', false),
('cac20c23-f692-4048-af46-716b5a4be5b2', '6', 'SalaF', 'af37e667-a484-4c8b-8ff9-a4e44b57fde3', false),
('d85f3d57-0594-4bd9-80eb-ae71cd530373', '7', 'SalaG', 'af37e667-a484-4c8b-8ff9-a4e44b57fde3', false),
('f45c9df3-3da2-4aee-9293-a54e56da6082', '8', 'SalaH', 'af37e667-a484-4c8b-8ff9-a4e44b57fde3', false),
('80a6fc7a-d6bb-46ab-b45f-60aae75016e8', '9', 'SalaI', '188c43b3-454b-4070-91d4-982b711faff7', false),
('28eb85ca-d38b-4032-a220-e73a13b0d017', '10', 'SalaJ', '188c43b3-454b-4070-91d4-982b711faff7', false);

--insert into appointment_period(id, startd, endd) values
--('a967505b-d652-471a-99b5-b2e28f2aa24a', '2014-01-30 07:00:00', '2014-01-30 08:00:00'),
--('c969f769-79bc-4572-8c27-ce034716def4', '2014-01-30 08:30:00', '2014-01-30 09:30:00'),
--('30db2310-aa87-4464-b644-a6b2b405bcd5', '2014-01-31 07:00:00', '2014-01-31 08:00:00'),
--('a0f8cf80-4c19-4e4e-a5f1-3b32945feb86', '2014-01-31 09:00:00', '2014-01-31 11:15:00'),
--('e1a4e456-c17c-46d2-b500-f9e794dfd837', '2014-02-07 07:00:00', '2014-01-30 08:00:00'),
--('01cf9847-3dab-45e2-b3e8-15224d91e94b', '2014-03-14 07:00:00', '2014-01-30 08:00:00');




