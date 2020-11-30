package de.upb.crypto.math.pairings.mcl;

import com.herumi.mcl.Fp;
import com.herumi.mcl.G2;
import com.herumi.mcl.MclConstants;
import de.upb.crypto.math.interfaces.structures.group.impl.GroupElementImpl;
import de.upb.crypto.math.random.interfaces.RandomGeneratorSupplier;
import de.upb.crypto.math.serialization.Representation;

public class MclGroup2Impl extends MclGroupImpl {
    protected MclGroup2ElementImpl generator = null;

    public MclGroup2Impl(int curveType) {
        super(curveType);
    }

    public MclGroup2Impl(Representation repr) {
        super(repr);
    }

    @Override
    public MclGroup2ElementImpl getElement(String string) {
        return createElement(getInternalObjectFromString(string));
    }

    @Override
    public GroupElementImpl getElement(Representation repr) {
        return new MclGroup2ElementImpl(this, repr);
    }

    @Override
    protected G2 getInternalObjectFromString(String str) {
        G2 result = new G2();
        result.setStr(str);
        return result;
    }

    protected MclGroup2ElementImpl createElement(G2 G2) {
        return new MclGroup2ElementImpl(this, G2);
    }

    @Override
    public MclGroup2ElementImpl getNeutralElement() {
        return getElement("0");
    }

    @Override
    public MclGroup2ElementImpl getUniformlyRandomElement() throws UnsupportedOperationException {
        return getGenerator().pow(RandomGeneratorSupplier.getRnd().getRandomElement(size()));
    }

    @Override
    public MclGroup2ElementImpl getGenerator() throws UnsupportedOperationException {
        if (generator != null && generatorCurveType.equals(curveType))
            return generator;

        G2 res;
        if (curveType.equals(MclConstants.BN254)) {
            Fp xa = new Fp("12723517038133731887338407189719511622662176727675373276651903807414909099441");
            Fp xb = new Fp("4168783608814932154536427934509895782246573715297911553964171371032945126671");
            Fp ya = new Fp("13891744915211034074451795021214165905772212241412891944830863846330766296736");
            Fp yb = new Fp("7937318970632701341203597196594272556916396164729705624521405069090520231616");

            res = new G2(xa, xb, ya, yb);
        } else {
            res = getInternalObjectFromString("1 " +
                    "352701069587466618187139116011060144890029952792775240219" +
                    "908644239793785735715026873347600343865175952761926303160 " +
                    "305914434424421370997125981475378163698647032547664755865" +
                    "9373206291635324768958432433509563104347017837885763365758 " +
                    "1985150602287291935568054521177171638300868978215655730859" +
                    "378665066344726373823718423869104263333984641494340347905 " +
                    "927553665492332455747201965776037880757740193453592970025" +
                    "027978793976877002675564980949289727957565575433344219582");
        }
        generatorCurveType = curveType;

        return generator = createElement(res);
    }

    @Override
    public double estimateCostInvPerOp() {
        return 2.4;
    }
}
