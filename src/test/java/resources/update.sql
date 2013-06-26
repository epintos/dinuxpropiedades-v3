UPDATE PUBLICATION SET status = 'ACTIVE' where status like 'AVAILABLE';
UPDATE PUBLICATION SET status = 'CANCELED' where status like 'FINISHED';

CREATE TABLE statuschange
(
  publication_id integer NOT NULL,
  changedate bytea,
  newstatus character varying(255),
  previousstatus character varying(255),
  CONSTRAINT fk6aa1e662aeacb1a1 FOREIGN KEY (publication_id)
      REFERENCES publication (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE statuschange
  OWNER TO paw;

  
CREATE TABLE publicity
(
  id serial NOT NULL,
  client character varying(255) NOT NULL,
  frequence integer NOT NULL,
  url character varying(255) NOT NULL,
  CONSTRAINT publicity_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE publicity
  OWNER TO PAW;

ALTER TABLE PUBLICATION ADD COLUMN currency character varying(50);
UPDATE PUBLICATION SET currency = 'DOLAR';
