CREATE SEQUENCE BRUKER_SEQ START WITH 1;

CREATE TABLE BRUKER (
  bruker_id             NUMBER(19)     NOT NULL,
  bruker_tidspunkt      TIMESTAMP(6)   NOT NULL,
  fnr                   NVARCHAR2(255) NOT NULL,
  oppfolgingsEnhetId    NVARCHAR2(255) NOT NULL,
  oppfolgingsEnhetNavn  NVARCHAR2(255) NOT NULL,
  under_oppfolging      NUMBER(1, 0)   NOT NULL,
  maal                  NVARCHAR2(255) NOT NULL,
  tiltakEn              NVARCHAR2(255) NOT NULL,
  tiltakTo              NVARCHAR2(255) NOT NULL,
  CONSTRAINT BRUKER_PK PRIMARY KEY (bruker_id)
);

CREATE INDEX BRUKER_ID_IDX
  ON BRUKER (bruker_id);

-- DROP TABLE BRUKER;
-- DROP SEQUENCE BRUKER_SEQ;
