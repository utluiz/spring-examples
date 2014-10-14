package br.com.starcode.specification.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Data {

	public static String imprimeData(Date time) {
		return new SimpleDateFormat("dd/MM/yyyy").format(time);
	}

}
