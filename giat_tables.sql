--
-- PostgreSQL database dump
--

-- Started on 2009-10-13 11:13:20

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = true;

--
-- TOC entry 1486 (class 1259 OID 19295)
-- Dependencies: 3
-- Name: DEPENDENCIES; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "DEPENDENCIES" (
    "DEPENDENCY_ID" oid NOT NULL,
    "PACKAGE" character varying,
    "VERSION" character varying,
    "WEBSITE" character varying,
    "MVN_DATA_ID" oid,
    "WEBSITE_ID" oid
);


ALTER TABLE public."DEPENDENCIES" OWNER TO postgres;

--
-- TOC entry 1765 (class 0 OID 19295)
-- Dependencies: 1486
-- Data for Name: DEPENDENCIES; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "DEPENDENCIES" ("DEPENDENCY_ID", "PACKAGE", "VERSION", "WEBSITE", "MVN_DATA_ID", "WEBSITE_ID") FROM stdin;
\.


--
-- TOC entry 1764 (class 2606 OID 19302)
-- Dependencies: 1486 1486
-- Name: DEPENDENCIES_PRIM_KEY; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "DEPENDENCIES"
    ADD CONSTRAINT "DEPENDENCIES_PRIM_KEY" PRIMARY KEY ("DEPENDENCY_ID");


-- Completed on 2009-10-13 11:13:21

--
-- PostgreSQL database dump complete
--

