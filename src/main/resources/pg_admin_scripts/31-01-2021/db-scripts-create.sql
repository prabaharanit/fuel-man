--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1
-- Dumped by pg_dump version 12.1

-- Started on 2021-01-31 11:31:10

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 232 (class 1255 OID 32792)
-- Name: set_created_dttm(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.set_created_dttm() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN
  NEW.created_dttm := now();
  RETURN NEW;
END;
$$;


ALTER FUNCTION public.set_created_dttm() OWNER TO postgres;

--
-- TOC entry 233 (class 1255 OID 32794)
-- Name: set_modified_dttm(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.set_modified_dttm() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN
  NEW.modified_dttm := now();
  RETURN NEW;
END;
$$;


ALTER FUNCTION public.set_modified_dttm() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 122891)
-- Name: crd_bk_trans_relation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.crd_bk_trans_relation (
    id integer NOT NULL,
    credit_book_id bigint NOT NULL,
    credit_transaction_id bigint NOT NULL,
    is_deleted integer DEFAULT 0
);


ALTER TABLE public.crd_bk_trans_relation OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 122889)
-- Name: crd_bk_trans_relation_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.crd_bk_trans_relation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.crd_bk_trans_relation_id_seq OWNER TO postgres;

--
-- TOC entry 3019 (class 0 OID 0)
-- Dependencies: 216
-- Name: crd_bk_trans_relation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.crd_bk_trans_relation_id_seq OWNED BY public.crd_bk_trans_relation.id;


--
-- TOC entry 213 (class 1259 OID 49205)
-- Name: credit_book; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.credit_book (
    id bigint NOT NULL,
    vh_id integer NOT NULL,
    fuel_type_id bigint NOT NULL,
    litre_sale_volume double precision NOT NULL,
    amount_of_sale double precision NOT NULL,
    is_deleted integer DEFAULT 0,
    created_dttm timestamp with time zone,
    modified_dttm timestamp with time zone,
    is_paid integer DEFAULT 0,
    fuel_sale_price double precision NOT NULL,
    comments character varying,
    dttm_of_sale timestamp with time zone,
    credit_transaction_id bigint
);


ALTER TABLE public.credit_book OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 49203)
-- Name: credit_book_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.credit_book_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.credit_book_id_seq OWNER TO postgres;

--
-- TOC entry 3020 (class 0 OID 0)
-- Dependencies: 212
-- Name: credit_book_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.credit_book_id_seq OWNED BY public.credit_book.id;


--
-- TOC entry 215 (class 1259 OID 57363)
-- Name: credit_transaction; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.credit_transaction (
    id bigint NOT NULL,
    is_bill_payment boolean,
    vh_owner_id integer NOT NULL,
    credit double precision,
    credited_dttm timestamp with time zone,
    is_deleted integer DEFAULT 0,
    created_dttm timestamp with time zone,
    modified_dttm timestamp with time zone,
    debit double precision NOT NULL,
    description character varying
);


ALTER TABLE public.credit_transaction OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 57361)
-- Name: credit_transaction_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.credit_transaction_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.credit_transaction_id_seq OWNER TO postgres;

--
-- TOC entry 3021 (class 0 OID 0)
-- Dependencies: 214
-- Name: credit_transaction_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.credit_transaction_id_seq OWNED BY public.credit_transaction.id;


--
-- TOC entry 211 (class 1259 OID 49187)
-- Name: daily_fuel_price; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.daily_fuel_price (
    id integer NOT NULL,
    fuel_type_id integer NOT NULL,
    price double precision NOT NULL,
    date_of_sale date NOT NULL,
    is_deleted integer DEFAULT 0,
    created_dttm timestamp with time zone,
    modified_dttm timestamp with time zone,
    from_dttm timestamp with time zone,
    to_dttm timestamp with time zone
);


ALTER TABLE public.daily_fuel_price OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 49185)
-- Name: daily_fuel_price_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.daily_fuel_price_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.daily_fuel_price_id_seq OWNER TO postgres;

--
-- TOC entry 3022 (class 0 OID 0)
-- Dependencies: 210
-- Name: daily_fuel_price_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.daily_fuel_price_id_seq OWNED BY public.daily_fuel_price.id;


