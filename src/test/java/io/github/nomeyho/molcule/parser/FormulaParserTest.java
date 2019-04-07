package io.github.nomeyho.molcule.parser;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class FormulaParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parse1() throws Exception {
        final FormulaParser parser = new FormulaParser("CH2O");
        final Map<Element, Integer> atoms = parser.parse();

        Assert.assertEquals(1, (int) atoms.get(Element.C));
        Assert.assertEquals(2, (int) atoms.get(Element.H));
        Assert.assertEquals(1, (int) atoms.get(Element.O));

        Assert.assertEquals(3, atoms.size());
    }

    @Test
    public void parse2() throws Exception {
        final FormulaParser parser = new FormulaParser("CH(CH3)3");
        final Map<Element, Integer> atoms = parser.parse();

        Assert.assertEquals(4, (int) atoms.get(Element.C));
        Assert.assertEquals(10, (int) atoms.get(Element.H));

        Assert.assertEquals(2, atoms.size());
    }

    @Test
    public void parse3() throws Exception {
        final FormulaParser parser = new FormulaParser("CH(CH3)2OH");
        final Map<Element, Integer> atoms = parser.parse();

        Assert.assertEquals(8, (int) atoms.get(Element.H));
        Assert.assertEquals(1, (int) atoms.get(Element.O));
        Assert.assertEquals(3, (int) atoms.get(Element.C));

        Assert.assertEquals(3, atoms.size());
    }

    @Test
    public void parse4() throws Exception {
        final FormulaParser parser = new FormulaParser("(NH4)2SO4");
        final Map<Element, Integer> atoms = parser.parse();

        Assert.assertEquals(4, (int) atoms.get(Element.O));
        Assert.assertEquals(1, (int) atoms.get(Element.S));
        Assert.assertEquals(8, (int) atoms.get(Element.H));
        Assert.assertEquals(2, (int) atoms.get(Element.N));

        Assert.assertEquals(4, atoms.size());
    }

    @Test
    public void parse5() throws Exception {
        final FormulaParser parser = new FormulaParser("C6H2(NO2)3(CH3)3");
        final Map<Element, Integer> atoms = parser.parse();

        Assert.assertEquals(6, (int) atoms.get(Element.O));
        Assert.assertEquals(11, (int) atoms.get(Element.H));
        Assert.assertEquals(9, (int) atoms.get(Element.C));
        Assert.assertEquals(3, (int) atoms.get(Element.N));

        Assert.assertEquals(4, atoms.size());
    }

    @Test
    public void parse6() throws Exception {
        final FormulaParser parser = new FormulaParser("(CH3)16(K(H2O)3CO(BrFe3(ReCl)3(SO4)2)2)2MnO4");
        final Map<Element, Integer> atoms = parser.parse();

        Assert.assertEquals(44, (int) atoms.get(Element.O));
        Assert.assertEquals(1, (int) atoms.get(Element.Mn));
        Assert.assertEquals(8, (int) atoms.get(Element.S));
        Assert.assertEquals(12, (int) atoms.get(Element.Cl));
        Assert.assertEquals(12, (int) atoms.get(Element.Re));
        Assert.assertEquals(12, (int) atoms.get(Element.Fe));
        Assert.assertEquals(4, (int) atoms.get(Element.Br));
        Assert.assertEquals(18, (int) atoms.get(Element.C));
        Assert.assertEquals(60, (int) atoms.get(Element.H));
        Assert.assertEquals(2, (int) atoms.get(Element.K));

        Assert.assertEquals(10, atoms.size());
    }

    @Test
    public void parseTwice() throws Exception {
        final FormulaParser parser = new FormulaParser("H2O");

        final Map<Element, Integer> atoms1 = parser.parse();
        Assert.assertEquals(2, (int) atoms1.get(Element.H));
        Assert.assertEquals(1, (int) atoms1.get(Element.O));
        Assert.assertEquals(2, atoms1.size());

        final Map<Element, Integer> atoms2 = parser.parse();
        Assert.assertEquals(2, (int) atoms2.get(Element.H));
        Assert.assertEquals(1, (int) atoms2.get(Element.O));
        Assert.assertEquals(2, atoms2.size());
    }

    @Test
    public void parseInvalidElement() throws Exception {
        final FormulaParser parser = new FormulaParser("a2");

        thrown.expect(FormulaParsingException.class);
        thrown.expectMessage("Invalid element: 'a'");

        parser.parse();
    }

    @Test
    public void parseInvalidCharacter() throws Exception {
        final FormulaParser parser = new FormulaParser("H20#");

        thrown.expect(FormulaParsingException.class);
        thrown.expectMessage("Invalid element: '#'");

        parser.parse();
    }

    @Test
    public void parseNullFormula() throws Exception {
        final FormulaParser parser = new FormulaParser("");

        thrown.expect(FormulaParsingException.class);
        thrown.expectMessage("Empty formula");

        parser.parse();
    }

    @Test
    public void parseEmptyFormula() throws Exception {
        final FormulaParser parser = new FormulaParser("");

        thrown.expect(FormulaParsingException.class);
        thrown.expectMessage("Empty formula");

        parser.parse();
    }

    @Test
    public void parseMisplacedNumber() throws Exception {
        final FormulaParser parser = new FormulaParser("2H");

        thrown.expect(FormulaParsingException.class);
        thrown.expectMessage("A number should be preceded by an atom or a group");

        parser.parse();
    }

    @Test
    public void parseEmptyGroup() throws Exception {
        final FormulaParser parser = new FormulaParser("()3");

        thrown.expect(FormulaParsingException.class);
        thrown.expectMessage("Empty group");

        parser.parse();
    }

    @Test
    public void parseMismatchRightParenthesis() throws Exception {
        final FormulaParser parser = new FormulaParser("NH4Cl)");

        thrown.expect(FormulaParsingException.class);
        thrown.expectMessage("Mismatched right parenthesis");

        parser.parse();
    }

    @Test
    public void parseMismatchLeftParenthesis() throws Exception {
        final FormulaParser parser = new FormulaParser("NH4(Cl");

        thrown.expect(FormulaParsingException.class);
        thrown.expectMessage("Mismatched left parenthesis");

        parser.parse();
    }
}