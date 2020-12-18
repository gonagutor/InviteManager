package com.gonagutor.invitemanager.Helpers;

import net.md_5.bungee.api.ChatColor;

public class Gradienter {
	/**
	 * 
	 * @param string   String que convertir a gradiente
	 * @param hex1     String que contiene un color hexadecimal en el formato
	 *                 #FFFFFF del inicio del gradiente
	 * @param hex2     String que contiene un color hexadecimal en el formato
	 *                 #FFFFFF del final del gradiente
	 * @param modifier String que contiene el modificador de texto (BOLD, ITALIC...)
	 *                 y lo aplica a string. Puede ser nulo si no se necesita
	 * @return String modificada con gradiente y modificador
	 */
	public static String gradientedString(String string, String hex1, String hex2, String modifier) {
		String ret = "";
		int red1 = ChatColor.of(hex1).getColor().getRed();
		int blue1 = ChatColor.of(hex1).getColor().getBlue();
		int green1 = ChatColor.of(hex1).getColor().getGreen();
		int red2 = ChatColor.of(hex2).getColor().getRed();
		int blue2 = ChatColor.of(hex2).getColor().getBlue();
		int green2 = ChatColor.of(hex2).getColor().getGreen();
		int nextred;
		int nextblue;
		int nextgreen;
		for (int i = 0; i < string.length(); i++) {
			nextred = red1 + ((red2 - red1) * i / string.length());
			nextblue = blue1 + ((blue2 - blue1) * i / string.length());
			nextgreen = green1 + ((green2 - green1) * i / string.length());
			ret += ChatColor.of(String.format("#%02X%02X%02X", nextred, nextgreen, nextblue)) + modifier
					+ String.format("%c", string.charAt(i));
		}
		return ret;
	}
}