--
-- TOC entry 231 (class 1259 OID 147545)
-- Name: daily_stock; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.daily_stock (
    id bigint NOT NULL,
    product_type_id smallint NOT NULL,
    sub_product_type_id smallint NOT NULL,
    opening_stock bigint DEFAULT 0 NOT NULL,
    closing_stock bigint DEFAULT 0,
    is_deleted smallint DEFAULT 0,
    created_dttm timestamp with time zone,
    modified_dttm timestamp with time zone
);


ALTER TABLE public.daily_stock OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 147543)
-- Name: day_stock_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.day_stock_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.day_stock_id_seq OWNER TO postgres;

--
-- TOC entry 3023 (class 0 OID 0)
-- Dependencies: 230
-- Name: day_stock_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.day_stock_id_seq OWNED BY public.daily_stock.id;


--
-- TOC entry 227 (class 1259 OID 147467)
-- Name: fuel_stock; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fuel_stock (
    id smallint NOT NULL,
    volume_added integer NOT NULL,
    added_dttm timestamp with time zone NOT NULL,
    fuel_type_id smallint NOT NULL,
    is_deleted smallint,
    created_dttm timestamp with time zone,
    modified_dttm timestamp with time zone,
    fuel_price double precision NOT NULL
);


ALTER TABLE public.fuel_stock OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 147465)
-- Name: fuel_stock_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.fuel_stock_id_seq
    AS smallint
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.fuel_stock_id_seq OWNER TO postgres;

--
-- TOC entry 3024 (class 0 OID 0)
-- Dependencies: 226
-- Name: fuel_stock_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.fuel_stock_id_seq OWNED BY public.fuel_stock.id;


--
-- TOC entry 202 (class 1259 OID 24585)
-- Name: fuel_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fuel_type (
    type character varying NOT NULL,
    name character varying NOT NULL,
    created_dttm timestamp with time zone,
    modified_dttm timestamp with time zone,
    is_deleted integer DEFAULT 0,
    id smallint NOT NULL
);


ALTER TABLE public.fuel_type OWNER TO postgres;

--
-- TOC entry 3025 (class 0 OID 0)
-- Dependencies: 202
-- Name: TABLE fuel_type; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.fuel_type IS 'table to contain list of fuel types available for RO [retail outlet]';


--
-- TOC entry 203 (class 1259 OID 32812)
-- Name: fueltype_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.fueltype_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.fueltype_id_seq OWNER TO postgres;

--
-- TOC entry 3026 (class 0 OID 0)
-- Dependencies: 203
-- Name: fueltype_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.fueltype_id_seq OWNED BY public.fuel_type.id;


--
-- TOC entry 223 (class 1259 OID 139301)
-- Name: lubes_brand; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lubes_brand (
    id smallint NOT NULL,
    name character varying NOT NULL,
    is_deleted smallint DEFAULT 0,
    created_dttm timestamp with time zone,
    modified_dttm timestamp with time zone
);


ALTER TABLE public.lubes_brand OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 139299)
-- Name: lubes_brand_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.lubes_brand_id_seq
    AS smallint
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.lubes_brand_id_seq OWNER TO postgres;

--
-- TOC entry 3027 (class 0 OID 0)
-- Dependencies: 222
-- Name: lubes_brand_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.lubes_brand_id_seq OWNED BY public.lubes_brand.id;


--
-- TOC entry 229 (class 1259 OID 147522)
-- Name: lubricant_stock; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lubricant_stock (
    id bigint NOT NULL,
    lube_type_id smallint NOT NULL,
    unit smallint NOT NULL,
    quantity smallint NOT NULL,
    total_quantity integer NOT NULL,
    is_deleted smallint NOT NULL,
    created_dttm timestamp with time zone,
    modified_dttm timestamp with time zone
);


ALTER TABLE public.lubricant_stock OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 147520)
-- Name: lubricant_stock_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.lubricant_stock_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.lubricant_stock_id_seq OWNER TO postgres;

--
-- TOC entry 3028 (class 0 OID 0)
-- Dependencies: 228
-- Name: lubricant_stock_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.lubricant_stock_id_seq OWNED BY public.lubricant_stock.id;


--
-- TOC entry 225 (class 1259 OID 139325)
-- Name: lubricant_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lubricant_type (
    id smallint NOT NULL,
    name character varying NOT NULL,
    type character varying NOT NULL,
    is_deleted smallint,
    lubes_brand_id smallint NOT NULL,
    created_dttm timestamp with time zone,
    modified_dttm timestamp with time zone,
    package_type_id smallint NOT NULL
);


