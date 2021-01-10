--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1
-- Dumped by pg_dump version 12.1

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
-- Name: crd_bk_trans_relation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.crd_bk_trans_relation_id_seq OWNED BY public.crd_bk_trans_relation.id;


--
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
-- Name: credit_book_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.credit_book_id_seq OWNED BY public.credit_book.id;


--
-- Name: credit_transaction; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.credit_transaction (
    id bigint NOT NULL,
    is_bill_payment boolean,
    vh_owner_id integer NOT NULL,
    credit double precision NOT NULL,
    credited_dttm timestamp with time zone NOT NULL,
    is_deleted integer DEFAULT 0,
    created_dttm timestamp with time zone,
    modified_dttm timestamp with time zone,
    debit double precision
);


ALTER TABLE public.credit_transaction OWNER TO postgres;

--
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
-- Name: credit_transaction_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.credit_transaction_id_seq OWNED BY public.credit_transaction.id;


--
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
-- Name: daily_fuel_price_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.daily_fuel_price_id_seq OWNED BY public.daily_fuel_price.id;


--
-- Name: fueltype; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fueltype (
    type character varying NOT NULL,
    name character varying NOT NULL,
    created_dttm timestamp with time zone,
    modified_dttm timestamp with time zone,
    is_deleted integer DEFAULT 0,
    id smallint NOT NULL
);


ALTER TABLE public.fueltype OWNER TO postgres;

--
-- Name: TABLE fueltype; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.fueltype IS 'table to contain list of fuel types available for RO [retail outlet]';


--
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
-- Name: fueltype_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.fueltype_id_seq OWNED BY public.fueltype.id;


--
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
-- Name: vehicle_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vehicle_id_seq OWNED BY public.vehicle.id;


--
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
-- Name: vehicle_owner_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vehicle_owner_id_seq OWNED BY public.vehicle_owner.id;


--
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
-- Name: vehicletype_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vehicletype_id_seq OWNED BY public.vehicletype.id;


--
-- Name: crd_bk_trans_relation id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.crd_bk_trans_relation ALTER COLUMN id SET DEFAULT nextval('public.crd_bk_trans_relation_id_seq'::regclass);


--
-- Name: credit_book id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_book ALTER COLUMN id SET DEFAULT nextval('public.credit_book_id_seq'::regclass);


--
-- Name: credit_transaction id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_transaction ALTER COLUMN id SET DEFAULT nextval('public.credit_transaction_id_seq'::regclass);


--
-- Name: daily_fuel_price id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.daily_fuel_price ALTER COLUMN id SET DEFAULT nextval('public.daily_fuel_price_id_seq'::regclass);


--
-- Name: fueltype id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fueltype ALTER COLUMN id SET DEFAULT nextval('public.fueltype_id_seq'::regclass);


--
-- Name: vehicle id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicle ALTER COLUMN id SET DEFAULT nextval('public.vehicle_id_seq'::regclass);


--
-- Name: vehicle_owner id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicle_owner ALTER COLUMN id SET DEFAULT nextval('public.vehicle_owner_id_seq'::regclass);


--
-- Name: vehicletype id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicletype ALTER COLUMN id SET DEFAULT nextval('public.vehicletype_id_seq'::regclass);


--
-- Name: crd_bk_trans_relation crd_bk_trans_relation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.crd_bk_trans_relation
    ADD CONSTRAINT crd_bk_trans_relation_pkey PRIMARY KEY (id);


--
-- Name: credit_book credit_book_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_book
    ADD CONSTRAINT credit_book_pkey PRIMARY KEY (id);


--
-- Name: credit_book credit_sale_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_book
    ADD CONSTRAINT credit_sale_unique UNIQUE (vh_id, fuel_type_id, dttm_of_sale);


--
-- Name: credit_transaction credit_transaction_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_transaction
    ADD CONSTRAINT credit_transaction_pkey PRIMARY KEY (id);


--
-- Name: daily_fuel_price daily_fuel_price_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.daily_fuel_price
    ADD CONSTRAINT daily_fuel_price_pkey PRIMARY KEY (id);


--
-- Name: daily_fuel_price fuel_type_dt_of_sale_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.daily_fuel_price
    ADD CONSTRAINT fuel_type_dt_of_sale_unique UNIQUE (fuel_type_id, date_of_sale);


--
-- Name: fueltype fueltype_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fueltype
    ADD CONSTRAINT fueltype_pkey PRIMARY KEY (id);


--
-- Name: fueltype type_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fueltype
    ADD CONSTRAINT type_unique UNIQUE (type);


--
-- Name: vehicle vehicle_number_plate_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicle
    ADD CONSTRAINT vehicle_number_plate_key UNIQUE (number_plate);


--
-- Name: vehicle_owner vehicle_owner_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicle_owner
    ADD CONSTRAINT vehicle_owner_pkey PRIMARY KEY (id);


--
-- Name: vehicle vehicle_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicle
    ADD CONSTRAINT vehicle_pkey PRIMARY KEY (id);


