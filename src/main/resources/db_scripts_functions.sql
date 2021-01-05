
-- FUNCTION SET_MODIFIED_DTTM

CREATE FUNCTION set_modified_dttm() RETURNS trigger AS $$
BEGIN
  NEW.modified_dttm := CURRENT_TIME;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;


-- FUNCTION SET_CREATED_DTTM

CREATE FUNCTION set_created_date() RETURNS trigger AS $$
BEGIN
  NEW.createddate := CURRENT_TIME;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;