ALTER TABLE public.lubricant_type OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 139323)
-- Name: lubricant_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.lubricant_type_id_seq
    AS smallint
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.lubricant_type_id_seq OWNER TO postgres;

--
-- TOC entry 3029 (class 0 OID 0)
-- Dependencies: 224
-- Name: lubricant_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.lubricant_type_id_seq OWNED BY public.lubricant_type.id;


--
-- TOC entry 221 (class 1259 OID 139288)
-- Name: package_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.package_type (
    id smallint NOT NULL,
    type character varying NOT NULL,
    unit character varying NOT NULL,
    is_deleted smallint,
    created_dttm timestamp with time zone,
    modified_dttm timestamp with time zone
);


ALTER TABLE public.package_type OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 139286)
-- Name: package_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.package_type_id_seq
    AS smallint
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.package_type_id_seq OWNER TO postgres;

--
-- TOC entry 3030 (class 0 OID 0)
-- Dependencies: 220
-- Name: package_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.package_type_id_seq OWNED BY public.package_type.id;


--
-- TOC entry 219 (class 1259 OID 139275)
-- Name: product_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_type (
    id smallint NOT NULL,
    type character varying NOT NULL,
    is_deleted integer,
    created_dttm timestamp with time zone NOT NULL,
    modified_dttm timestamp with time zone
);


ALTER TABLE public.product_type OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 139273)
-- Name: product_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_type_id_seq
    AS smallint
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_type_id_seq OWNER TO postgres;

--
-- TOC entry 3031 (class 0 OID 0)
-- Dependencies: 218
-- Name: product_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.product_type_id_seq OWNED BY public.product_type.id;


--
-- TOC entry 209 (class 1259 OID 49163)
-- Name: vehicle; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vehicle (
    id integer NOT NULL,
    vh_type_id smallint NOT NULL,
    vh_owner_id integer NOT NULL,
    number_plate character varying NOT NULL,
    created_dttm timestamp with time zone,
    modified_dttm timestamp with time zone,
    is_deleted integer DEFAULT 0
);


ALTER TABLE public.vehicle OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 49161)
-- Name: vehicle_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.vehicle_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.vehicle_id_seq OWNER TO postgres;

--
-- TOC entry 3032 (class 0 OID 0)
-- Dependencies: 208
-- Name: vehicle_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vehicle_id_seq OWNED BY public.vehicle.id;


--
-- TOC entry 205 (class 1259 OID 40983)
-- Name: vehicle_owner; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vehicle_owner (
    id integer NOT NULL,
    name character varying NOT NULL,
    mobile_no character varying NOT NULL,
    transport_name character varying NOT NULL,
    created_dttm timestamp with time zone,
    modified_dttm timestamp with time zone,
    is_deleted integer DEFAULT 0
);


ALTER TABLE public.vehicle_owner OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 40981)
-- Name: vehicle_owner_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.vehicle_owner_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.vehicle_owner_id_seq OWNER TO postgres;

--
-- TOC entry 3033 (class 0 OID 0)
-- Dependencies: 204
-- Name: vehicle_owner_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vehicle_owner_id_seq OWNED BY public.vehicle_owner.id;


--
-- TOC entry 207 (class 1259 OID 41009)
-- Name: vehicletype; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vehicletype (
    id integer NOT NULL,
    type character varying NOT NULL,
    name character varying NOT NULL,
    created_dttm timestamp with time zone,
    modified_dttm timestamp with time zone,
    is_deleted integer DEFAULT 0
);


ALTER TABLE public.vehicletype OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 41007)
-- Name: vehicletype_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.vehicletype_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.vehicletype_id_seq OWNER TO postgres;

--
-- TOC entry 3034 (class 0 OID 0)
-- Dependencies: 206
-- Name: vehicletype_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vehicletype_id_seq OWNED BY public.vehicletype.id;


--
-- TOC entry 2798 (class 2604 OID 122894)
-- Name: crd_bk_trans_relation id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.crd_bk_trans_relation ALTER COLUMN id SET DEFAULT nextval('public.crd_bk_trans_relation_id_seq'::regclass);


