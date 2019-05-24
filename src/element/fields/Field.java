package element.fields;

import exception.ParseException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 *  Field interface
 * @param <T> Basic java type
 */
public interface Field<T> extends Serializable {
    // Serialized field
    void serialize(DataOutputStream dos) throws IOException;
    // Deserialized field
    T parse(byte[] bytes) throws ParseException;

    FieldType getType();

    T getValue();

    void setValue(T value);
}
