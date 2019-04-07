package io.github.nomeyho.molcule.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Element {

    H(1.007825, 0.9999),
    He(4.002603, 1.0000),
    Li(7.016005, 0.9258),
    Be(9.012183, 1.0000),
    B(11.009305, 0.8020),
    C(12.000000, 0.9890),
    N(14.003074, 0.9963),
    O(15.994915, 0.9976),
    F(18.998403, 1.0000),
    Ne(19.992439, 0.9060),
    Na(22.989770, 1.0000),
    Mg(23.985045, 0.7890),
    Al(26.981541, 1.0000),
    Si(27.976928, 0.9223),
    P(30.973763, 1.0000),
    S(31.972072, 0.9502),
    Cl(34.968853, 0.7577),
    Ar(39.962383, 0.9960),
    K(38.963708, 0.9320),
    Ca(39.962591, 0.9695),
    Sc(44.955914, 1.0000),
    Ti(47.947947, 0.7380),
    V(50.943963, 0.9975),
    Cr(51.940510, 0.8379),
    Mn(54.938046, 1.0000),
    Fe(55.934939, 0.9172),
    Co(58.933198, 1.0000),
    Ni(57.935347, 0.6827),
    Cu(62.929599, 0.6917),
    Zn(63.929145, 0.4860),
    Ga(68.925581, 0.6010),
    Ge(73.921179, 0.3650),
    As(74.921596, 1.0000),
    Se(79.916521, 0.4960),
    Br(78.918336, 0.5069),
    Kr(83.911506, 0.5700),
    Rb(84.911800, 0.7217),
    Sr(87.905625, 0.8258),
    Y(88.905856, 1.0000),
    Zr(89.904708, 0.5145),
    Nb(92.906378, 1.0000),
    Mo(97.905405, 0.2413),
    Ru(101.90434, 0.3160),
    Rh(102.905503, 1.0000),
    Pd(105.903475, 0.2733),
    Ag(106.905095, 0.5184),
    Cd(113.903361, 0.2873),
    In(114.903875, 0.9570),
    Sn(119.902199, 0.3240),
    Sb(120.903824, 0.5730),
    Te(129.906229, 0.3380),
    I(126.904477, 1.0000),
    Xe(131.904148, 0.2690),
    Cs(132.905433, 1.0000),
    Ba(137.905236, 0.7170),
    La(138.906355, 0.9991),
    Ce(139.905442, 0.8848),
    Pr(140.907657, 1.0000),
    Nd(141.907731, 0.2713),
    Sm(151.919741, 0.2570),
    Eu(152.921243, 0.5220),
    Gd(157.924111, 0.2484),
    Tb(158.925350, 1.0000),
    Dy(163.929183, 0.2820),
    Ho(164.930332, 1.0000),
    Er(165.930305, 0.3360),
    Tm(168.934225, 1.0000),
    Yb(173.938873, 0.3180),
    Lu(174.940785, 0.9740),
    Hf(179.946561, 0.3520),
    Ta(180.948014, 0.9999),
    W(183.950953, 0.3067),
    Re(186.955765, 0.6260),
    Os(191.961487, 0.4100),
    Ir(192.962942, 0.6270),
    Pt(194.964785, 0.3380),
    Au(196.966560, 1.0000),
    Hg(201.970632, 0.2965),
    Tl(204.974410, 0.7048),
    Pb(207.976641, 0.5240),
    Bi(208.980388, 1.0000),
    Th(232.038054, 1.0000),
    U(238.050786, 0.9927);

    private static final Map<String, Element> LOOKUP = new HashMap<>();
    static {
        for(Element e: Element.values()) {
            LOOKUP.put(e.name(), e);
        }
    }

    private final double weight;
    private final double abundance;
    
    Element(double weight, double abundance) {
        this.weight = weight;
        this.abundance = abundance;
    }

    public double getWeight() {
        return weight;
    }

    public double getAbundance() {
        return abundance;
    }

    /*
     * This method has two advantages over 'valueOf':
     * - returns an Optional instead of throwing an exception for unknown values
     * - more efficient thanks to the lookup table (avoid iterating over all values)
     */
    public static Optional<Element> from(final String s) {
        return Optional.ofNullable(LOOKUP.get(s));
    }
}