--
-- TOC entry 2794 (class 2604 OID 57354)
-- Name: credit_book id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_book ALTER COLUMN id SET DEFAULT nextval('public.credit_book_id_seq'::regclass);


--
-- TOC entry 2796 (class 2604 OID 57366)
-- Name: credit_transaction id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_transaction ALTER COLUMN id SET DEFAULT nextval('public.credit_transaction_id_seq'::regclass);


--
-- TOC entry 2791 (class 2604 OID 49190)
-- Name: daily_fuel_price id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.daily_fuel_price ALTER COLUMN id SET DEFAULT nextval('public.daily_fuel_price_id_seq'::regclass);


--
-- TOC entry 2807 (class 2604 OID 147548)
-- Name: daily_stock id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.daily_stock ALTER COLUMN id SET DEFAULT nextval('public.day_stock_id_seq'::regclass);


--
-- TOC entry 2805 (class 2604 OID 147470)
-- Name: fuel_stock id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fuel_stock ALTER COLUMN id SET DEFAULT nextval('public.fuel_stock_id_seq'::regclass);


--
-- TOC entry 2784 (class 2604 OID 32824)
-- Name: fuel_type id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fuel_type ALTER COLUMN id SET DEFAULT nextval('public.fueltype_id_seq'::regclass);


--
-- TOC entry 2802 (class 2604 OID 139304)
-- Name: lubes_brand id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lubes_brand ALTER COLUMN id SET DEFAULT nextval('public.lubes_brand_id_seq'::regclass);


--
-- TOC entry 2806 (class 2604 OID 147525)
-- Name: lubricant_stock id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lubricant_stock ALTER COLUMN id SET DEFAULT nextval('public.lubricant_stock_id_seq'::regclass);


--
-- TOC entry 2804 (class 2604 OID 139328)
-- Name: lubricant_type id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lubricant_type ALTER COLUMN id SET DEFAULT nextval('public.lubricant_type_id_seq'::regclass);


--
-- TOC entry 2801 (class 2604 OID 139291)
-- Name: package_type id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.package_type ALTER COLUMN id SET DEFAULT nextval('public.package_type_id_seq'::regclass);


--
-- TOC entry 2800 (class 2604 OID 139278)
-- Name: product_type id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_type ALTER COLUMN id SET DEFAULT nextval('public.product_type_id_seq'::regclass);


--
-- TOC entry 2789 (class 2604 OID 49166)
-- Name: vehicle id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicle ALTER COLUMN id SET DEFAULT nextval('public.vehicle_id_seq'::regclass);


--
-- TOC entry 2785 (class 2604 OID 40986)
-- Name: vehicle_owner id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicle_owner ALTER COLUMN id SET DEFAULT nextval('public.vehicle_owner_id_seq'::regclass);


--
-- TOC entry 2787 (class 2604 OID 41012)
-- Name: vehicletype id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicletype ALTER COLUMN id SET DEFAULT nextval('public.vehicletype_id_seq'::regclass);


--
-- TOC entry 2836 (class 2606 OID 122897)
-- Name: crd_bk_trans_relation crd_bk_trans_relation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.crd_bk_trans_relation
    ADD CONSTRAINT crd_bk_trans_relation_pkey PRIMARY KEY (id);


--
-- TOC entry 2830 (class 2606 OID 57356)
-- Name: credit_book credit_book_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_book
    ADD CONSTRAINT credit_book_pkey PRIMARY KEY (id);


--
-- TOC entry 2832 (class 2606 OID 114700)
-- Name: credit_book credit_sale_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_book
    ADD CONSTRAINT credit_sale_unique UNIQUE (vh_id, fuel_type_id, dttm_of_sale);


--
-- TOC entry 2834 (class 2606 OID 57369)
-- Name: credit_transaction credit_transaction_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_transaction
    ADD CONSTRAINT credit_transaction_pkey PRIMARY KEY (id);


--
-- TOC entry 2826 (class 2606 OID 49193)
-- Name: daily_fuel_price daily_fuel_price_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.daily_fuel_price
    ADD CONSTRAINT daily_fuel_price_pkey PRIMARY KEY (id);


--
-- TOC entry 2848 (class 2606 OID 147550)
-- Name: daily_stock day_stock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.daily_stock
    ADD CONSTRAINT day_stock_pkey PRIMARY KEY (id);


