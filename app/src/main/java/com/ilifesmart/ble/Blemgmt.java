package com.ilifesmart.ble;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Blemgmt {

	private static final int TL1CMD_SETVAR=11;

	private static HashMap<String,Integer> BlemgmtIdxMap = new HashMap<>();
	static {
		BlemgmtIdxMap.put("WIFI", 0x88);
	}

	private static int GetAndIncreaseTL1CTag() {
		int result = cTag.getAndIncrement();
		if (result >= 0xf) {
			cTag.set(1);
		}

		return result;
	}

	private static AtomicInteger cTag = new AtomicInteger(1);
	public static void sendTL1Cmd_QueryWiFi(String address, String cmd) {
		String[] address_array = new String[] {address};

		SendTL1CmdSync(address, "_SETVAR", "WIFI", null, str2bytes(cmd));
	}

	public static void SendTL1CmdSync(String address, String cmd, String idx, String type, byte[] bytes) {
		if (cmd.equals("_SETVAR")) {

		}
	}

	public static Tl1Def EncodeTL1Command(String cmd, String idx, Integer type, Object val) {
		if (cmd.equals("_SETVAR")) {
			int ctag = GetAndIncreaseTL1CTag();
			String dst=null, src=null;
			if (idx.equals("WIFI")) {
				dst = src = "\0\0\0\1";
			}
//			return EpCmd.cmd_setvar(dst, src, ctag, idx, val)
		}
		return null;

	}

	private static byte[] str2bytes(String s) {
		return s.getBytes();
	}

	private class Tl1DefFrame {

		public byte[] toTL1Val_ScanWiFi() {
			return str2bytes("scan:");
		}

		public byte[] toTL1Val_SetWiFi(String cmd) {
			return str2bytes(cmd);
		}

		public byte[] toTL1Val_Query(String cmd) {
			return str2bytes(cmd);
		}

		public boolean fromTL1Val_SetWiFiDecoder(String tl1CmdStr, String cmd, String idx) {
			return tl1CmdStr.substring(0, 15).equals("done");
		}

		public void fromTL1Val_QueryDecoder(String tl1CmdStr, String cmd, String idx) {

		}
	}

	public static class Tl1Def {

		private static final String CONST_ZEROADDR = "\0\0\0\0";
		private String dstId;
		private String srcId;
		private int secu = 0;
		private int verb = 11;
		private int ctag = 0;
		private int len = 0;
		private List<Byte> dummy;
		private String _dummy = null;

		public Tl1Def set(String dst, String src, int vert, int ctag, List<Byte> dummy) {
			Tl1Def def = new Tl1Def();
			dst = (dst == null) ? CONST_ZEROADDR : dst;
			src = (src == null) ? CONST_ZEROADDR : src;
			def.dstId = dst;
			def.srcId = src;
			def.secu  = 0;
			def.verb  = vert;
			def.ctag  = ctag;
			def.len   =  dummy.size();
			def.dummy = dummy;
			def._dummy = null;

			return def;
		}

		public String ztab2str(List<Byte> dummy) {
			byte[] dst = new byte[dummy.size()];
			for (int i = 0; i < dummy.size(); i++) {
				dst[i] = dummy.get(i);
			}
			return new String(dst);
		}

		public String to_blestr() {
			this._dummy = ztab2str(this.dummy);
			String result =  new StringBuilder()
							.append(dstId)
							.append(srcId)
							.append((char) (secu))
							.append((char) (verb))
							.append((char) (ctag))
							.append((char) (len))
							.append(_dummy)
							.toString();

			Log.d("Blemgmt", "to_blestr: result " + result);
			return result;
		}

	}
}
