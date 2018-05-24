CREATE TRIGGER update_code_record AFTER INSERT ON Record
  BEGIN
    UPDATE Config SET value = value + 1 WHERE ID_config = 1;
    UPDATE Record SET code = (SELECT value FROM Config WHERE ID_config = 1) WHERE ID_record = NEW.ID_record;
  END;