package com.alfred.Sailfish.app.Util;

/**
 * Quick Reference快速引用
 * @author dengweixiong
 *
 */
public abstract class Reference {
	
	public static final String prefix = "{\"stat\":\"";
	public static final String id_prefix = "{\"id\":";
	public static final String dataprefix = "{\"data\":";
	public static final String suffix = "\"}";
	public static final String datasuffix = "}";
	public static final String NSR = prefix + "no_such_record" + suffix;
	public static final String EXE_FAIL =prefix +  "exe_fail" + suffix;
	public static final String EXE_PARTLY_FAIL =prefix +  "exe_partly_fail" + suffix;
	public static final String EXE_SUC = prefix + "exe_suc" + suffix;
	public static final String DUPLICATE = prefix + "duplicate" + suffix;
	public static final String INST_NOT_MATCH = prefix + "institution_not_match" + suffix;
	public static final String BALANCE_NOT_ENOUGH = prefix + "balance_not_enough" + suffix;
	public static final String NOT_MATCH = prefix + "not_match" + suffix;
	public static final String EMPTY_RESULT = prefix + "empty_result" + suffix;
	public static final String SESSION_EXPIRED=prefix + "session_expired" + suffix;
	
	public static final int TYPE_MEMBER_COURSE = 1;
	public static final int TYPE_TRAINNER_COURSE = 2;
	public static final int TYPE_COLLECTION_COURSE = 3;
	public static final int TYPE_PRIVATE_COURSE = 4;
	
	public static final int TYPE_OPERATE_ADD = 1;
	public static final int TYPE_OPERATE_REMOVE = 2;
	public static final int TYPE_OPERATE_MODIFY = 3;
	
	public static final int TYPE_BALANCE_CARD = 1;
	public static final int TYPE_TIMES_CARD = 2;
	public static final int TYPE_TIME_CARD = 3;
}
