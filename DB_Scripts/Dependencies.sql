-- Table: "DEPENDENCIES"

-- DROP TABLE "DEPENDENCIES";

CREATE TABLE "DEPENDENCIES"
(
  "DEPENDENCY_ID" oid NOT NULL,
  "PACKAGE" character varying,
  "VERSION" character varying,
  "WEBSITE" character varying,
  "MVN_DATA_ID" oid,
  "WEBSITE_ID" oid,
  CONSTRAINT "DEPENDENCIES_PRIM_KEY" PRIMARY KEY ("DEPENDENCY_ID"),
  CONSTRAINT "FK_MVN_DATA" FOREIGN KEY ("MVN_DATA_ID")
      REFERENCES "MVN_DATA" ("DEP_ID") MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT "FK_WEBSITE" FOREIGN KEY ("WEBSITE_ID")
      REFERENCES "WEBSITE" ("WID") MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE SET NULL
)
WITH (OIDS=TRUE);
ALTER TABLE "DEPENDENCIES" OWNER TO postgres;
