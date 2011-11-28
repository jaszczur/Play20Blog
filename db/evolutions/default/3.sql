# --- !Ups

alter table blog_entry add location varchar(255)


# --- !Downs

alter table blog_entry drop column location