--
-- TOC entry 2844 (class 2606 OID 147472)
-- Name: fuel_stock fuel_stock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fuel_stock
    ADD CONSTRAINT fuel_stock_pkey PRIMARY KEY (id);


--
-- TOC entry 2828 (class 2606 OID 73742)
-- Name: daily_fuel_price fuel_type_dt_of_sale_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.daily_fuel_price
    ADD CONSTRAINT fuel_type_dt_of_sale_unique UNIQUE (fuel_type_id, date_of_sale);


--
-- TOC entry 2812 (class 2606 OID 32826)
-- Name: fuel_type fueltype_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fuel_type
    ADD CONSTRAINT fueltype_pkey PRIMARY KEY (id);


--
-- TOC entry 2842 (class 2606 OID 139309)
-- Name: lubes_brand lubes_brand_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lubes_brand
    ADD CONSTRAINT lubes_brand_pkey PRIMARY KEY (id);


--
-- TOC entry 2846 (class 2606 OID 147527)
-- Name: lubricant_stock lubricant_stock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lubricant_stock
    ADD CONSTRAINT lubricant_stock_pkey PRIMARY KEY (id);


--
-- TOC entry 2840 (class 2606 OID 139296)
-- Name: package_type package_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.package_type
    ADD CONSTRAINT package_type_pkey PRIMARY KEY (id);


--
-- TOC entry 2838 (class 2606 OID 139283)
-- Name: product_type product_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_type
    ADD CONSTRAINT product_type_pkey PRIMARY KEY (id);


--
-- TOC entry 2814 (class 2606 OID 32842)
-- Name: fuel_type type_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fuel_type
    ADD CONSTRAINT type_unique UNIQUE (type);


--
-- TOC entry 2822 (class 2606 OID 81930)
-- Name: vehicle vehicle_number_plate_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicle
    ADD CONSTRAINT vehicle_number_plate_key UNIQUE (number_plate);


--
-- TOC entry 2816 (class 2606 OID 40992)
-- Name: vehicle_owner vehicle_owner_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicle_owner
    ADD CONSTRAINT vehicle_owner_pkey PRIMARY KEY (id);


--
-- TOC entry 2824 (class 2606 OID 49172)
-- Name: vehicle vehicle_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicle
    ADD CONSTRAINT vehicle_pkey PRIMARY KEY (id);


--
-- TOC entry 2818 (class 2606 OID 41018)
-- Name: vehicletype vehicletype_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicletype
    ADD CONSTRAINT vehicletype_pkey PRIMARY KEY (id);


--
-- TOC entry 2820 (class 2606 OID 41020)
-- Name: vehicletype vh_type_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicletype
    ADD CONSTRAINT vh_type_unique UNIQUE (type);


--
-- TOC entry 2872 (class 2620 OID 49227)
-- Name: credit_book set_created_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_created_dttm BEFORE INSERT ON public.credit_book FOR EACH ROW EXECUTE FUNCTION public.set_created_dttm();


--
-- TOC entry 2874 (class 2620 OID 57380)
-- Name: credit_transaction set_created_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_created_dttm BEFORE INSERT ON public.credit_transaction FOR EACH ROW EXECUTE FUNCTION public.set_created_dttm();


--
-- TOC entry 2870 (class 2620 OID 49199)
-- Name: daily_fuel_price set_created_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_created_dttm BEFORE INSERT ON public.daily_fuel_price FOR EACH ROW EXECUTE FUNCTION public.set_created_dttm();


--
-- TOC entry 2886 (class 2620 OID 155659)
-- Name: daily_stock set_created_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_created_dttm BEFORE INSERT ON public.daily_stock FOR EACH ROW EXECUTE FUNCTION public.set_created_dttm();


--
-- TOC entry 2884 (class 2620 OID 155657)
-- Name: fuel_stock set_created_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_created_dttm BEFORE INSERT ON public.fuel_stock FOR EACH ROW EXECUTE FUNCTION public.set_created_dttm();


--
-- TOC entry 2862 (class 2620 OID 32793)
-- Name: fuel_type set_created_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_created_dttm BEFORE INSERT ON public.fuel_type FOR EACH ROW EXECUTE FUNCTION public.set_created_dttm();


