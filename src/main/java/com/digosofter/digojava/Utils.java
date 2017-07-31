package com.digosofter.digojava;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

public abstract class Utils
{
  public enum EnmDataFormato
  {
    DD,
    DD_MM,
    DD_MM_YY,
    DD_MM_YYYY,
    DD_MM_YYYY_HH_MM,
    DD_MM_YYYY_HH_MM_SS,
    HH_MM,
    HH_MM_DD_MM_YY,
    HH_MM_DD_MM_YYYY,
    HH_MM_SS_DD_MM_YYYY,
    MM,
    YYYY,
    YYYY_MM_DD,
    YYYY_MM_DD_HH_MM_SS,
    YYYY_MM_DD_HH_T_MM_SS_Z,
  }

  public enum EnmStringTipo
  {
    ALPHA,
    ALPHANUMERICO,
    NUMERICO
  }

  public static final String STR_VAZIA = "";
  private static Locale _objLocaleBrasil;
  private static String _strDownload;

  public static String addMascara(String str, String strMascara)
  {
    if (Utils.getBooStrVazia(str))
    {
      return null;
    }

    if (Utils.getBooStrVazia(strMascara))
    {
      return null;
    }

    String strResultado = Utils.STR_VAZIA;

    for (char chr : strMascara.toCharArray())
    {
      if ((chr != '*') || (str.length() < 1))
      {
        strResultado += chr;
        continue;
      }

      strResultado += str.charAt(0);

      str = str.substring(1);
    }

    return strResultado;
  }

  public static String addMascaraCep(String strCep)
  {
    if (Utils.getBooStrVazia(strCep))
    {
      return null;
    }

    strCep = Utils.simplificar(strCep);

    strCep = Utils.getStrFixa(strCep, 10);

    return Utils.addMascara(strCep, "**.***-***");
  }

  public static String addMascaraCnpj(String strCnpj)
  {
    if (Utils.getBooStrVazia(strCnpj))
    {
      return null;
    }

    strCnpj = Utils.simplificar(strCnpj);

    strCnpj = Utils.getStrFixa(strCnpj, 14);

    return Utils.addMascara(strCnpj, "**.***.***/****-**");
  }

  public static String addMascaraCpf(String strCpf)
  {
    if (Utils.getBooStrVazia(strCpf))
    {
      return null;
    }

    strCpf = Utils.simplificar(strCpf);

    strCpf = Utils.getStrFixa(strCpf, 11);

    return Utils.addMascara(strCpf, "***.***.***-**");
  }

  public static double arredondar(double dblValor, int intQtdCasas)
  {
    dblValor = (dblValor * Math.pow(10, intQtdCasas));

    dblValor = Math.floor(dblValor);

    return (dblValor / Math.pow(10, intQtdCasas));
  }

  public static int charToInt(char c)
  {
    return Integer.parseInt(Character.toString(c));
  }

  public static String concatenarArrStr(String[] arrStrTermo, String strDelimitador, boolean booEliminarDuplicado)
  {
    if (arrStrTermo == null)
    {
      return null;
    }

    if (arrStrTermo.length < 1)
    {
      return null;
    }

    return Utils.concatenarLstStr(Arrays.asList(arrStrTermo), strDelimitador, booEliminarDuplicado);
  }

  public static String concatenarLstStr(List<String> lstStrTermo, String strDelimitador, boolean booEliminarDuplicado)
  {
    if (lstStrTermo == null)
    {
      return null;
    }

    if (lstStrTermo.isEmpty())
    {
      return null;
    }

    if (Utils.getBooStrVazia(strDelimitador))
    {
      strDelimitador = ";";
    }

    List<String> lstStrIncluido = new ArrayList<>();
    StringBuilder stbResultado = new StringBuilder();

    for (String strTermo : lstStrTermo)
    {
      if (Utils.getBooStrVazia(strTermo))
      {
        continue;
      }

      if (booEliminarDuplicado && lstStrIncluido.contains(strTermo))
      {
        continue;
      }

      stbResultado.append(strTermo.concat(strDelimitador));

      lstStrIncluido.add(strTermo);
    }

    return stbResultado.toString();
  }

