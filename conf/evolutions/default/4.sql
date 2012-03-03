# --- !Ups

create table location (
  id                       bigint not null,
  latitude                 double,
  longitude                double,
  name                     varchar(255),
  constraint pk_location   primary key (id) );

alter table blog_entry drop column location;
alter table blog_entry add location_id bigint;
alter table blog_entry add foreign key (location_id) references location(id);

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists location;

alter table blog_entry drop column location_id;
alter table blog_entry add location varchar(255);

SET REFERENTIAL_INTEGRITY TRUE;


