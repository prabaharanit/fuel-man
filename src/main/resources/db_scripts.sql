-- Table: public.fueltype

-- DROP TABLE public.fueltype;

CREATE TABLE public.fueltype
(
	id serial NOT NULL,
    type character varying COLLATE pg_catalog."default" NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    created_dttm timestamp with time zone,
    modified_dttm timestamp with time zone,
    is_deleted integer DEFAULT 0,
    CONSTRAINT fueltype_pkey PRIMARY KEY (id),
    CONSTRAINT type_unique UNIQUE (type)
)

TABLESPACE pg_default;

ALTER TABLE public.fueltype
    OWNER to postgres;
COMMENT ON TABLE public.fueltype
    IS 'table to contain list of fuel types available for RO [retail outlet]';

-- Trigger: set_created_dttm

-- DROP TRIGGER set_created_dttm ON public.fueltype;

CREATE TRIGGER set_created_dttm
    BEFORE INSERT
    ON public.fueltype
    FOR EACH ROW
    EXECUTE PROCEDURE public.set_created_dttm();

-- Trigger: set_modified_dttm

-- DROP TRIGGER set_modified_dttm ON public.fueltype;

CREATE TRIGGER set_modified_dttm
    BEFORE UPDATE 
    ON public.fueltype
    FOR EACH ROW
    EXECUTE PROCEDURE public.set_modified_dttm();
    
    
-- Table: public.vehicle_owner

-- DROP TABLE public.vehicle_owner;

