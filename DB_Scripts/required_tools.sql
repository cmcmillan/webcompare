--
-- PostgreSQL database dump
--

-- Started on 2009-10-13 11:20:37

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1496 (class 1259 OID 19397)
-- Dependencies: 1764 3
-- Name: required_tools; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE required_tools (
    rid integer NOT NULL,
    priority integer DEFAULT 999,
    artifact_name character varying,
    artifact_version character varying,
    website character varying,
    status_code integer,
    category_id integer
);


ALTER TABLE public.required_tools OWNER TO postgres;

--
-- TOC entry 1771 (class 0 OID 0)
-- Dependencies: 1496
-- Name: COLUMN required_tools.artifact_name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN required_tools.artifact_name IS 'Name of the tool/framework/library/plugin';


--
-- TOC entry 1495 (class 1259 OID 19395)
-- Dependencies: 3 1496
-- Name: required_tools_rid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE required_tools_rid_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.required_tools_rid_seq OWNER TO postgres;

--
-- TOC entry 1772 (class 0 OID 0)
-- Dependencies: 1495
-- Name: required_tools_rid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE required_tools_rid_seq OWNED BY required_tools.rid;


--
-- TOC entry 1763 (class 2604 OID 19400)
-- Dependencies: 1496 1495 1496
-- Name: rid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE required_tools ALTER COLUMN rid SET DEFAULT nextval('required_tools_rid_seq'::regclass);


--
-- TOC entry 1766 (class 2606 OID 19406)
-- Dependencies: 1496 1496
-- Name: required_tools_prim_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY required_tools
    ADD CONSTRAINT required_tools_prim_key PRIMARY KEY (rid);


--
-- TOC entry 1768 (class 2606 OID 19412)
-- Dependencies: 1494 1496
-- Name: fk_giat_category; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY required_tools
    ADD CONSTRAINT fk_giat_category FOREIGN KEY (category_id) REFERENCES giat_categories(category_id) ON UPDATE CASCADE ON DELETE SET DEFAULT;


--
-- TOC entry 1767 (class 2606 OID 19407)
-- Dependencies: 1496 1492
-- Name: fk_giat_status; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY required_tools
    ADD CONSTRAINT fk_giat_status FOREIGN KEY (status_code) REFERENCES giat_status(status_code) ON UPDATE CASCADE ON DELETE SET DEFAULT;


-- Completed on 2009-10-13 11:20:37

--
-- PostgreSQL database dump complete
--

