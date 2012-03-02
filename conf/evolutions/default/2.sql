# --- !Ups

alter table blog_entry add creation_date TIMESTAMP not null


# --- !Downs

alter table blog_entry drop column creation_date

