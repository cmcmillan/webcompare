-- Table: "WEBSITE"

-- DROP TABLE "WEBSITE";

CREATE TABLE "WEBSITE"
(
  "WID" oid NOT NULL,
  "WEBSITE" character varying(255),
  "PACKAGE" character varying(255) NOT NULL,
  "PACKAGE_KEY" character varying(255) NOT NULL,
  "PACKAGE_VERSION" character varying,
  CONSTRAINT "PRIMARY_KEY" PRIMARY KEY ("WID")
)
WITH (OIDS=FALSE);
ALTER TABLE "WEBSITE" OWNER TO postgres;
