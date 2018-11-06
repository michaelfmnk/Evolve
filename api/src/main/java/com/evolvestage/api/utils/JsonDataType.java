package com.evolvestage.api.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.SerializationException;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JsonDataType implements UserType {

    private final Gson gson = new GsonBuilder().serializeNulls().create();

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor se)
            throws HibernateException, SQLException {
        if (Objects.isNull(value)) {
            st.setNull(index, Types.OTHER);
        } else {
            st.setObject(index, gson.toJson(value, Map.class), Types.OTHER);
        }
    }

    @Override
    public Object deepCopy(Object originalValue) throws HibernateException {
        if (originalValue == null) {
            return null;
        }

        if (!(originalValue instanceof Map)) {
            return null;
        }

        Map resultMap = new HashMap<>();

        Map<?, ?> tempMap = (Map<?, ?>) originalValue;
        tempMap.forEach((key, value) -> resultMap.put(key, value));

        return resultMap;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor se, Object value)
            throws HibernateException, SQLException {
        PGobject o = (PGobject) rs.getObject(names[0]);
        if (Objects.nonNull(o) && Objects.nonNull(o.getValue())) {
            return gson.fromJson(o.getValue(), Map.class);
        }

        return new HashMap();
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        Object copy = deepCopy(value);

        if (copy instanceof Serializable) {
            return (Serializable) copy;
        }

        throw new SerializationException(String.format("Cannot serialize '%s', %s is not Serializable.", value, value.getClass()), null);
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return Objects.isNull(x) ? 0 : x.hashCode();
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return ObjectUtils.nullSafeEquals(x, y);
    }

    @Override
    public Class<?> returnedClass() {
        return Map.class;
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }

}
