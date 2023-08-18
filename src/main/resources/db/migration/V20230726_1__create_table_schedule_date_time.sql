CREATE TABLE schedule_date_time (
  date date NOT NULL,
   unavailable_times time[],
   CONSTRAINT pk_schedule_date_time PRIMARY KEY (date)
);