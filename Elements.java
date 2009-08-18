//Ali UGUR
//120020032

public class Elements {
	String name;
	int firstN, secondN, posCont, negCont, conN, thirdN;
	double value, vsat;
	String type;
	
	public Elements( String typer, String namer, int firster, int seconder, int thirder, int firCont, int secCont, int numel, double vsater, double valuer)
	{
		type = typer;
		name = namer;
		firstN = firster;
		secondN = seconder;
        thirdN = thirder;
		posCont = firCont;
		negCont = secCont;
		conN = numel;
        vsat = vsater;
		value = valuer;
	}
    
	public String toString()
	{
        return type + "\t" + name + "\t" + firstN + "\t" + secondN + "\t" + value;
	}
}