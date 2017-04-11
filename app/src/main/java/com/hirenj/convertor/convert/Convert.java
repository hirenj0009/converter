package com.hirenj.convertor.convert;

import com.hirenj.convertor.Task.FetchCurrency;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Hiren J on 3/5/2017.
 */

public class Convert {


    private ArrayList<String> resultArray;
    private ArrayList<String> resultUnitArray;
    DecimalFormat numberFormat;


    public ArrayList<String> getResultArray() {
        return resultArray;
    }

    public void setResultArray(Double value, String from, String type) {

        ArrayList<String> resultArray = new ArrayList<>();
        ArrayList<String> resultUnitArray = new ArrayList<>();

        if (type.equals("Temperature")) {
            resultArray = tempConvert(value, from);
        } else {
            Map<String, Double> valueMap = getValueMap(type);

            if (valueMap != null && valueMap.size() > 1) {

                Set<String> keys = valueMap.keySet();
                for (String key : keys) {
                    double result;
                    double fromValue = value * valueMap.get(key);

                    result = fromValue / valueMap.get(from);
                    resultArray.add(getNumberFormat().format(result) + "");
                    resultUnitArray.add(key);
                }


            }
            this.setResultUnitArray(resultUnitArray);
        }
        this.resultArray = resultArray;
    }

    public ArrayList<String> getResultUnitArray() {
        return resultUnitArray;
    }

    public void setResultUnitArray(ArrayList<String> resultUnitArray) {
        this.resultUnitArray = resultUnitArray;
    }

