--
-- PostgreSQL database dump
--

-- Started on 2009-10-13 15:46:40

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 2277 (class 1259 OID 17318)
-- Dependencies: 2572 2573 2574 2575 2576 3
-- Name: launch_site; Type: TABLE; Schema: public; Owner: geodb; Tablespace: 
--

CREATE TABLE launch_site (
    site_id integer NOT NULL,
    site_name character varying NOT NULL,
    site_operator character varying,
    site_type character varying,
    latitude character varying NOT NULL,
    longitude character varying NOT NULL,
    country character varying,
    min_inclination double precision,
    max_inclination double precision,
    operational_start_date integer DEFAULT (-1),
    site_desc character varying,
    lat double precision DEFAULT 0.0,
    lon double precision DEFAULT 0.0,
    CONSTRAINT valid_latitude CHECK (((lat >= ((-90.0))::double precision) AND (lat <= (90.0)::double precision))),
    CONSTRAINT valid_longitude CHECK (((lon >= ((-180.0))::double precision) AND (lon <= (180.0)::double precision)))
);


ALTER TABLE public.launch_site OWNER TO geodb;

--
-- TOC entry 2582 (class 0 OID 0)
-- Dependencies: 2277
-- Name: TABLE launch_site; Type: COMMENT; Schema: public; Owner: geodb
--

COMMENT ON TABLE launch_site IS 'Partial list of launch sites.
http://www.tech-faq.com/satellite-launch.shtml
http://www.astronautix.com/sites/index.htm
http://en.wikipedia.org/wiki/List_of_rocket_launch_sites';


--
-- TOC entry 2276 (class 1259 OID 17316)
-- Dependencies: 3 2277
-- Name: launch_site_site_id_seq; Type: SEQUENCE; Schema: public; Owner: geodb
--

CREATE SEQUENCE launch_site_site_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.launch_site_site_id_seq OWNER TO geodb;

--
-- TOC entry 2584 (class 0 OID 0)
-- Dependencies: 2276
-- Name: launch_site_site_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: geodb
--

ALTER SEQUENCE launch_site_site_id_seq OWNED BY launch_site.site_id;


--
-- TOC entry 2585 (class 0 OID 0)
-- Dependencies: 2276
-- Name: launch_site_site_id_seq; Type: SEQUENCE SET; Schema: public; Owner: geodb
--

SELECT pg_catalog.setval('launch_site_site_id_seq', 1, false);


--
-- TOC entry 2571 (class 2604 OID 17321)
-- Dependencies: 2277 2276 2277
-- Name: site_id; Type: DEFAULT; Schema: public; Owner: geodb
--

ALTER TABLE launch_site ALTER COLUMN site_id SET DEFAULT nextval('launch_site_site_id_seq'::regclass);


--
-- TOC entry 2579 (class 0 OID 17318)
-- Dependencies: 2277
-- Data for Name: launch_site; Type: TABLE DATA; Schema: public; Owner: geodb
--

COPY launch_site (site_id, site_name, site_operator, site_type, latitude, longitude, country, min_inclination, max_inclination, operational_start_date, site_desc, lat, lon) FROM stdin;
\.


--
-- TOC entry 2578 (class 2606 OID 17331)
-- Dependencies: 2277 2277
-- Name: pk_launch_site; Type: CONSTRAINT; Schema: public; Owner: geodb; Tablespace: 
--

ALTER TABLE ONLY launch_site
    ADD CONSTRAINT pk_launch_site PRIMARY KEY (site_id);


--
-- TOC entry 2583 (class 0 OID 0)
-- Dependencies: 2277
-- Name: launch_site; Type: ACL; Schema: public; Owner: geodb
--

REVOKE ALL ON TABLE launch_site FROM PUBLIC;
REVOKE ALL ON TABLE launch_site FROM geodb;
GRANT ALL ON TABLE launch_site TO geodb;
GRANT ALL ON TABLE launch_site TO postgis WITH GRANT OPTION;
GRANT SELECT,REFERENCES ON TABLE launch_site TO PUBLIC;


-- Completed on 2009-10-13 15:46:40

--
-- PostgreSQL database dump complete
--