--
-- TOC entry 2880 (class 2620 OID 139311)
-- Name: lubes_brand set_created_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_created_dttm BEFORE INSERT ON public.lubes_brand FOR EACH ROW EXECUTE FUNCTION public.set_created_dttm();


--
-- TOC entry 2882 (class 2620 OID 139338)
-- Name: lubricant_type set_created_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_created_dttm BEFORE INSERT ON public.lubricant_type FOR EACH ROW EXECUTE FUNCTION public.set_created_dttm();


--
-- TOC entry 2878 (class 2620 OID 139297)
-- Name: package_type set_created_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_created_dttm BEFORE INSERT ON public.package_type FOR EACH ROW EXECUTE FUNCTION public.set_created_dttm();


--
-- TOC entry 2876 (class 2620 OID 139285)
-- Name: product_type set_created_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_created_dttm BEFORE INSERT ON public.product_type FOR EACH ROW EXECUTE FUNCTION public.set_created_dttm();


--
-- TOC entry 2868 (class 2620 OID 49183)
-- Name: vehicle set_created_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_created_dttm BEFORE INSERT ON public.vehicle FOR EACH ROW EXECUTE FUNCTION public.set_created_dttm();


--
-- TOC entry 2864 (class 2620 OID 40993)
-- Name: vehicle_owner set_created_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_created_dttm BEFORE INSERT ON public.vehicle_owner FOR EACH ROW EXECUTE FUNCTION public.set_created_dttm();


--
-- TOC entry 2866 (class 2620 OID 41021)
-- Name: vehicletype set_created_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_created_dttm BEFORE INSERT ON public.vehicletype FOR EACH ROW EXECUTE FUNCTION public.set_created_dttm();


--
-- TOC entry 2873 (class 2620 OID 49228)
-- Name: credit_book set_modified_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_modified_dttm BEFORE UPDATE ON public.credit_book FOR EACH ROW EXECUTE FUNCTION public.set_modified_dttm();


--
-- TOC entry 2875 (class 2620 OID 57381)
-- Name: credit_transaction set_modified_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_modified_dttm BEFORE UPDATE ON public.credit_transaction FOR EACH ROW EXECUTE FUNCTION public.set_modified_dttm();


--
-- TOC entry 2871 (class 2620 OID 49200)
-- Name: daily_fuel_price set_modified_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_modified_dttm BEFORE UPDATE ON public.daily_fuel_price FOR EACH ROW EXECUTE FUNCTION public.set_modified_dttm();


--
-- TOC entry 2887 (class 2620 OID 155660)
-- Name: daily_stock set_modified_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_modified_dttm BEFORE UPDATE ON public.daily_stock FOR EACH ROW EXECUTE FUNCTION public.set_modified_dttm();


--
-- TOC entry 2885 (class 2620 OID 155658)
-- Name: fuel_stock set_modified_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_modified_dttm BEFORE UPDATE ON public.fuel_stock FOR EACH ROW EXECUTE FUNCTION public.set_modified_dttm();


--
-- TOC entry 2863 (class 2620 OID 32797)
-- Name: fuel_type set_modified_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_modified_dttm BEFORE UPDATE ON public.fuel_type FOR EACH ROW EXECUTE FUNCTION public.set_modified_dttm();


--
-- TOC entry 2881 (class 2620 OID 139310)
-- Name: lubes_brand set_modified_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_modified_dttm BEFORE UPDATE ON public.lubes_brand FOR EACH ROW EXECUTE FUNCTION public.set_modified_dttm();


--
-- TOC entry 2883 (class 2620 OID 139337)
-- Name: lubricant_type set_modified_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_modified_dttm BEFORE UPDATE ON public.lubricant_type FOR EACH ROW EXECUTE FUNCTION public.set_modified_dttm();


--
-- TOC entry 2879 (class 2620 OID 139298)
-- Name: package_type set_modified_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_modified_dttm BEFORE UPDATE ON public.package_type FOR EACH ROW EXECUTE FUNCTION public.set_modified_dttm();


--
-- TOC entry 2877 (class 2620 OID 139284)
-- Name: product_type set_modified_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_modified_dttm BEFORE UPDATE ON public.product_type FOR EACH ROW EXECUTE FUNCTION public.set_modified_dttm();


--
-- TOC entry 2869 (class 2620 OID 49184)
-- Name: vehicle set_modified_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_modified_dttm BEFORE UPDATE ON public.vehicle FOR EACH ROW EXECUTE FUNCTION public.set_modified_dttm();


