# Molecular formula parser
Simple molecular formula parser for Java

## Install
```
<dependency>
  <groupId>io.github.nomeyho</groupId>
  <artifactId>molecular-formula-parser</artifactId>
  <version>1.0.3</version>
</dependency>
```

## Usage
```
FormulaParser parser = new FormulaParser("H20");
Map<Element, Integer> atoms = parser.parse();

atoms.get(Element.H); // 2
atoms.get(Element.0); // 1
```
