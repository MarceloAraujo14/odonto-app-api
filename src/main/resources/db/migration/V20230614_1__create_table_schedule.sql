CREATE TABLE if not exists schedule (
  schedule_id INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   customer_id INTEGER,
   customer_name VARCHAR(50),
   customer_phone VARCHAR(20),
   date date,
   times time[],
   services TEXT[],
   status VARCHAR(20),
   CONSTRAINT pk_schedule PRIMARY KEY (schedule_id)
);