--
-- TOC entry 2865 (class 2620 OID 40994)
-- Name: vehicle_owner set_modified_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_modified_dttm BEFORE UPDATE ON public.vehicle_owner FOR EACH ROW EXECUTE FUNCTION public.set_modified_dttm();


--
-- TOC entry 2867 (class 2620 OID 41022)
-- Name: vehicletype set_modified_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_modified_dttm BEFORE UPDATE ON public.vehicletype FOR EACH ROW EXECUTE FUNCTION public.set_modified_dttm();


--
-- TOC entry 2854 (class 2606 OID 122908)
-- Name: credit_book crd_trans_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_book
    ADD CONSTRAINT crd_trans_fkey FOREIGN KEY (credit_transaction_id) REFERENCES public.credit_transaction(id) NOT VALID;


--
-- TOC entry 2856 (class 2606 OID 122898)
-- Name: crd_bk_trans_relation credit_book_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.crd_bk_trans_relation
    ADD CONSTRAINT credit_book_id_fkey FOREIGN KEY (credit_book_id) REFERENCES public.credit_book(id);


--
-- TOC entry 2857 (class 2606 OID 122903)
-- Name: crd_bk_trans_relation credit_transaction_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.crd_bk_trans_relation
    ADD CONSTRAINT credit_transaction_id_fkey FOREIGN KEY (credit_transaction_id) REFERENCES public.credit_transaction(id);


--
-- TOC entry 2860 (class 2606 OID 147473)
-- Name: fuel_stock fk_fuel_type_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fuel_stock
    ADD CONSTRAINT fk_fuel_type_id FOREIGN KEY (fuel_type_id) REFERENCES public.fuel_type(id);


--
-- TOC entry 2858 (class 2606 OID 139332)
-- Name: lubricant_type fk_lubes_brand_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lubricant_type
    ADD CONSTRAINT fk_lubes_brand_id FOREIGN KEY (lubes_brand_id) REFERENCES public.lubes_brand(id);


--
-- TOC entry 2859 (class 2606 OID 147533)
-- Name: lubricant_type fk_package_type_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lubricant_type
    ADD CONSTRAINT fk_package_type_id FOREIGN KEY (package_type_id) REFERENCES public.package_type(id) NOT VALID;


--
-- TOC entry 2861 (class 2606 OID 147551)
-- Name: daily_stock fk_product_type_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.daily_stock
    ADD CONSTRAINT fk_product_type_id FOREIGN KEY (product_type_id) REFERENCES public.product_type(id);


--
-- TOC entry 2853 (class 2606 OID 98321)
-- Name: credit_book fuel_type_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_book
    ADD CONSTRAINT fuel_type_id FOREIGN KEY (fuel_type_id) REFERENCES public.fuel_type(id) NOT VALID;


--
-- TOC entry 2851 (class 2606 OID 49194)
-- Name: daily_fuel_price fuel_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.daily_fuel_price
    ADD CONSTRAINT fuel_type_id_fkey FOREIGN KEY (fuel_type_id) REFERENCES public.fuel_type(id);


--
-- TOC entry 2852 (class 2606 OID 49217)
-- Name: credit_book vh_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_book
    ADD CONSTRAINT vh_id_fkey FOREIGN KEY (vh_id) REFERENCES public.vehicle(id);


--
-- TOC entry 2850 (class 2606 OID 49178)
-- Name: vehicle vh_owner_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicle
    ADD CONSTRAINT vh_owner_id_fkey FOREIGN KEY (vh_owner_id) REFERENCES public.vehicle_owner(id) NOT VALID;


--
-- TOC entry 2855 (class 2606 OID 57375)
-- Name: credit_transaction vh_owner_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_transaction
    ADD CONSTRAINT vh_owner_id_fkey FOREIGN KEY (vh_owner_id) REFERENCES public.vehicle_owner(id);


--
-- TOC entry 2849 (class 2606 OID 49173)
-- Name: vehicle vh_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicle
    ADD CONSTRAINT vh_type_id_fkey FOREIGN KEY (vh_type_id) REFERENCES public.vehicletype(id) NOT VALID;


-- Completed on 2021-01-31 11:31:12

--
-- PostgreSQL database dump complete
--

