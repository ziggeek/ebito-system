--liquibase formatted sql

--changeset Pro100:1
--2023-12-15--create-table-document
create table if not exists t_documents
(
    cloud_id  bigserial primary key not null,
    client_id varchar(255)          not null,
    name_file varchar(255)          not null
);