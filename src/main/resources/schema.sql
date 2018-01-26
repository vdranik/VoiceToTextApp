create table AUDIOTEXT(
  id integer not null,
  recognitionStatus varchar(255) not null,
  displayText varchar(9223372036854775807),
  off_set bigint not null,
  duration bigint not null,
  PRIMARY KEY (id)
);