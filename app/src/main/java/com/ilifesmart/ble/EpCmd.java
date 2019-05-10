package com.ilifesmart.ble;

import java.util.ArrayList;
import java.util.List;

public class EpCmd {

	public static final String TAG = "EpCmd";
	private static int TL1CMD_HEART=1;
	private static int TL1CMD_RFCFG=2;
	private static int TL1CMD_GET=3;
	private static int TL1CMD_SET=4;
	private static int TL1CMD_AGTINIT=5;
	private static int TL1CMD_GETRSSI=6;
	private static int TL1CMD_SETEEPROM=7;
	private static int TL1CMD_EPINIT=8;
	private static int TL1CMD_EPINFO=9;
	private static int TL1CMD_AGTOPT=10;
	private static int TL1CMD_SETVAR=11;
	private static int TL1CMD_SETGRP=12;
	private static int TL1CMD_SETRELAY=13;
	private static int TL1CMD_RELAYMSG=14;
	private static int TL1CMD_SETOPT=15;
	private static int TL1CMD_SECMSG=0x20;

	private static int IOTYPE_BAD=0x1e;
	private static int IOTYPE_MASK_DIR =0x80;  //bit 7
	private static int IOTYPE_MASK_B =0x7e;  //bit 1-6
	private static int IOTYPE_TYPE_B =0x00;  //bit 6
	private static int IOTYPE_MASK_A =0x40;  //bit 6
	private static int IOTYPE_TYPE_A =0x40;  //bit 6
	private static int IOTYPE_MASK_ACC =0x3e;  //bit 1-5
	private static int IOTYPE_MASK_Fxx =0x78;  //bit 1-6
	private static int IOTYPE_TYPE_Fxx =0x8;  //bit 1-6
	private static int IOTYPE_MASK_FLOAT32 =0x7e;  //bit 1-6
	private static int IOTYPE_TYPE_FLOAT32 =0x2; //bit 1-6

	private static int EPIO_TYPE_IB=0x00;
	private static int EPIO_TYPE_IA=0x40;
	private static int EPIO_TYPE_OB=0x80;
	private static int EPIO_TYPE_OA=0xC0;

	public static Blemgmt.Tl1Def cmd_setvar(String dst, String src, byte ctag, byte idx, byte[] bytes) {
		List<Byte> dummy = new ArrayList<>();

		dummy.add(idx);
		for (byte ver:bytes) {
			dummy.add(ver);
		}

		return new Blemgmt.Tl1Def().set(dst, src, TL1CMD_SETVAR, ctag, dummy);
	}
}
