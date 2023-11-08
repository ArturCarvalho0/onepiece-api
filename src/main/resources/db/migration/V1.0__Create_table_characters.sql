create table if not exists characters(
    id serial,
    name varchar(50) not null,

    location varchar(50) not null,

    description varchar(50) not null,

    reward numeric not null,
  
    img varchar(50) not null,

    constraint pk_characters primary key (id)

);

