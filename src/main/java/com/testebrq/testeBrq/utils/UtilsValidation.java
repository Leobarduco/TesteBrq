package com.testebrq.testeBrq.utils;

public class UtilsValidation {
    
	public static boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }
    
    public static boolean isValidPage(int page, int maxPages) {
        return page >= 1 && page <= maxPages;
    }

    public static void validarPagina(String page, int maxPages) {
        if (!isNumeric(page)) {
            throw new IllegalArgumentException("O valor da página deve ser um número.");
        }

        int pageNumber = Integer.parseInt(page);
        if (!isValidPage(pageNumber, maxPages)) {
            throw new IllegalArgumentException("O número da página deve estar entre 1 e " + maxPages);
        }
    }
}
