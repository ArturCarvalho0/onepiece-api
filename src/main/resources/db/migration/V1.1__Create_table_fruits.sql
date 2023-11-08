create table if not exists fruits(
    id serial,
    name varchar(50) not null,
    type varchar(50) not null,
    description varchar(50) not null,
    img varchar(50) not null,
    character_id int not null,
    foreign key (character_id) references characters(id),
    constraint pk_fruits primary key (id)
)