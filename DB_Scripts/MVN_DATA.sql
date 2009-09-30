-- Table: "MVN_DATA"

-- DROP TABLE "MVN_DATA";

CREATE TABLE "MVN_DATA"
(
  "DEP_ID" oid NOT NULL,
  "RAW" character varying,
  "DEPENDENCY" boolean,
  "TRIM" character varying,
  "PACKAGE" character varying,
  "CLASS" character varying,
  "CONTAINER" character varying,
  "VERSION" character varying,
  CONSTRAINT "MVN_DEP_PRIM_KEY" PRIMARY KEY ("DEP_ID")
)
WITH (OIDS=TRUE);
ALTER TABLE "MVN_DATA" OWNER TO postgres;
COMMENT ON TABLE "MVN_DATA" IS 'Raw MVN Dependecy Data';