--
-- Name: vehicletype vehicletype_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicletype
    ADD CONSTRAINT vehicletype_pkey PRIMARY KEY (id);


--
-- Name: vehicletype vh_type_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicletype
    ADD CONSTRAINT vh_type_unique UNIQUE (type);


--
-- Name: credit_book set_created_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_created_dttm BEFORE INSERT ON public.credit_book FOR EACH ROW EXECUTE FUNCTION public.set_created_dttm();


--
-- Name: credit_transaction set_created_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_created_dttm BEFORE INSERT ON public.credit_transaction FOR EACH ROW EXECUTE FUNCTION public.set_created_dttm();


--
-- Name: daily_fuel_price set_created_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_created_dttm BEFORE INSERT ON public.daily_fuel_price FOR EACH ROW EXECUTE FUNCTION public.set_created_dttm();


--
-- Name: fueltype set_created_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_created_dttm BEFORE INSERT ON public.fueltype FOR EACH ROW EXECUTE FUNCTION public.set_created_dttm();


--
-- Name: vehicle set_created_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_created_dttm BEFORE INSERT ON public.vehicle FOR EACH ROW EXECUTE FUNCTION public.set_created_dttm();


--
-- Name: vehicle_owner set_created_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_created_dttm BEFORE INSERT ON public.vehicle_owner FOR EACH ROW EXECUTE FUNCTION public.set_created_dttm();


--
-- Name: vehicletype set_created_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_created_dttm BEFORE INSERT ON public.vehicletype FOR EACH ROW EXECUTE FUNCTION public.set_created_dttm();


--
-- Name: credit_book set_modified_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_modified_dttm BEFORE UPDATE ON public.credit_book FOR EACH ROW EXECUTE FUNCTION public.set_modified_dttm();


--
-- Name: credit_transaction set_modified_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_modified_dttm BEFORE UPDATE ON public.credit_transaction FOR EACH ROW EXECUTE FUNCTION public.set_modified_dttm();


--
-- Name: daily_fuel_price set_modified_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_modified_dttm BEFORE UPDATE ON public.daily_fuel_price FOR EACH ROW EXECUTE FUNCTION public.set_modified_dttm();


--
-- Name: fueltype set_modified_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_modified_dttm BEFORE UPDATE ON public.fueltype FOR EACH ROW EXECUTE FUNCTION public.set_modified_dttm();


--
-- Name: vehicle set_modified_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_modified_dttm BEFORE UPDATE ON public.vehicle FOR EACH ROW EXECUTE FUNCTION public.set_modified_dttm();


--
-- Name: vehicle_owner set_modified_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_modified_dttm BEFORE UPDATE ON public.vehicle_owner FOR EACH ROW EXECUTE FUNCTION public.set_modified_dttm();


--
-- Name: vehicletype set_modified_dttm; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER set_modified_dttm BEFORE UPDATE ON public.vehicletype FOR EACH ROW EXECUTE FUNCTION public.set_modified_dttm();


--
-- Name: credit_book crd_trans_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_book
    ADD CONSTRAINT crd_trans_fkey FOREIGN KEY (credit_transaction_id) REFERENCES public.credit_transaction(id) NOT VALID;


--
-- Name: crd_bk_trans_relation credit_book_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.crd_bk_trans_relation
    ADD CONSTRAINT credit_book_id_fkey FOREIGN KEY (credit_book_id) REFERENCES public.credit_book(id);


--
-- Name: crd_bk_trans_relation credit_transaction_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.crd_bk_trans_relation
    ADD CONSTRAINT credit_transaction_id_fkey FOREIGN KEY (credit_transaction_id) REFERENCES public.credit_transaction(id);


--
-- Name: credit_book fuel_type_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_book
    ADD CONSTRAINT fuel_type_id FOREIGN KEY (fuel_type_id) REFERENCES public.fueltype(id) NOT VALID;


--
-- Name: daily_fuel_price fuel_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.daily_fuel_price
    ADD CONSTRAINT fuel_type_id_fkey FOREIGN KEY (fuel_type_id) REFERENCES public.fueltype(id);


--
-- Name: credit_book vh_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_book
    ADD CONSTRAINT vh_id_fkey FOREIGN KEY (vh_id) REFERENCES public.vehicle(id);


--
-- Name: vehicle vh_owner_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicle
    ADD CONSTRAINT vh_owner_id_fkey FOREIGN KEY (vh_owner_id) REFERENCES public.vehicle_owner(id) NOT VALID;


--
-- Name: credit_transaction vh_owner_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_transaction
    ADD CONSTRAINT vh_owner_id_fkey FOREIGN KEY (vh_owner_id) REFERENCES public.vehicle_owner(id);


--
-- Name: vehicle vh_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicle
    ADD CONSTRAINT vh_type_id_fkey FOREIGN KEY (vh_type_id) REFERENCES public.vehicletype(id) NOT VALID;


--
-- PostgreSQL database dump complete
--