  public static String downloadString(String url)
  {
    if (Utils.getBooStrVazia(url))
    {
      return null;
    }

    if (!url.startsWith("http"))
    {
      url = ("http://" + url);
    }

    setStrDownload(null);

    downloadStringProcess(url);

    while (getStrDownload() == null)
    {
      try
      {
        Thread.sleep(10);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }

    return getStrDownload();
  }

  public static boolean getBoo(final String strValor)
  {
    if (Utils.getBooStrVazia(strValor))
    {
      return false;
    }

    switch (strValor.toLowerCase())
    {
      case "1":
      case "s":
      case "sim":
      case "t":
      case "true":
        return true;

      default:
        return false;
    }
  }

  /**
   * @return Retorna true se o valor de "str" é um numeral.
   */
  public static boolean getBooNumeral(String str)
  {
    if (Utils.getBooStrVazia(str))
    {
      return false;
    }

    str = Utils.simplificar(str);

    return str.matches("[-+]?\\d*\\.?\\d+");
  }

  public static boolean getBooStrVazia(String str)
  {
    return ((str == null) || (str.isEmpty()));
  }

  /**
   * Verifica se a string é uma URL válida.
   */
  public static boolean getBooUrlValida(String url)
  {
    if (Utils.getBooStrVazia(url))
    {
      return false;
    }

    try
    {
      new URL(url);
    }
    catch (MalformedURLException ex)
    {
      ex.printStackTrace();

      return false;
    }

    return true;
  }

  public static int getIntNumeroAleatorio(int intMaximo)
  {
    return new Random().nextInt(intMaximo);
  }

  public static String getMd5(String str)
  {
    MessageDigest objMessageDigest = null;

    try
    {
      objMessageDigest = MessageDigest.getInstance("MD5");
    }
    catch (NoSuchAlgorithmException ex)
    {
      ex.printStackTrace();

      return null;
    }

    BigInteger objBigInteger = new BigInteger(1, objMessageDigest.digest(str.getBytes()));

    return String.format("%0" + (objMessageDigest.digest(str.getBytes()).length << 1) + "X", objBigInteger);
  }

  public static Locale getObjLocaleBrasil()
  {
    if (_objLocaleBrasil != null)
    {
      return _objLocaleBrasil;
    }

    _objLocaleBrasil = new Locale("pt", "BR");

    return _objLocaleBrasil;
  }

  public static String getStrAleatoria(int intTamanho, EnmStringTipo enmStringTipo)
  {
    String strConjunto = Utils.getStrConjunto(enmStringTipo);

    StringBuilder stbResultado = new StringBuilder();

    for (int i = 0; i < intTamanho; i++)
    {
      stbResultado.append(strConjunto.charAt(Utils.getIntNumeroAleatorio(strConjunto.length())));
    }

    return stbResultado.toString();
  }

  public static String getStrDataFormatada(Calendar dtt, EnmDataFormato enmDataFormato)
  {
    if (dtt == null)
    {
      return null;
    }

    String strDataFormato = Utils.enmDataFormatoToString(enmDataFormato);

    if (Utils.getBooStrVazia(strDataFormato))
    {
      return null;
    }

    SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat(strDataFormato, Utils.getObjLocaleBrasil());

    return objSimpleDateFormat.format(dtt.getTime());
  }

  public static String getStrFixa(int intTermo, int intQuantidade)
  {
    String strNumero = String.valueOf(intTermo);

    if (Utils.getBooStrVazia(strNumero))
    {
      strNumero = Utils.STR_VAZIA;
    }

    while (strNumero.length() < intQuantidade)
    {
      strNumero = ("0" + strNumero);
    }

    if (strNumero.length() > intQuantidade)
    {
      strNumero = strNumero.substring(0, intQuantidade);
    }

    return strNumero;
  }

  public static String getStrFixa(String strTermo, int intQuantidade)
  {
    if (Utils.getBooStrVazia(strTermo))
    {
      strTermo = Utils.STR_VAZIA;
    }

    while (strTermo.length() < intQuantidade)
    {
      strTermo = strTermo.concat(" ");
    }

    if (strTermo.length() > intQuantidade)
    {
      strTermo = strTermo.substring(0, intQuantidade);
    }

    return strTermo;
  }

  public static String getStrPrimeiraMaiuscula(String strTermo)
  {
    if (Utils.getBooStrVazia(strTermo))
    {
      return null;
    }

    if (strTermo.length() < 2)
    {
      return strTermo.toUpperCase();
    }

    return (strTermo.substring(0, 1).toUpperCase().concat(strTermo.substring(1)));
  }

  public static String getStrToken(List<String> lstStrTermo)
  {
    return Utils.getStrToken(lstStrTermo, 5);
  }

  public static String getStrToken(List<String> lstStrTermo, int intTamanho)
  {
    if (lstStrTermo == null)
    {
      return null;
    }

    if (lstStrTermo.isEmpty())
    {
      return null;
    }

    String strMd5;
    String strResultado = Utils.STR_VAZIA;

    for (String strTermo : lstStrTermo)
    {
      if (Utils.getBooStrVazia(strTermo))
      {
        continue;
      }

      strMd5 = Utils.getMd5(strTermo);

      strResultado = Utils.getMd5(strResultado.concat(strMd5));
    }

    return strResultado.substring(0, intTamanho);
  }

  /**
   * Retorna o valor em uma lista no padrão "key=valor,key2=valor2;...".
   *
   * @param strLista
   * @param strKey
   * @return
   */
  public static String getStrValor(String strLista, String strKey)
  {
    if (Utils.getBooStrVazia(strLista))
    {
      return null;
    }

    if (Utils.getBooStrVazia(strKey))
    {
      return null;
    }

    String[] arrStrTermo = strLista.split(";");

    if (arrStrTermo == null)
    {
      return null;
    }

    if (arrStrTermo.length < 1)
    {
      return null;
    }

    for (String strTermo : arrStrTermo)
    {
      if (Utils.getBooStrVazia(strTermo))
      {
        continue;
      }

      if (!strTermo.contains("="))
      {
        continue;
      }

      String strTermoKey = strTermo.split("=")[0];

      if (!strKey.equals(strTermoKey))
      {
        continue;
      }

      return strTermo.split("=")[1];
    }

    return null;
  }

  public static String getStrValorMonetario(double dblValor)
  {
    if (dblValor == 0)
    {
      return "R$ 0,00";
    }

    NumberFormat objNumberFormat = NumberFormat.getCurrencyInstance(Utils.getObjLocaleBrasil());

    String strResultado = objNumberFormat.format(dblValor);

    strResultado = strResultado.replace("R$", "R$ ");
    strResultado = strResultado.replace("  ", " ");

    return strResultado;
  }

  public static String getStrValorNumerico(double dblValor)
  {
    if (dblValor == 0)
    {
      return "0";
    }

    NumberFormat objNumberFormat = NumberFormat.getNumberInstance(Utils.getObjLocaleBrasil());

    String strResultado = objNumberFormat.format(dblValor);

    strResultado = (strResultado.endsWith(",0")) ? strResultado.replace(",0", Utils.STR_VAZIA) : strResultado;

    return strResultado;
  }

  public static String getStrValorPercentual(double dblValor)
  {
    if (dblValor == 0)
    {
      return "0,00 %";
    }

    NumberFormat objNumberFormat = NumberFormat.getNumberInstance(Utils.getObjLocaleBrasil());

    String strResultado = objNumberFormat.format(dblValor);

    strResultado = strResultado.concat(" %");
    strResultado = strResultado.replace(",0 %", " %");

    return strResultado;
  }

  /**
   * "Pinga" um host e retorna true caso haja resposta deste.
   */
  public static boolean ping(String url)
  {
    if (!Utils.getBooUrlValida(url))
    {
      return false;
    }

    try
    {
      HttpURLConnection objHttpURLConnection = (HttpURLConnection) new URL(url).openConnection();

      return (HttpURLConnection.HTTP_OK == objHttpURLConnection.getResponseCode());
    }
    catch (Exception ex)
    {
      ex.printStackTrace();

      return false;
    }
  }

  public static String removerMascara(String strTermo)
  {
    if (Utils.getBooStrVazia(strTermo))
    {
      return null;
    }

    return strTermo.replaceAll("[.-/()]", Utils.STR_VAZIA);
  }

  public static String removerUltimaLetra(String strTermo)
  {
    return Utils.removerUltimaLetra(strTermo, 1);
  }

  public static String removerUltimaLetra(String strTermo, int intQuantidade)
  {
    if (Utils.getBooStrVazia(strTermo))
    {
      return null;
    }

    if (strTermo.length() < intQuantidade)
    {
      return null;
    }

    return strTermo.substring(0, strTermo.length() - intQuantidade);
  }

  public static String simplificar(String strTermo)
  {
    if (Utils.getBooStrVazia(strTermo))
    {
      return null;
    }

    strTermo = strTermo.toLowerCase(Utils.getObjLocaleBrasil());

    char[] arrChrComAcento = new char[]{'ç', 'á', 'é', 'í', 'ó', 'ú', 'ý', 'à', 'è', 'ì', 'ò', 'ù', 'ã', 'õ', 'ñ', 'ä', 'ë', 'ï', 'ö', 'ü', 'ÿ', 'â', 'ê', 'î', 'ô', 'û'};

    char[] arrChrSemAcento = new char[]{'c', 'a', 'e', 'i', 'o', 'u', 'y', 'a', 'e', 'i', 'o', 'u', 'a', 'o', 'n', 'a', 'e', 'i', 'o', 'u', 'y', 'a', 'e', 'i', 'o', 'u'};

    for (int i = 0; i < arrChrComAcento.length; i++)
    {
      strTermo = strTermo.replace(arrChrComAcento[i], arrChrSemAcento[i]);
    }

    char[] arrChrCaracterEspecial = new char[]{'/', '.', ',', '-', ':', '(', ')', 'ª', '|', '\\', '°', '+', '$', '-', '.', '{', '}', '[', ']'};

    for (char chrCaracterEspecial : arrChrCaracterEspecial)
    {
      strTermo = strTermo.replace(chrCaracterEspecial, ' ');
    }

    return strTermo.replace(" ", "_");
  }

  public static GregorianCalendar strToDtt(String strData)
  {
    if (Utils.getBooStrVazia(strData))
    {
      return null;
    }

    strData = strData.toLowerCase();

    strData = strData.replace("/", "-");
    strData = strData.replace("t", " ");

    String strFormato = Utils.getStrDttFormato(strData);

    if (Utils.getBooStrVazia(strFormato))
    {
      return null;
    }

    GregorianCalendar dttResultado = new GregorianCalendar();

    SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat(strFormato);

    try
    {
      dttResultado.setTime(objSimpleDateFormat.parse(strData));
    }
    catch (ParseException ex)
    {
      ex.printStackTrace();

      return null;
    }

    return dttResultado;
  }

  public static double toDouble(String strTermo)
  {
    if (Utils.getBooStrVazia(strTermo))
    {
      return 0;
    }

    strTermo = strTermo.replace(",", ".");

    if (Utils.getBooStrVazia(strTermo))
    {
      return 0;
    }

    return Double.valueOf(strTermo);
  }

  public static boolean validarCnpj(String strCnpj)
  {
    if (Utils.getBooStrVazia(strCnpj))
    {
      return false;
    }

    strCnpj = strCnpj.replaceAll(Pattern.compile("\\s").toString(), "");
    strCnpj = strCnpj.replaceAll(Pattern.compile("\\D").toString(), "");

    if (strCnpj.length() != 14)
    {
      return false;
    }

    char[] arrChrCnpj = strCnpj.toCharArray();

    int intSoma = (Utils.charToInt(arrChrCnpj[0]) * 5);

    intSoma += (Utils.charToInt(arrChrCnpj[1]) * 4);
    intSoma += (Utils.charToInt(arrChrCnpj[2]) * 3);
    intSoma += (Utils.charToInt(arrChrCnpj[3]) * 2);
    intSoma += (Utils.charToInt(arrChrCnpj[4]) * 9);
    intSoma += (Utils.charToInt(arrChrCnpj[5]) * 8);
    intSoma += (Utils.charToInt(arrChrCnpj[6]) * 7);
    intSoma += (Utils.charToInt(arrChrCnpj[7]) * 6);
    intSoma += (Utils.charToInt(arrChrCnpj[8]) * 5);
    intSoma += (Utils.charToInt(arrChrCnpj[9]) * 4);
    intSoma += (Utils.charToInt(arrChrCnpj[10]) * 3);
    intSoma += (Utils.charToInt(arrChrCnpj[11]) * 2);

    int intD1 = intSoma % 11;

    intD1 = intD1 < 2 ? 0 : 11 - intD1;

    intSoma = 0;

    intSoma += (Utils.charToInt(arrChrCnpj[0]) * 6);
    intSoma += (Utils.charToInt(arrChrCnpj[1]) * 5);
    intSoma += (Utils.charToInt(arrChrCnpj[2]) * 4);
    intSoma += (Utils.charToInt(arrChrCnpj[3]) * 3);
    intSoma += (Utils.charToInt(arrChrCnpj[4]) * 2);
    intSoma += (Utils.charToInt(arrChrCnpj[5]) * 9);
    intSoma += (Utils.charToInt(arrChrCnpj[6]) * 8);
    intSoma += (Utils.charToInt(arrChrCnpj[7]) * 7);
    intSoma += (Utils.charToInt(arrChrCnpj[8]) * 6);
    intSoma += (Utils.charToInt(arrChrCnpj[9]) * 5);
    intSoma += (Utils.charToInt(arrChrCnpj[10]) * 4);
    intSoma += (Utils.charToInt(arrChrCnpj[11]) * 3);
    intSoma += (Utils.charToInt(arrChrCnpj[12]) * 2);

    int intD2 = (intSoma % 11);

    intD2 = (intD2 < 2 ? 0 : 11 - intD2);

    return (Utils.charToInt(arrChrCnpj[12]) == intD1) && (Utils.charToInt(arrChrCnpj[13]) == intD2);
  }

  public static boolean validarCpf(String strCpf)
  {
    if (Utils.getBooStrVazia(strCpf))
    {
      return false;
    }

    int intDigito2 = 0;
    int intDigito1 = 0;
    int intCpfDigito;
    int intD1 = 0;
    int intD2 = 0;

    for (int nCount = 1; nCount < strCpf.length() - 1; nCount++)
    {
      intCpfDigito = Integer.valueOf(strCpf.substring(nCount - 1, nCount)).intValue();
      intD1 = intD1 + (11 - nCount) * intCpfDigito;
      intD2 = intD2 + (12 - nCount) * intCpfDigito;
    }

    int intResto = (intD1 % 11);

    if (intResto < 2)
    {
      intDigito1 = 0;
    }
    else
    {
      intDigito1 = 11 - intResto;
    }

    intD2 += 2 * intDigito1;
    intResto = (intD2 % 11);

    if (intResto < 2)
    {
      intDigito2 = 0;
    }
    else
    {
      intDigito2 = 11 - intResto;
    }

    String strDigitoVerificador = strCpf.substring(strCpf.length() - 2, strCpf.length());
    String strDigitoResult = String.valueOf(intDigito1) + String.valueOf(intDigito2);

    return strDigitoVerificador.equals(strDigitoResult);
  }

  private static void downloadStringProcess(final String url)
  {
    new Thread()
    {
      @Override
      public void run()
      {
        InputStream objInputStream = null;

        try
        {
          objInputStream = new URL(url).openStream();

          setStrDownload(IOUtils.toString(objInputStream));
        }
        catch (Exception e)
        {
          e.printStackTrace();
          setStrDownload(STR_VAZIA);
        }
        finally
        {
          IOUtils.closeQuietly(objInputStream);
        }
      }
    }.start();
  }

  private static String enmDataFormatoToString(EnmDataFormato enmDataFormato)
  {
    switch (enmDataFormato)
    {
      case DD:
        return "dd";

      case DD_MM:
        return "dd/MM";

      case DD_MM_YYYY_HH_MM:
        return "dd/MM/yyyy HH:mm";

      case DD_MM_YYYY_HH_MM_SS:
        return "dd/MM/yyyy HH:mm:ss";

      case HH_MM:
        return "HH:mm";

      case HH_MM_DD_MM_YY:
        return "HH:mm dd/MM/yy";

      case HH_MM_DD_MM_YYYY:
        return "HH:mm dd/MM/yyyy";

      case HH_MM_SS_DD_MM_YYYY:
        return "HH:mm:ss dd/MM/yyyy";

      case YYYY:
        return "yyyy";

      case YYYY_MM_DD:
        return "yyyy-MM-dd";

      case YYYY_MM_DD_HH_MM_SS:
        return "yyyy-MM-dd HH:mm:ss";

      case YYYY_MM_DD_HH_T_MM_SS_Z:
        return "yyyy-MM-dd'T'HH:mm:ssZ";

      case MM:
        return "MM";

      default:
        return "dd/MM/yyyy";
    }
  }

  private static String getStrConjunto(final EnmStringTipo enmStringTipo)
  {
    switch (enmStringTipo)
    {
      case ALPHANUMERICO:
        return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

      case NUMERICO:
        return "1234567890";

      default:
        return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    }
  }

  private static String getStrDownload()
  {
    return _strDownload;
  }

  private static String getStrDttFormato(String strDtt)
  {
    if (Utils.getBooStrVazia(strDtt))
    {
      return null;
    }

    strDtt = strDtt.toLowerCase();

    strDtt = strDtt.replace("/", "-");
    strDtt = strDtt.replace("t", " ");

    if (strDtt.matches("\\d\\d-\\d\\d-\\d\\d\\d\\d \\d\\d:\\d\\d:\\d\\d.\\d\\d\\d"))
    {
      return "dd-MM-yyyy HH:mm:ss.SSS";
    }

    if (strDtt.matches("\\d\\d-\\d\\d-\\d\\d\\d\\d \\d\\d:\\d\\d:\\d\\d"))
    {
      return "dd-MM-yyyy HH:mm:ss";
    }

    if (strDtt.matches("\\d\\d-\\d\\d-\\d\\d\\d\\d \\d\\d:\\d\\d"))
    {
      return "dd-MM-yyyy HH:mm";
    }

    if (strDtt.matches("\\d\\d-\\d\\d-\\d\\d\\d\\d"))
    {
      return "dd-MM-yyyy";
    }

    if (strDtt.matches("\\d\\d:\\d\\d:\\d\\d.\\d\\d\\d \\d\\d-\\d\\d-\\d\\d\\d\\d"))
    {
      return "HH:mm:ss.SSS dd-MM-yyyy";
    }

    if (strDtt.matches("\\d\\d:\\d\\d:\\d\\d \\d\\d-\\d\\d-\\d\\d\\d\\d"))
    {
      return "HH:mm:ss dd-MM-yyyy";
    }
    if (strDtt.matches("\\d\\d:\\d\\d \\d\\d-\\d\\d-\\d\\d\\d\\d"))
    {
      return "HH:mm dd-MM-yyyy";
    }

    if (strDtt.matches("\\d\\d-\\d\\d-\\d\\d\\d\\d"))
    {
      return "dd-MM-yyyy";
    }

    if (strDtt.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d.\\d\\d\\d"))
    {
      return "yyyy-MM-dd HH:mm:ss.SSS";
    }

    if (strDtt.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d\\-\\d\\d:\\d\\d"))
    {
      return "yyyy-MM-dd HH:mm:ssZ";
    }

    if (strDtt.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d.\\d\\d\\d-\\d\\d:\\d\\d"))
    {
      return "yyyy-MM-dd HH:mm:ss.SSSZ";
    }

    if (strDtt.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d"))
    {
      return "yyyy-MM-dd HH:mm:ss";
    }

    if (strDtt.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d"))
    {
      return "yyyy-MM-dd HH:mm";
    }

    if (strDtt.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d"))
    {
      return "yyyy-MM-dd";
    }

    return null;
  }

  private static void setStrDownload(String strDownload)
  {
    _strDownload = strDownload;
  }
}