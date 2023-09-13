package com.idealize.services;

public class CalcudoraService {

    public static boolean isNumeric(String strNumber) {
        if (strNumber.equals(null)) return false;
        String num = strNumber.replaceAll(",", ".");
        return num.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

    public static Double convertToNumber(String strNumber) {
        if (strNumber.equals(null)) return 0D;
        String num = strNumber.replaceAll(",", ".");
        if (isNumeric(num)) return Double.parseDouble(num);
        return 0D;
    }

}