--
-- PostgreSQL database dump
--

-- Started on 2009-09-29 13:45:12

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 1748 (class 1262 OID 11511)
-- Name: postgres; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8';


ALTER DATABASE postgres OWNER TO postgres;

\connect postgres

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = true;

--
-- TOC entry 1471 (class 1259 OID 19295)
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
-- TOC entry 1470 (class 1259 OID 19287)
-- Dependencies: 3
-- Name: MVN_DATA; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "MVN_DATA" (
    "DEP_ID" oid NOT NULL,
    "RAW" character varying,
    "DEPENDENCY" boolean,
    "TRIM" character varying,
    "PACKAGE" character varying,
    "CLASS" character varying,
    "CONTAINER" character varying,
    "VERSION" character varying
);


ALTER TABLE public."MVN_DATA" OWNER TO postgres;

--
-- TOC entry 1751 (class 0 OID 0)
-- Dependencies: 1470
-- Name: TABLE "MVN_DATA"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE "MVN_DATA" IS 'Raw MVN Dependecy Data';


SET default_with_oids = false;

--
-- TOC entry 1469 (class 1259 OID 19273)
-- Dependencies: 3
-- Name: WEBSITE; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "WEBSITE" (
    "WID" oid NOT NULL,
    "WEBSITE" character varying(255),
    "PACKAGE" character varying(255) NOT NULL,
    "PACKAGE_KEY" character varying(255) NOT NULL,
    "PACKAGE_VERSION" character varying
);


ALTER TABLE public."WEBSITE" OWNER TO postgres;

--
-- TOC entry 1743 (class 2606 OID 19302)
-- Dependencies: 1471 1471
-- Name: DEPENDENCIES_PRIM_KEY; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "DEPENDENCIES"
    ADD CONSTRAINT "DEPENDENCIES_PRIM_KEY" PRIMARY KEY ("DEPENDENCY_ID");


--
-- TOC entry 1741 (class 2606 OID 19294)
-- Dependencies: 1470 1470
-- Name: MVN_DEP_PRIM_KEY; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "MVN_DATA"
    ADD CONSTRAINT "MVN_DEP_PRIM_KEY" PRIMARY KEY ("DEP_ID");


--
-- TOC entry 1739 (class 2606 OID 19280)
-- Dependencies: 1469 1469
-- Name: PRIMARY_KEY; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "WEBSITE"
    ADD CONSTRAINT "PRIMARY_KEY" PRIMARY KEY ("WID");


--
-- TOC entry 1745 (class 2606 OID 19308)
-- Dependencies: 1740 1470 1471
-- Name: FK_MVN_DATA; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "DEPENDENCIES"
    ADD CONSTRAINT "FK_MVN_DATA" FOREIGN KEY ("MVN_DATA_ID") REFERENCES "MVN_DATA"("DEP_ID") ON UPDATE CASCADE ON DELETE SET NULL;


--
-- TOC entry 1744 (class 2606 OID 19303)
-- Dependencies: 1738 1471 1469
-- Name: FK_WEBSITE; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "DEPENDENCIES"
    ADD CONSTRAINT "FK_WEBSITE" FOREIGN KEY ("WEBSITE_ID") REFERENCES "WEBSITE"("WID") ON UPDATE CASCADE ON DELETE SET NULL;


--
-- TOC entry 1750 (class 0 OID 0)
-- Dependencies: 3
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2009-09-29 13:45:13

--
-- PostgreSQL database dump complete
--