CREATE TABLE public.vehicle_owner
(
    id serial NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    mobile_no integer not null,
    transport_name character varying COLLATE pg_catalog."default" NOT NULL,
    created_dttm timestamp with time zone,
    modified_dttm timestamp with time zone,
    is_deleted integer DEFAULT 0,
    CONSTRAINT vehicle_owner_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;    


-- Trigger: set_created_dttm

-- DROP TRIGGER set_created_dttm ON public.vehicle_owner;

CREATE TRIGGER set_created_dttm
    BEFORE INSERT
    ON public.vehicle_owner
    FOR EACH ROW
    EXECUTE PROCEDURE public.set_created_dttm();

-- Trigger: set_modified_dttm

-- DROP TRIGGER set_modified_dttm ON public.vehicle_owner;

CREATE TRIGGER set_modified_dttm
    BEFORE UPDATE 
    ON public.vehicle_owner
    FOR EACH ROW
    EXECUTE PROCEDURE public.set_modified_dttm();

    
-- Table: public.vehicletype

-- DROP TABLE public.vehicletype;

CREATE TABLE public.vehicletype
(
	id serial NOT NULL,
    type character varying COLLATE pg_catalog."default" NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    created_dttm timestamp with time zone,
    modified_dttm timestamp with time zone,
    is_deleted integer DEFAULT 0,
    CONSTRAINT vehicletype_pkey PRIMARY KEY (id),
    CONSTRAINT vh_type_unique UNIQUE (type)
)

TABLESPACE pg_default; 

-- Trigger: set_created_dttm

-- DROP TRIGGER set_created_dttm ON public.vehicletype;

CREATE TRIGGER set_created_dttm
    BEFORE INSERT
    ON public.vehicletype
    FOR EACH ROW
    EXECUTE PROCEDURE public.set_created_dttm();

-- Trigger: set_modified_dttm

-- DROP TRIGGER set_modified_dttm ON public.fueltype;

CREATE TRIGGER set_modified_dttm
    BEFORE UPDATE 
    ON public.vehicletype
    FOR EACH ROW
    EXECUTE PROCEDURE public.set_modified_dttm();
    

    
-- Table: public.vehicle

-- DROP TABLE public.vehicle;

CREATE TABLE public.vehicle
(
    id serial NOT NULL ,
    vh_type_id smallint NOT NULL,
    vh_owner_id integer NOT NULL,
    number_plate character varying COLLATE pg_catalog."default" NOT NULL,
    created_dttm timestamp with time zone,
    modified_dttm timestamp with time zone,
    is_deleted integer DEFAULT 0,
    CONSTRAINT vehicle_pkey PRIMARY KEY (id),
    CONSTRAINT vh_owner_id_fkey FOREIGN KEY (vh_owner_id)
        REFERENCES public.vehicle_owner (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT vh_type_id_fkey FOREIGN KEY (vh_type_id)
        REFERENCES public.vehicletype (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE public.vehicle
    OWNER to postgres;
-- Trigger: set_created_dttm

-- DROP TRIGGER set_created_dttm ON public.vehicle;

CREATE TRIGGER set_created_dttm
    BEFORE INSERT
    ON public.vehicle
    FOR EACH ROW
    EXECUTE PROCEDURE public.set_created_dttm();

-- Trigger: set_modified_dttm

-- DROP TRIGGER set_modified_dttm ON public.vehicle;

CREATE TRIGGER set_modified_dttm
    BEFORE UPDATE 
    ON public.vehicle
    FOR EACH ROW
    EXECUTE PROCEDURE public.set_modified_dttm();

    
-- Table: public.daily_fuel_price

-- DROP TABLE public.daily_fuel_price;

CREATE TABLE public.daily_fuel_price
(
	id serial NOT NULL,
    fuel_type_id integer NOT NULL,
    price float NOT NULL,
    date_of_sale date NOT NULL,
    is_deleted integer DEFAULT 0,
    created_dttm timestamp with time zone,
    modified_dttm timestamp with time zone,
    CONSTRAINT daily_fuel_price_pkey PRIMARY KEY (id),
    CONSTRAINT fuel_type_id_fkey FOREIGN KEY (fuel_type_id)
    REFERENCES public.fueltype (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID
)

TABLESPACE pg_default;


-- Trigger: set_created_dttm

-- DROP TRIGGER set_created_dttm ON public.daily_fuel_price;

CREATE TRIGGER set_created_dttm
    BEFORE INSERT
    ON public.daily_fuel_price
    FOR EACH ROW
    EXECUTE PROCEDURE public.set_created_dttm();

-- Trigger: set_modified_dttm

-- DROP TRIGGER set_modified_dttm ON public.daily_fuel_price;

CREATE TRIGGER set_modified_dttm
    BEFORE UPDATE 
    ON public.daily_fuel_price
    FOR EACH ROW
    EXECUTE PROCEDURE public.set_modified_dttm();
    
    

-- Table: public.credit_book

-- DROP TABLE public.credit_book;

CREATE TABLE public.credit_book
(
	id bigserial NOT NULL,
    vh_id integer NOT NULL,
    vh_owner_id integer NOT NULL,
    fuel_type_id integer NOT NULL,
    fuel_price float NOT NULL,
    liter_sale_volume float NOT NULL,
    amount_of_sale float NOT NULL,
    date_of_sale timestamp with time zone NOT NULL,
    is_deleted integer DEFAULT 0,
    created_dttm timestamp with time zone,
    modified_dttm timestamp with time zone,
    CONSTRAINT credit_book_pkey PRIMARY KEY (id),
    CONSTRAINT fuel_type_id_fkey FOREIGN KEY (fuel_type_id)
    REFERENCES public.fueltype (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID,
    CONSTRAINT vh_id_fkey FOREIGN KEY (vh_id)
    REFERENCES public.vehicle (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID,
    CONSTRAINT vh_owner_id_fkey FOREIGN KEY (vh_owner_id)
    REFERENCES public.vehicle_owner (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID
)

TABLESPACE pg_default;


ALTER TABLE public.credit_book
    OWNER to postgres add column is_paid boolean default 'no';


-- Trigger: set_created_dttm

-- DROP TRIGGER set_created_dttm ON public.credit_book;

CREATE TRIGGER set_created_dttm
    BEFORE INSERT
    ON public.credit_book
    FOR EACH ROW
    EXECUTE PROCEDURE public.set_created_dttm();

-- Trigger: set_modified_dttm

-- DROP TRIGGER set_modified_dttm ON public.credit_book;

CREATE TRIGGER set_modified_dttm
    BEFORE UPDATE 
    ON public.credit_book
    FOR EACH ROW
    EXECUTE PROCEDURE public.set_modified_dttm();
    
    
-- Table: public.credit_transaction

-- DROP TABLE public.credit_transaction;

CREATE TABLE public.credit_transaction
(
	id bigserial NOT NULL,
    credit_book_id bigint NULL,
    is_bill_payment boolean NULL,
    vh_owner_id integer NOT NULL,
    received_amt float NOT NULL,
    date_received timestamp with time zone NOT NULL,
    is_deleted integer DEFAULT 0,
    created_dttm timestamp with time zone,
    modified_dttm timestamp with time zone,
    CONSTRAINT credit_transaction_pkey PRIMARY KEY (id),
    CONSTRAINT credit_book_id_fkey FOREIGN KEY (credit_book_id),
    REFERENCES public.credit_book (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID,
    CONSTRAINT vh_owner_id_fkey FOREIGN KEY (vh_owner_id)
    REFERENCES public.vehicle_owner (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID
)

TABLESPACE pg_default;


-- Trigger: set_created_dttm

-- DROP TRIGGER set_created_dttm ON public.credit_book;

CREATE TRIGGER set_created_dttm
    BEFORE INSERT
    ON public.credit_transaction
    FOR EACH ROW
    EXECUTE PROCEDURE public.set_created_dttm();

-- Trigger: set_modified_dttm

-- DROP TRIGGER set_modified_dttm ON public.credit_book;

CREATE TRIGGER set_modified_dttm
    BEFORE UPDATE 
    ON public.credit_transaction
    FOR EACH ROW
    EXECUTE PROCEDURE public.set_modified_dttm();
    
