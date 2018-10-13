create table documents (
  document_id serial primary key,
  file_id UUID not null,
  data_id int,
  document_name varchar not null,
  mime varchar(100) not null,
  size numeric(12, 3) not null,
  created_date timestamp without time zone not null default now(),
  updated_date timestamp without time zone not null default now()
);

create unique index documents_file_id_uindex on documents(file_id);
