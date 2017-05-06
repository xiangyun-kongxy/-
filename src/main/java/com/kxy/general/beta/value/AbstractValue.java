package com.kxy.general.beta.value;

import com.kxy.general.beta.relation.Relation;
import com.kxy.general.beta.value.direct.Level;
import com.kxy.general.beta.value.exception.NoSuchAddress;
import com.kxy.general.beta.value.exception.NoSuchOperate;
import com.kxy.general.beta.value.exception.ParameterCountError;
import com.kxy.general.beta.value.exception.ParameterTypeMissMatch;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xiangyunkong on 14/04/2017.
 */
public abstract class AbstractValue implements Value {
    private static final long serialVersionUID = 991253607836803448L;

    /**
     * get the value of a mapping value (map object to the real value). if this
     * is a direct value, the result is itself.
     * @param object for mapping value, it is the original value
     * @return the real value
     * @throws ParameterTypeMissMatch the parameter object doesn't match the
     *          required type
     * @throws NoSuchAddress this object doesn't have an address which this
     *          value hold
     */
    @Override
    public Value get(final Object object) throws
            ParameterTypeMissMatch, NoSuchAddress {
        return this;
    }

    /**
     * compare values: relation(this, ...value). if this value doesn't
     * support this relation, return Level(0)
     * @param relation the relation to compare
     * @param value the value to compare
     * @return how match the values in the relation relation
     * @throws NoSuchOperate this value doesn't support this relation
     * @throws ParameterCountError parameter count of value doesn't match the
     *          relation
     * @throws ParameterTypeMissMatch the type of value doesn't match the
     *          relation
     */
    @Override
    public Level operate(final Relation relation, final Value... value)
            throws NoSuchOperate, ParameterCountError, ParameterTypeMissMatch {
        return operate(relation, Arrays.asList(value));
    }

    /**
     * compare values: relation(this, ...value). if this value doesn't
     * support this relation, return Level(0)
     * @param relation the relation to compare
     * @param values the values to compare
     * @return how match the values in the relation relation
     * @throws NoSuchOperate this value doesn't support this relation
     * @throws ParameterCountError parameter count of value doesn't match the
     * relation
     * @throws ParameterTypeMissMatch the type of value doesn't match the
     * relation
     */
    @Override
    public Level operate(final Relation relation, final List<Value> values)
            throws NoSuchOperate, ParameterCountError, ParameterTypeMissMatch {
        throw new NoSuchOperate();
    }

    /**
     * to check the relation whether it is the specified type.
     * @param relation the relation to check
     * @param type the desired type
     * @return if it matches
     */
    protected Boolean matchOperator(final Relation relation, final Class type) {
        return type.isInstance(relation);
    }

    /**
     * to check params's count and type.
     * @param params parameter to check
     * @param count desired parameter count
     * @param type desired parameter type
     * @throws ParameterTypeMissMatch parameter type dis-matched
     * @throws ParameterCountError parameter count dis-matched
     */
    protected void checkParameter(final List<Value> params, final int count,
                                  final Class type)
        throws ParameterTypeMissMatch, ParameterCountError {
        if (params == null || params.size() != count) {
            throw new ParameterCountError();
        }
        for (Value param:params) {
            if (!type.isInstance(param)) {
                throw new ParameterTypeMissMatch();
            }
        }
    }
}
