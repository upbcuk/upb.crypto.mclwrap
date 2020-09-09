package de.upb.crypto.math.pairings.mcl;

import com.herumi.mcl.Fr;
import com.herumi.mcl.GT;
import com.herumi.mcl.Mcl;
import de.upb.crypto.math.interfaces.structures.group.impl.GroupElementImpl;
import de.upb.crypto.math.serialization.Representation;
import de.upb.crypto.math.structures.zn.Zn;

import java.math.BigInteger;
import java.util.Objects;

public class MclGroupTElementImpl extends MclGroupElementImpl {

    public MclGroupTElementImpl(MclGroupTImpl group, Representation repr) {
        super(group, repr);
    }

    public MclGroupTElementImpl(MclGroupTImpl group, GT elem) {
        super(group, elem);
    }

    @Override
    protected GT getElement() {
        return (GT) super.getElement();
    }

    @Override
    public MclGroupTImpl getStructure() {
        return (MclGroupTImpl) super.getStructure();
    }

    @Override
    public MclGroupTElementImpl inv() {
        GT res = new GT();
        //Mcl.inv(res, getElement());
        //return getStructure().createElement(res);
        Fr minusOne = new Fr("-1");
        Mcl.pow(res, getElement(), minusOne);
        return getStructure().createElement(res);
        //return pow(getStructure().size().subtract(BigInteger.ONE)); //TODO change!
    }

    @Override
    public MclGroupTElementImpl op(GroupElementImpl e) throws IllegalArgumentException {
        GT res = new GT();
        Mcl.mul(res, getElement(), ((MclGroupTElementImpl) e).getElement());
        return getStructure().createElement(res);
    }

    @Override
    public MclGroupTElementImpl pow(BigInteger k) {
        return pow(Zn.valueOf(k, getStructure().size()));
    }

    public MclGroupTElementImpl pow(Zn.ZnElement k) {
        GT res = new GT();
        Fr exponent = new Fr();
        exponent.setStr(k.getInteger().toString());
        Mcl.pow(res, getElement(), exponent);
        return getStructure().createElement(res);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || this.getClass() != other.getClass()) return false;
        MclGroupTElementImpl that = (MclGroupTElementImpl) other;
        return getElement().equals(that.getElement()) // need to use this method since G1 does not override equals
                && Objects.equals(super.group, that.group);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}