CREATE SEQUENCE BRUKER_SEQ START WITH 1;
CREATE SEQUENCE TILTAK_SEQ START WITH 1;

CREATE TABLE BRUKER (
  bruker_id             NUMBER(19)     NOT NULL,
  bruker_tidspunkt      TIMESTAMP(6)   NOT NULL,
  fnr                   NVARCHAR2(255) NOT NULL,
  oppfolgingsenhet_id   NVARCHAR2(255) NOT NULL,
  oppfolgingsenhet_navn NVARCHAR2(255) NOT NULL,
  under_oppfolging      NUMBER(1, 0)   NOT NULL,
  maal                  NVARCHAR2(255),
  tiltak_en             NVARCHAR2(255) NOT NULL,
  tiltak_to             NVARCHAR2(255) NOT NULL,
  CONSTRAINT BRUKER_PK PRIMARY KEY (bruker_id)
);

CREATE TABLE TILTAK (
  tiltak_id             NUMBER(19)     NOT NULL,
  bruker_id             NUMBER(19)     NOT NULL,
  tiltak_nokkel         NVARCHAR2(255) NOT NULL,
  CONSTRAINT TILTAK_PK PRIMARY KEY (tiltak_id),
  CONSTRAINT TILTAK_BRUKER_FK FOREIGN KEY (bruker_id) REFERENCES BRUKER (bruker_id)
);

CREATE INDEX BRUKER_ID_IDX
  ON BRUKER (bruker_id);
CREATE INDEX TILTAK_ID_IDX
  ON TILTAK (tiltak_id);
CREATE INDEX TILTAK_BRUKER_ID_IDX
  ON TILTAK (bruker_id);

-- DROP TABLE TILTAK;
-- DROP SEQUENCE TILTAK_SEQ;
-- DROP TABLE BRUKER;
-- DROP SEQUENCE BRUKER_SEQ;
