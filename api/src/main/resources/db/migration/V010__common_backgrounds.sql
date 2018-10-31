create table common_backgrounds (
  background_id uuid primary key
);

insert into common_backgrounds (background_id)
values
       -- real
       ('111d2419-acc3-4b35-ba49-c5938d0f524d'),
       ('f34d29fe-2136-406b-8bf5-08c97e3eb958'),
       -- not real
       ('1e2ef3f0-dd39-11e8-9f8b-f2801f1b9fd1'),
       ('1e2ef580-dd39-11e8-9f8b-f2801f1b9fd1');

