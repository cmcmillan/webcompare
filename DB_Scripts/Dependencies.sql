--
-- PostgreSQL database dump
--

-- Started on 2009-10-13 11:17:46

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1486 (class 1259 OID 19295)
-- dependencies: 3
-- Name: dependencies; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "dependencies" (
    "dependency_id" SERIAL NOT NULL,
    "dep_package" character varying,
    "dep_version" character varying,
    "dep_website" character varying,
    "mvn_data_id" INTEGER,
    "dep_website_id" INTEGER
);


ALTER TABLE public."dependencies" OWNER TO postgres;

--
-- TOC entry 1765 (class 0 OID 19295)
-- dependencies: 1486
-- Data for Name: dependencies; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "dependencies" ("dependency_id", "dep_package", "dep_version", "dep_website", "mvn_data_id", "dep_website_id") FROM stdin;
--\.


--
-- TOC entry 1764 (class 2606 OID 19302)
-- dependencies: 1486 1486
-- Name: dependencies_PRIM_KEY; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "dependencies"
    ADD CONSTRAINT "dependencies_PRIM_KEY" PRIMARY KEY ("dependency_id");


-- Completed on 2009-10-13 11:17:46

--
-- PostgreSQL database dump complete
--

