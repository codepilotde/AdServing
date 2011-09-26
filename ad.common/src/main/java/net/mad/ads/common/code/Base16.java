/**
 * Mad-Advertisement
 * Copyright (C) 2011 Thorsten Marx <thmarx@gmx.net>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mad.ads.common.code;

import java.util.UUID;


/**
 * A collection of static utility methods to manipulate Base16-encoded data.
 *
 * <p>Base16 encoding uses two bytes with the ASCII values of 2 hexadecimal
 * digits to represent one byte of data.
 *
 * <p><dl><dt><b>Maturity:</b><dd>
 * Mature. Fixed API. Fully documented.
 * Methods for encoding and decoding binary data should be added.
 * </dl>
 *
 * @author Stefan Zeiger
 * @see com.novocode.tk.util.Base64
 */

public final class Base16
{
  /* Dummy constructor */
  private Base16() {}


  /**
   * Encodes a Latin-1 string. The upper 8 bits are discarded.
   *
   * @param dec the string to be encoded.
   * @return the Base16 code for the string.
   * @see #decode
   */

  public static String encode(String dec)
  {
    int l = dec.length();
    StringBuffer enc = new StringBuffer(l*2);

    for(int i=0; i<l; i++)
      enc.append(enc_codes[(byte)(dec.charAt(i))]);

    return enc.toString();
  }


  /**
   * Encodes a byte array.
   *
   * @param dec the data to be encoded.
   * @return the Base16 code for the data.
   * @see #decodeBytes
   */

  public static String encodeBytes(byte[] dec)
  {
    StringBuffer enc = new StringBuffer(dec.length*2);

    for(int i=0; i<dec.length; i++) enc.append(enc_codes[dec[i] & 0xFF]);

    return enc.toString();
  }


  /**
   * Decodes a Latin-1 string.
   *
   * @param enc the Base16 code of a Latin-1 string.
   * @return the decoded string.
   * @see #encode
   */

  public static String decode(String enc)
  {
    int l = enc.length();
    StringBuffer dec = new StringBuffer(l/2);

    for(int i=0; i<l; i+=2)
    {
      byte n, b;

      b = (byte)(enc.charAt(i));
      if((b>=(byte)('0')) && (b<=(byte)('9'))) n = (byte)(b-(byte)('0'));
      else n = (byte)(b-(byte)('A')+10);
      
      b = (byte)(enc.charAt(i+1));
      if((b>=(byte)('0')) && (b<=(byte)('9')))
	n = (byte)((n<<4) + b-(byte)('0'));
      else n = (byte)((n<<4) + b-(byte)('A')+10);

      dec.append((char)n);
    }

    return dec.toString();
  }


  /**
   * Decodes a byte array.
   *
   * @param enc the Base16 code for the data.
   * @return the decoded byte array.
   * @see #encodeBytes
   */

  public static byte[] decodeBytes(String enc)
  {
    int l = enc.length();
    int i=0, j=0;
    byte[] dec = new byte[l/2];

    while(i<l)
    {
      byte n, b;

      b = (byte)(enc.charAt(i));
      if((b>=(byte)('0')) && (b<=(byte)('9'))) n = (byte)(b-(byte)('0'));
      else n = (byte)(b-(byte)('A')+10);
      
      b = (byte)(enc.charAt(i+1));
      if((b>=(byte)('0')) && (b<=(byte)('9')))
	n = (byte)((n<<4) + b-(byte)('0'));
      else n = (byte)((n<<4) + b-(byte)('A')+10);

      dec[j] = n;

      i+=2;
      j++;
    }

    return dec;
  }


  private static final String[] enc_codes =
  {
    "00","01","02","03","04","05","06","07",
    "08","09","0A","0B","0C","0D","0E","0F",
    "10","11","12","13","14","15","16","17",
    "18","19","1A","1B","1C","1D","1E","1F",
    "20","21","22","23","24","25","26","27",
    "28","29","2A","2B","2C","2D","2E","2F",
    "30","31","32","33","34","35","36","37",
    "38","39","3A","3B","3C","3D","3E","3F",
    "40","41","42","43","44","45","46","47",
    "48","49","4A","4B","4C","4D","4E","4F",
    "50","51","52","53","54","55","56","57",
    "58","59","5A","5B","5C","5D","5E","5F",
    "60","61","62","63","64","65","66","67",
    "68","69","6A","6B","6C","6D","6E","6F",
    "70","71","72","73","74","75","76","77",
    "78","79","7A","7B","7C","7D","7E","7F",
    "80","81","82","83","84","85","86","87",
    "88","89","8A","8B","8C","8D","8E","8F",
    "90","91","92","93","94","95","96","97",
    "98","99","9A","9B","9C","9D","9E","9F",
    "A0","A1","A2","A3","A4","A5","A6","A7",
    "A8","A9","AA","AB","AC","AD","AE","AF",
    "B0","B1","B2","B3","B4","B5","B6","B7",
    "B8","B9","BA","BB","BC","BD","BE","BF",
    "C0","C1","C2","C3","C4","C5","C6","C7",
    "C8","C9","CA","CB","CC","CD","CE","CF",
    "D0","D1","D2","D3","D4","D5","D6","D7",
    "D8","D9","DA","DB","DC","DD","DE","DF",
    "E0","E1","E2","E3","E4","E5","E6","E7",
    "E8","E9","EA","EB","EC","ED","EE","EF",
    "F0","F1","F2","F3","F4","F5","F6","F7",
    "F8","F9","FA","FB","FC","FD","FE","FF"
  };
  
  public static void main (String... args) {
	  String test = "oiutoiut87-6597fgo87f8";
	  
	  test = Base16.encode(test);
	  System.out.println(test);
	  test = Base16.decode(test);
	  System.out.println(test);
  }
}
