package com.teixeira.tdd.currency;

import java.text.Normalizer;
import java.util.*;

public class CurrencyResolver {

    private static final Set<String> SUPPORTED = new HashSet<>(Arrays.asList(
            "USD","EUR","BRL","GBP","JPY","CNY","AUD","CAD","CHF",
            "ARS","CLP","MXN","COP","INR","RUB","ZAR"
    ));

    private static final Set<String> AMBIGUOUS = new HashSet<>(Arrays.asList(
            "dolar","dollar","peso","franco","rupia","rupee"
    ));

    private static final Map<String,String> ALIASES = new HashMap<>();
    private static final Map<String,String> COUNTRY_DEFAULT = new HashMap<>();

    static {
        // Country defaults (PT/EN where helpful)
        putCountry("brasil","BRL"); putCountry("brazil","BRL");
        putCountry("estados unidos","USD"); putCountry("eua","USD"); putCountry("usa","USD"); putCountry("united states","USD");
        putCountry("argentina","ARS"); putCountry("chile","CLP"); putCountry("mexico","MXN"); putCountry("méxico","MXN");
        putCountry("canada","CAD"); putCountry("canadá","CAD"); putCountry("australia","AUD");
        putCountry("reino unido","GBP"); putCountry("reinounido","GBP"); putCountry("uk","GBP"); putCountry("united kingdom","GBP"); putCountry("inglaterra","GBP");
        putCountry("suiça","CHF"); putCountry("suica","CHF"); putCountry("switzerland","CHF");
        putCountry("china","CNY"); putCountry("india","INR"); putCountry("russia","RUB"); putCountry("rússia","RUB");
        putCountry("africa do sul","ZAR"); putCountry("áfrica do sul","ZAR"); putCountry("south africa","ZAR");
        putCountry("japao","JPY"); putCountry("japão","JPY"); putCountry("japan","JPY");
        putCountry("colombia","COP"); putCountry("colômbia","COP");

        // Direct aliases and names (PT/EN + symbols common)
        alias("usd","USD"); alias("us$","USD"); alias("dolar americano","USD"); alias("dólar americano","USD");
        alias("u.s. dollar","USD"); alias("us dollar","USD"); alias("dollar usa","USD"); alias("dolar dos eua","USD"); alias("dólar dos eua","USD");
        alias("dolar dos estados unidos","USD"); alias("dólar dos estados unidos","USD");

        alias("eur","EUR"); alias("euro","EUR");
        alias("euro da uniao europeia","EUR"); alias("euro da união europeia","EUR"); alias("euro zona do euro","EUR"); alias("zona do euro","EUR");

        alias("brl","BRL"); alias("real","BRL"); alias("real brasileiro","BRL"); alias("brazilian real","BRL");

        alias("gbp","GBP"); alias("libra esterlina","GBP"); alias("pound sterling","GBP"); alias("libra","GBP"); alias("pound","GBP");

        alias("jpy","JPY"); alias("iene","JPY"); alias("iene japones","JPY"); alias("iene japonês","JPY"); alias("yen","JPY"); alias("japanese yen","JPY");

        alias("cny","CNY"); alias("yuan","CNY"); alias("iuan","CNY"); alias("renminbi","CNY"); alias("yuan chines","CNY"); alias("yuan chinês","CNY"); alias("rmb","CNY");

        alias("aud","AUD"); alias("dolar australiano","AUD"); alias("dólar australiano","AUD"); alias("australian dollar","AUD");

        alias("cad","CAD"); alias("dolar canadense","CAD"); alias("dólar canadense","CAD"); alias("canadian dollar","CAD");

        alias("chf","CHF"); alias("franco suico","CHF"); alias("franco suíço","CHF"); alias("swiss franc","CHF");

        alias("ars","ARS"); alias("peso argentino","ARS");
        alias("clp","CLP"); alias("peso chileno","CLP");
        alias("mxn","MXN"); alias("peso mexicano","MXN");
        alias("cop","COP"); alias("peso colombiano","COP");

        alias("inr","INR"); alias("rupia indiana","INR"); alias("indian rupee","INR");

        alias("rub","RUB"); alias("rublo russo","RUB"); alias("russian ruble","RUB");

        alias("zar","ZAR"); alias("rand sul africano","ZAR"); alias("rand sul-africano","ZAR"); alias("south african rand","ZAR");
    }

    private static void alias(String key, String code) {
        ALIASES.put(norm(key), code);
    }
    private static void putCountry(String key, String code) {
        COUNTRY_DEFAULT.put(norm(key), code);
    }

    /** Resolve um texto livre (código, nome da moeda ou país) para um código ISO 4217 suportado. */
    public String resolve(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new CurrencyNotFoundException("currency text is empty");
        }
        String n = norm(text);

        // 3-letter code directly
        if (n.matches("[a-z]{3}")) {
            String code = n.toUpperCase();
            if (SUPPORTED.contains(code)) return code;
        }

        // Direct aliases
        if (ALIASES.containsKey(n)) {
            return ALIASES.get(n);
        }

        // Country defaults
        if (COUNTRY_DEFAULT.containsKey(n)) {
            return COUNTRY_DEFAULT.get(n);
        }

        // Handle generic ambiguous words
        if (AMBIGUOUS.contains(n)) {
            throw new AmbiguousCurrencyException("currency name is ambiguous: " + text);
        }

        throw new CurrencyNotFoundException("unknown currency or country: " + text);
    }

    private static String norm(String s) {
        String noAccents = Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\p{M}+", "");
        String lowered = noAccents.toLowerCase(Locale.ROOT);
        // keep letters, numbers and spaces only
        String cleaned = lowered.replaceAll("[^a-z0-9 ]", " ");
        // collapse spaces
        return cleaned.trim().replaceAll("\s+", " ");
    }

    /** Lista de códigos suportados (principalmente moedas globais). */
    public Set<String> supportedCodes() {
        return Collections.unmodifiableSet(SUPPORTED);
    }
}