    // temp method to add hard coded values, get this from internet to get real time values.
    private Map<String, Double> getValueMap(String type) {

        Map<String, Double> valueMap = new HashMap<>();

        if (type.equals("Currency")) {
            FetchCurrency fetchTask = new FetchCurrency();
            valueMap = fetchTask.doFetch("http://api.fixer.io/latest?base=USD");
        } else if (false) { //digital converter check here
            valueMap.put("KB", 1.00);
            valueMap.put("MB", 1024.00);
            valueMap.put("GB", 1024.0 * 1024.0);
            valueMap.put("TB", 1024.0 * 1024.0 * 1024.00);
            valueMap.put("PB", 1024.0 * 1024.0 * 1024.0 * 1024.0);
        } else if (type.equals("Length")) {
            valueMap.put("meter [m]",1.0E+31);
            valueMap.put("kilometer [km]",1.0E+28);
            valueMap.put("decimeter [dm]",1.0E+32);
            valueMap.put("centimeter [cm]",1.0E+33);
            valueMap.put("millimeter [mm]",1.0E+34);
            valueMap.put("micrometer [μm]",1.0E+37);
            valueMap.put("nanometer [nm]",1.0E+40);
            valueMap.put("mile [mi, mi(Int)]",6.2137119223733E+27);
            valueMap.put("yard [yd]",1.0936132983377E+31);
            valueMap.put("foot [ft]",3.2808398950131E+31);
            valueMap.put("inch [in]",3.9370078740157E+32);
            valueMap.put("light year [ly]",1.0570008340247E+15);
            valueMap.put("exameter [Em]",10000000000000.0);
            valueMap.put("petameter [Pm]",1.0E+16);
            valueMap.put("terameter [Tm]",1.0E+19);
            valueMap.put("gigameter [Gm]",1.0E+22);
            valueMap.put("megameter [Mm]",1.0E+25);
            valueMap.put("hectometer [hm]",1.0E+29);
            valueMap.put("dekameter [dam]",1.0E+30);
            valueMap.put("micron [&#181;]",1.0E+37);
            valueMap.put("picometer [pm]",1.0E+43);
            valueMap.put("femtometer [fm]",1.0E+46);
            valueMap.put("attometer [am]",1.0E+49);
            valueMap.put("megaparsec [Mpc]",324077928.96664);
            valueMap.put("kiloparsec [kpc]",324077928966.64);
            valueMap.put("parsec [pc]",3.2407792896664E+14);
            valueMap.put("astronomical unit [AU, UA]",6.6845871226706E+19);
            valueMap.put("league [lea]",2.0712373074578E+27);
            valueMap.put("nautical league (UK)",1.7987060827923E+27);
            valueMap.put("nautical league (int.)",1.7998560115191E+27);
            valueMap.put("league (statute) [st.league]",2.0712331649832E+27);
            valueMap.put("nautical mile (UK) [NM (UK)]",5.3961182483768E+27);
            valueMap.put("nautical mile (international)",5.3995680345572E+27);
            valueMap.put("mile (statute) [mi, mi (US)]",6.2136994949495E+27);
            valueMap.put("mile (US survey) [mi]",6.2136994949495E+27);
            valueMap.put("mile (Roman)",6.7576516890075E+27);
            valueMap.put("kiloyard [kyd]",1.0936132983377E+28);
            valueMap.put("furlong [fur]",4.9709695378987E+28);
            valueMap.put("furlong (US survey) [fur]",4.9709595959596E+28);
            valueMap.put("chain [ch]",4.9709695378987E+29);
            valueMap.put("chain (US survey) [ch]",4.9709595959596E+29);
            valueMap.put("rope",1.6404199475066E+30);
            valueMap.put("rod [rd]",1.9883878151595E+30);
            valueMap.put("rod (US survey) [rd]",1.9883838383838E+30);
            valueMap.put("perch",1.9883878151595E+30);
            valueMap.put("pole",1.9883878151595E+30);
            valueMap.put("fathom [fath]",5.4680664916885E+30);
            valueMap.put("fathom (US survey) [fath]",5.4680555555556E+30);
            valueMap.put("ell",8.7489063867017E+30);
            valueMap.put("foot (US survey) [ft]",3.2808333333333E+31);
            valueMap.put("link [li]",4.9709695378987E+31);
            valueMap.put("link (US survey) [li]",4.9709595959596E+31);
            valueMap.put("cubit (UK)",2.1872265966754E+31);
            valueMap.put("hand",9.8425196850394E+31);
            valueMap.put("span (cloth)",4.3744531933508E+31);
            valueMap.put("finger (cloth)",8.7489063867017E+31);
            valueMap.put("nail (cloth)",1.7497812773403E+32);
            valueMap.put("inch (US survey) [in]",3.937E+32);
            valueMap.put("barleycorn",1.1811023622047E+33);
            valueMap.put("mil [mil, thou]",3.9370078740158E+35);
            valueMap.put("microinch",3.9370078740157E+38);
            valueMap.put("angstrom [A]",1.0E+41);
            valueMap.put("a.u. of length [a.u., b]",1.8897259885789E+41);
            valueMap.put("X-unit [X]",9.9792431741977E+43);
            valueMap.put("fermi [F, f]",1.0E+46);
            valueMap.put("arpent",1.7087707786527E+29);
            valueMap.put("pica",2.3622047244095E+33);
            valueMap.put("point",2.8346456692913E+34);
            valueMap.put("twip",5.6692913385827E+35);
            valueMap.put("aln",1.684131736527E+31);
            valueMap.put("famn",5.6137724550898E+30);
            valueMap.put("caliber [cl]",3.9370078740158E+34);
            valueMap.put("centiinch [cin]",3.9370078740158E+34);
            valueMap.put("ken",4.7206329424649E+30);
            valueMap.put("Russian archin",1.4060742407199E+31);
            valueMap.put("Roman actus",2.8185909750972E+29);
            valueMap.put("vara de tarea",3.9912894099916E+30);
            valueMap.put("vara conuquera",3.9912894099916E+30);
            valueMap.put("vara castellana",1.1973868229975E+31);
            valueMap.put("cubit (Greek)",2.1608166158155E+31);
            valueMap.put("long reed",3.124609423822E+30);
            valueMap.put("reed",3.6453776611257E+30);
            valueMap.put("long cubit",1.8747656542932E+31);
            valueMap.put("handbreadth",1.3123359580052E+32);
            valueMap.put("fingerbreadth",5.249343832021E+32);
            valueMap.put("Planck length",6.1879273537329E+65);
            valueMap.put("Electron radius (classical)",3.5486904388329E+45);
            valueMap.put("Bohr radius [b, a.u.]",1.8897259885789E+41);
            valueMap.put("Earth's equatorial radius",1.5678502891116E+24);
            valueMap.put("Earth's polar radius",1.5731242420491E+24);
            valueMap.put("Earth's distance from sun",6.6844919786096E+19);
            valueMap.put("Sun's radius",1.4367816091954E+22);
        } else if (type.equals("Area")) {
            valueMap.put("square meter [m^2]",1.0E+15);
            valueMap.put("square kilometer [km^2]",1000000000.0);
            valueMap.put("square centimeter [cm^2]",1.0E+19);
            valueMap.put("square millimeter [mm^2]",1.0E+21);
            valueMap.put("square micrometer [μm^2]",1.0E+27);
            valueMap.put("hectare [ha]",100000000000.0);
            valueMap.put("acre [ac]",247105381467.17);
            valueMap.put("square mile [mi^2]",386102158.54245);
            valueMap.put("square yard [yd^2]",1.1959900463011E+15);
            valueMap.put("square foot [ft^2]",1.076391041671E+16);
            valueMap.put("square inch [in^2]",1.5500031000062E+18);
            valueMap.put("square hectometer [hm^2]",100000000000.0);
            valueMap.put("square dekameter [dam^2]",10000000000000.0);
            valueMap.put("square decimeter [dm^2]",1.0E+17);
            valueMap.put("square nanometer [nm^2]",1.0E+33);
            valueMap.put("are [a]",10000000000000.0);
            valueMap.put("barn [b]",1.0E+43);
            valueMap.put("square mile (US survey)",386100614.13536);
            valueMap.put("square foot (US survey)",1.0763867361111E+16);
            valueMap.put("circular inch",1.97352524139E+18);
            valueMap.put("township",10725059.959512);
            valueMap.put("section",386102158.54245);
            valueMap.put("acre (US survey) [ac]",247104393046.63);
            valueMap.put("rood",988421525868.66);
            valueMap.put("square chain [ch^2]",2471053814671.6);
            valueMap.put("square rod",39536861034746.0);
            valueMap.put("square rod (US survey)",39536702887460.0);
            valueMap.put("square perch",39536861034746.0);
            valueMap.put("square pole",39536861034746.0);
            valueMap.put("square mil [mil^2]",1.5500031000062E+24);
            valueMap.put("circular mil",1.97352524139E+24);
            valueMap.put("homestead",1544408634.1698);
            valueMap.put("sabin",1.076391041671E+16);
            valueMap.put("arpent",247446216476.09);
            valueMap.put("cuerda",254427313535.39);
            valueMap.put("plaza",156250000000.0);
            valueMap.put("varas castellanas cuad",1.4311536386366E+15);
            valueMap.put("varas conuqueras cuad",1.5901707095962E+14);
            valueMap.put("Electron cross section",1.5032029647492E+43);
        } else if (type.equals("Volume")) {
            valueMap.put("cubic meter [m^3]",10000000000.0);
            valueMap.put("cubic kilometer [km^3]",10.0);
            valueMap.put("cubic centimeter [cm^3]",1.0E+16);
            valueMap.put("cubic millimeter [mm^3]",1.0E+19);
            valueMap.put("liter [L, l]",10000000000000.0);
            valueMap.put("milliliter [mL]",1.0E+16);
            valueMap.put("gallon (US) [gal (US)]",2641720523581.5);
            valueMap.put("quart (US) [qt (US)]",10566882094326.0);
            valueMap.put("pint (US) [pt (US)]",21133764188652.0);
            valueMap.put("cup (US)",42267528377304.0);
            valueMap.put("tablespoon (US)",6.7628045403686E+14);
            valueMap.put("teaspoon (US)",2.0288413621106E+15);
            valueMap.put("cubic mile [mi^3]",2.3991275857893);
            valueMap.put("cubic yard [yd^3]",13079506193.144);
            valueMap.put("cubic foot [ft^3]",353146667214.89);
            valueMap.put("cubic inch [in^3]",6.1023744094732E+14);
            valueMap.put("cubic decimeter [dm^3]",10000000000000.0);
            valueMap.put("exaliter [EL]",1.0E-5);
            valueMap.put("petaliter [PL]",0.01);
            valueMap.put("teraliter [TL]",10.0);
            valueMap.put("gigaliter [GL]",10000.0);
            valueMap.put("megaliter [ML]",10000000.0);
            valueMap.put("kiloliter [kL]",10000000000.0);
            valueMap.put("hectoliter [hL]",100000000000.0);
            valueMap.put("dekaliter [daL]",1000000000000.0);
            valueMap.put("deciliter [dL]",1.0E+14);
            valueMap.put("centiliter [cL]",1.0E+15);
            valueMap.put("microliter [μL]",1.0E+19);
            valueMap.put("nanoliter [nL]",1.0E+22);
            valueMap.put("picoliter [pL]",1.0E+25);
            valueMap.put("femtoliter [fL]",1.0E+28);
            valueMap.put("attoliter [aL]",1.0E+31);
            valueMap.put("cc [cc, cm^3]",1.0E+16);
            valueMap.put("drop",2.0E+17);
            valueMap.put("barrel (oil) [bbl (oil)]",62898107704.321);
            valueMap.put("barrel (US) [bbl (US)]",83864143605.761);
            valueMap.put("barrel (UK) [bbl (UK)]",61102568971.969);
            valueMap.put("gallon (UK) [gal (UK)]",2199692482990.9);
            valueMap.put("quart (UK) [qt (UK)]",8798769931963.5);
            valueMap.put("pint (UK) [pt (UK)]",17597539863927.0);
            valueMap.put("cup (metric)",40000000000000.0);
            valueMap.put("cup (UK)",35195079727854.0);
            valueMap.put("fluid ounce (US) [fl oz (US)]",3.3814022701843E+14);
            valueMap.put("fluid ounce (UK) [fl oz (UK)]",3.5195079727854E+14);
            valueMap.put("tablespoon (metric)",6.6666666666667E+14);
            valueMap.put("tablespoon (UK)",5.6312127564566E+14);
            valueMap.put("dessertspoon (US)",1.0144206810553E+15);
            valueMap.put("dessertspoon (UK)",8.446819134685E+14);
            valueMap.put("teaspoon (metric)",2.0E+15);
            valueMap.put("teaspoon (UK)",1.689363826937E+15);
            valueMap.put("gill (US) [gi]",84535056754608.0);
            valueMap.put("gill (UK) [gi (UK)]",70390159455708.0);
            valueMap.put("minim (US)",1.6230730896885E+17);
            valueMap.put("minim (UK)",1.689363826937E+17);
            valueMap.put("ton register [ton reg]",3531466672.1489);
            valueMap.put("ccf",3531466672.1489);
            valueMap.put("hundred-cubic foot",3531466672.1489);
            valueMap.put("acre-foot [ac*ft]",8107131.9378991);
            valueMap.put("acre-foot (US survey)",8107083.2952048);
            valueMap.put("acre-inch [ac*in]",97285583.254789);
            valueMap.put("dekastere",1000000000.0);
            valueMap.put("stere [st]",10000000000.0);
            valueMap.put("decistere",100000000000.0);
            valueMap.put("cord [cd]",2758958337.6163);
            valueMap.put("tun",10483017950.72);
            valueMap.put("hogshead",41932071802.881);
            valueMap.put("board foot",4237760006578.6);
            valueMap.put("dram [dr]",2.7051218161474E+15);
            valueMap.put("cor (Biblical)",45454545454.546);
            valueMap.put("homer (Biblical)",45454545454.546);
            valueMap.put("bath (Biblical)",454545454545.46);
            valueMap.put("hin (Biblical)",2727272727272.7);
            valueMap.put("cab (Biblical)",8181818181818.2);
            valueMap.put("log (Biblical)",32727272727273.0);
            valueMap.put("Taza (Spanish)",42267528377304.0);
            valueMap.put("Earth's volume",9.2336103416436E-12);
        } else if (type.equals("Weight")) {
            valueMap.put("kilogram [kg]",1.0E+15);
            valueMap.put("gram [g]",1.0E+18);
            valueMap.put("milligram [mg]",1.0E+21);
            valueMap.put("ton (metric) [t]",1000000000000.0);
            valueMap.put("pound [lbs]",2.2046226218488E+15);
            valueMap.put("ounce [oz]",3.527396194958E+16);
            valueMap.put("carat [car, ct]",5.0E+18);
            valueMap.put("ton (short) [ton (US)]",1102311310924.4);
            valueMap.put("ton (long) [ton (UK)]",984206527611.06);
            valueMap.put("Atomic mass unit [u]",6.0221366516752E+41);
            valueMap.put("exagram [Eg]",1.0);
            valueMap.put("petagram [Pg]",1000.0);
            valueMap.put("teragram [Tg]",1000000.0);
            valueMap.put("gigagram [Gg]",1000000000.0);
            valueMap.put("megagram [Mg]",1000000000000.0);
            valueMap.put("hectogram [hg]",1.0E+16);
            valueMap.put("dekagram [dag]",1.0E+17);
            valueMap.put("decigram [dg]",1.0E+19);
            valueMap.put("centigram [cg]",1.0E+20);
            valueMap.put("microgram [μg]",1.0E+24);
            valueMap.put("nanogram [ng]",1.0E+27);
            valueMap.put("picogram [pg]",1.0E+30);
            valueMap.put("femtogram [fg]",1.0E+33);
            valueMap.put("attogram [ag]",1.0E+36);
            valueMap.put("dalton",6.02217364335E+41);
            valueMap.put("kilogram-force square second/meter",1.0197162129779E+14);
            valueMap.put("kilopound [kip]",2204622621848.8);
            valueMap.put("kip",2204622621848.8);
            valueMap.put("slug",68521765856800.0);
            valueMap.put("pound-force square second/foot",68521765856792.0);
            valueMap.put("pound (troy or apothecary)",2.679228880719E+15);
            valueMap.put("poundal [pdl]",7.09888484236E+16);
            valueMap.put("ton (assay) (US) [AT (US)]",3.4285710367347E+16);
            valueMap.put("ton (assay) (UK) [AT (UK)]",3.061224489796E+16);
            valueMap.put("kiloton (metric) [kt]",1000000000.0);
            valueMap.put("quintal (metric) [cwt]",10000000000000.0);
            valueMap.put("hundredweight (US)",22046226218488.0);
            valueMap.put("hundredweight (UK)",19684130552221.0);
            valueMap.put("quarter (US) [qr (US)]",88184904873951.0);
            valueMap.put("quarter (UK) [qr (UK)]",78736522208885.0);
            valueMap.put("stone (US)",1.763698097479E+14);
            valueMap.put("stone (UK)",1.5747304441777E+14);
            valueMap.put("tonne [t]",1000000000000.0);
            valueMap.put("pennyweight [pwt]",6.4301493137083E+17);
            valueMap.put("scruple (apothecary) [s.ap]",7.71617917645E+17);
            valueMap.put("grain [gr]",1.54323583529E+19);
            valueMap.put("gamma",1.0E+24);
            valueMap.put("talent (Biblical Hebrew)",29239766081871.0);
            valueMap.put("mina (Biblical Hebrew)",1.7543859649123E+15);
            valueMap.put("shekel (Biblical Hebrew)",8.7719298245614E+16);
            valueMap.put("bekan (Biblical Hebrew)",1.7543859649123E+17);
            valueMap.put("gerah (Biblical Hebrew)",1.7543859649123E+18);
            valueMap.put("talent (Biblical Greek)",49019607843137.0);
            valueMap.put("mina (Biblical Greek)",2.9411764705882E+15);
            valueMap.put("tetradrachma (Biblical Greek)",7.3529411764706E+16);
            valueMap.put("didrachma (Biblical Greek)",1.4705882352941E+17);
            valueMap.put("drachma (Biblical Greek)",2.9411764705882E+17);
            valueMap.put("denarius (Biblical Roman)",2.5974025974026E+17);
            valueMap.put("assarion (Biblical Roman)",4.1558441558442E+18);
            valueMap.put("quadrans (Biblical Roman)",1.6623376623377E+19);
            valueMap.put("lepton (Biblical Roman)",3.3246753246753E+19);
            valueMap.put("Planck mass",4.5940892447777E+22);
            valueMap.put("Electron mass (rest)",1.0977683828808E+45);
            valueMap.put("Muon mass",5.3091724927313E+42);
            valueMap.put("Proton mass",5.9786332019449E+41);
            valueMap.put("Neutron mass",5.9704037533301E+41);
            valueMap.put("Deuteron mass",2.9908008946084E+41);
            valueMap.put("Earth's mass",1.673360107095E-10);
            valueMap.put("Sun's mass",5.0E-16);
        } else if (type.equals("Time")) {
            valueMap.put("second [s]",3.1536E+18);
            valueMap.put("millisecond [ms]",3.1536E+21);
            valueMap.put("minute [min]",5.256E+16);
            valueMap.put("hour [h]",8.76E+14);
            valueMap.put("day [d]",365000000000000.);
            valueMap.put("week",5214285714285.7);
            valueMap.put("month",1200000000000.0);
            valueMap.put("year [y]",100000000000.0);
            valueMap.put("decade",10000000000.0);
            valueMap.put("century",1000000000.0);
            valueMap.put("millennium",100000000.0);
            valueMap.put("microsecond [μs]",3.1536E+24);
            valueMap.put("nanosecond [ns]",3.1536E+27);
            valueMap.put("picosecond [ps]",3.1536E+30);
            valueMap.put("femtosecond [fs]",3.1536E+33);
            valueMap.put("attosecond [as]",3.1536E+36);
            valueMap.put("shake",3.1536E+26);
            valueMap.put("month (synodic)",1236006041191.2);
            valueMap.put("year (Julian)",99931553730.322);
            valueMap.put("year (leap)",99726775956.284);
            valueMap.put("year (tropical)",99933675424.067);
            valueMap.put("year (sidereal)",99929813565.361);
            valueMap.put("day (sidereal)",36599933916786.0);
            valueMap.put("hour (sidereal)",8.7839841400286E+14);
            valueMap.put("minute (sidereal)",5.2703904840172E+16);
            valueMap.put("second (sidereal)",3.1622342904103E+18);
            valueMap.put("fortnight",2607142857142.9);
            valueMap.put("septennial",14285714285.714);
            valueMap.put("octennial",12500000000.0);
            valueMap.put("novennial",11111111111.111);
            valueMap.put("quindecennial",6666666666.6667);
            valueMap.put("quinquennial",20000000000.0);
            valueMap.put("Planck time",5.8502270636075E+61);
        } else if (type.equals("Speed")) {
            valueMap.put("meter/second [m/s]",2777777777777.8);
            valueMap.put("kilometer/hour [km/h]",10000000000000.0);
            valueMap.put("mile/hour [mi/h]",6213711922373.3);
            valueMap.put("meter/hour [m/h]",1.0E+16);
            valueMap.put("meter/minute [m/min]",1.6666666666667E+14);
            valueMap.put("kilometer/minute [km/min]",166666666666.67);
            valueMap.put("kilometer/second [km/s]",2777777777.7778);
            valueMap.put("centimeter/hour [cm/h]",1.0E+18);
            valueMap.put("centimeter/minute [cm/min]",1.6666666666667E+16);
            valueMap.put("centimeter/second [cm/s]",2.7777777777778E+14);
            valueMap.put("millimeter/hour [mm/h]",1.0E+19);
            valueMap.put("millimeter/minute [mm/min]",1.6666666666667E+17);
            valueMap.put("millimeter/second [mm/s]",2.7777777777778E+15);
            valueMap.put("foot/hour [ft/h]",3.2808398950131E+16);
            valueMap.put("foot/minute [ft/min]",5.4680664916885E+14);
            valueMap.put("foot/second [ft/s]",9113444152814.2);
            valueMap.put("yard/hour [yd/h]",1.0936132983377E+16);
            valueMap.put("yard/minute [yd/min]",1.8226888305628E+14);
            valueMap.put("yard/second [yd/s]",3037814717604.7);
            valueMap.put("mile/minute [mi/min]",103561865372.89);
            valueMap.put("mile/second [mi/s]",1726031089.5482);
            valueMap.put("knot [kt, kn]",5399568034557.2);
            valueMap.put("knot (UK) [kt (UK)]",5396118248376.8);
            valueMap.put("Velocity of light in vacuum",9265.6693110598);
            valueMap.put("Cosmic velocity - first",351617440.22504);
            valueMap.put("Cosmic velocity - second",248015873.01587);
            valueMap.put("Cosmic velocity - third",166633339.99867);
            valueMap.put("Earth's velocity",93323627.676055);
            valueMap.put("Velocity of sound in pure water",1873459079.907);
            valueMap.put("Velocity of sound in sea water (20°C, 10 meter deep)",1825563734.0811);
            valueMap.put("Mach (20°C, 1 atm)",8084335790.9714);
            valueMap.put("Mach (SI standard)",9414715033.89);
        }else if (type.equals("Pressure")) {
            valueMap.put("pascal [Pa]",1.0E+18);
            valueMap.put("kilopascal [kPa]",1.0E+15);
            valueMap.put("bar",10000000000000.0);
            valueMap.put("psi [psi]",1.4503773773E+14);
            valueMap.put("ksi [ksi]",145037737730.0);
            valueMap.put("Standard atmosphere [atm]",9869232667160.1);
            valueMap.put("exapascal [EPa]",1.0);
            valueMap.put("petapascal [PPa]",1000.0);
            valueMap.put("terapascal [TPa]",1000000.0);
            valueMap.put("gigapascal [GPa]",1000000000.0);
            valueMap.put("megapascal [MPa]",1000000000000.0);
            valueMap.put("hectopascal [hPa]",1.0E+16);
            valueMap.put("dekapascal [daPa]",1.0E+17);
            valueMap.put("decipascal [dPa]",1.0E+19);
            valueMap.put("centipascal [cPa]",1.0E+20);
            valueMap.put("millipascal [mPa]",1.0E+21);
            valueMap.put("micropascal [&#181;Pa]",1.0E+24);
            valueMap.put("nanopascal [nPa]",1.0E+27);
            valueMap.put("picopascal [pPa]",1.0E+30);
            valueMap.put("femtopascal [fPa]",1.0E+33);
            valueMap.put("attopascal [aPa]",1.0E+36);
            valueMap.put("newton/square meter",1.0E+18);
            valueMap.put("newton/square centimeter",1.0E+14);
            valueMap.put("newton/square millimeter",1000000000000.0);
            valueMap.put("kilonewton/square meter",1.0E+15);
            valueMap.put("millibar [mbar]",1.0E+16);
            valueMap.put("microbar [&#181;bar]",1.0E+19);
            valueMap.put("dyne/square centimeter",1.0E+19);
            valueMap.put("kilogram-force/square meter",1.0197162129779E+17);
            valueMap.put("kilogram-force/sq. cm",10197162129779.0);
            valueMap.put("kilogram-force/sq. millimeter",101971621297.79);
            valueMap.put("gram-force/sq. centimeter",1.0197162129779E+16);
            valueMap.put("ton-force (short)/sq. foot",10442717116574.0);
            valueMap.put("ton-force (short)/sq. inch",72518868865.1);
            valueMap.put("ton-force (long)/square foot",9323854568370.6);
            valueMap.put("ton-force (long)/square inch",64748990058.129);
            valueMap.put("kip-force/square inch",145037737730.0);
            valueMap.put("pound-force/square foot",2.088543423312E+16);
            valueMap.put("pound-force/square inch",1.4503773773E+14);
            valueMap.put("poundal/square foot",6.7196897513951E+17);
            valueMap.put("torr [Torr]",7.50061682704E+15);
            valueMap.put("centimeter mercury (0°C)",7.5006375541921E+14);
            valueMap.put("millimeter mercury (0°C)",7.5006375541921E+15);
            valueMap.put("inch mercury (32°F) [inHg]",2.9530058646696E+14);
            valueMap.put("inch mercury (60°F) [inHg]",2.9613397100848E+14);
            valueMap.put("centimeter water (4°C)",1.0197442889221E+16);
            valueMap.put("millimeter water (4°C)",1.0197442889221E+17);
            valueMap.put("inch water (4°C) [inAq]",4.0147421331128E+15);
            valueMap.put("foot water (4°C) [ftAq]",3.3456229215318E+14);
            valueMap.put("inch water (60°F) [inAq]",4.0185980718766E+15);
            valueMap.put("foot water (60°F) [ftAq]",3.3488317265639E+14);
            valueMap.put("atmosphere technical [at]",10197162129779.0);
        }
        return valueMap;
    }


    private ArrayList<String> tempConvert(double value, String from) {

        ArrayList<String> resultArray = new ArrayList<>();

        ArrayList<String> resultUnitArray = new ArrayList<>();
        if ("Celsius".equals(from)) {
            resultArray.add(((value * 9) / 5) + 32 + "");
            resultUnitArray.add("Fahrenheit");
        } else if ("Fahrenheit".equals(from)) {
            resultArray.add(((value - 32) * 5) / 9 + "");
            resultUnitArray.add("Celsius");
        }
        this.setResultUnitArray(resultUnitArray);
        return resultArray;
    }


    public DecimalFormat getNumberFormat() {
        if (numberFormat == null) {
            return new DecimalFormat(".0000");
        }
        return numberFormat;
    }

    public void setNumberFormat(int numberAfterDot) {
        String format = ".";
        if (numberAfterDot == 0) {
            format = " ";
        }
        for (int i = 0; i < numberAfterDot; i++) {
            format = format + "0";
        }
        this.numberFormat = new DecimalFormat(format);
    }
}
