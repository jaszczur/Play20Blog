# --- !Ups

create table blog_entry (
  id                        bigint not null,
  title                     varchar(255),
  content                   varchar(4096),
  constraint pk_blog_entry primary key (id))
;

create sequence blog_entry_seq;


# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists blog_entry;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists blog_entry_seq;

