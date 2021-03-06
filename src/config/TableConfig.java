package config;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import utils.CompareUtil;
import utils.DateUtil;
import utils.TypeUtil;

/**
 * 表结构初始化
 */
public class TableConfig {
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String LENGTH = "length";
    public static final String INT = "int";
    public static final String LONG = "long";
    public static final String CHAR = "char";
    public static final String VARCHAR = "varchar";
    public static final String BOOLEAN = "boolean";
    public static final String DATE = "date";

    public static final String DATEFORMAT = "MM/dd/yyyy KK:mm:ss aa";
    public static final String BRACKET_B = "(";
    public static final String BRACKET_A = ")";
    public static final String SEPARATOR = ",";
    public static final String POINT = ".";
    public static final String NULL = " ";
    public static final int BUFFERSIZE = 1024 * 1024 * 2;//读取数据流文件缓冲区大小
    public static final int TBUFFERSIZE = 1024 * 1024 * 16;

    public static final String PAGENAME = "heap";
    public static final String INDEXNAME = "index";
    public static int RECORDLENGTH = 0;
    public static int TREESIZE = 80000;
    public static String KEYWORDS;//查找数据流文件的key
    public static String I_KEYWORDS;
    public static String[] RANGS_KEYS = new String[2];//query区间key
    public static String[] R_RANGS_KEYS = new String[2];

    public static final Map<String, String> defindTable = new LinkedHashMap<> ();//初始化表结构
    public static final List<Map<String, Object>> tableInfo = new ArrayList<> ();//加载元数据对比表头，固定字段顺序

    static {//表结构
        defindTable.put ("DeviceId", TableConfig.INT + "(4)");
        defindTable.put ("ArrivalTime", TableConfig.DATE + "(8)");
        defindTable.put ("DepartureTime", TableConfig.DATE + "(8)");
        defindTable.put ("DurationSeconds", TableConfig.LONG + "(8)");
        defindTable.put ("StreetMarker", TableConfig.CHAR + "(6)");
        defindTable.put ("Sign", TableConfig.CHAR + "(40)");
        defindTable.put ("Area", TableConfig.CHAR + "(20)");
        defindTable.put ("StreetId", TableConfig.INT + "(4)");
        defindTable.put ("StreetName", TableConfig.CHAR + "(30)");
        defindTable.put ("BetweenStreet1", TableConfig.CHAR + "(30)");
        defindTable.put ("BetweenStreet2", TableConfig.CHAR + "(30)");
        defindTable.put ("Side Of Street", TableConfig.INT + "(4)");
        defindTable.put ("In Violation", TableConfig.BOOLEAN + "(1)");
        defindTable.put ("Vehicle Present", TableConfig.BOOLEAN + "(1)");
    }

    //加载元数据初始化表结构
    public static void initTableInfo(String... fields) {
        Stream.of (fields).forEach (f -> {
            String attr = defindTable.get (f);
            initTable(f, attr);
        });
    }

    //加载数据流文件初始化表结构
    public static void initTableInfo() {
        TableConfig.defindTable.forEach ((k, v) -> TableConfig.initTable (k, v));
    }

    public static void initRangKeys() {
        if (RANGS_KEYS[0] == null || RANGS_KEYS[1] == null) return;
        R_RANGS_KEYS[0] = parseKeyWord(RANGS_KEYS[0]);
        R_RANGS_KEYS[1] = parseKeyWord(RANGS_KEYS[1]);
    }

    public static void initKeyWords() {
        if (KEYWORDS == null) return;
        I_KEYWORDS = parseKeyWord (KEYWORDS);
    }

    private static String parseKeyWord(String key) {
        String[] split = key.split (NULL);
        StringBuilder result = new StringBuilder (CompareUtil.parseId (split[0]));
        try {
            result.append (DateUtil.regex (CompareUtil.parseTime (split[0]) + NULL + split[1] + NULL + split[2]));
        } catch (ParseException e) {
            System.out.println("Date resolution error,Please enter the correct date");
        }
        return result.toString ();
    }

    private static void initTable(String fieldName, String attr) {
        String type = TypeUtil.getType (attr);
        int length = TypeUtil.getTypeLen (attr);
        HashMap<String, Object> map = new HashMap<> ();
        map.put (TableConfig.NAME, fieldName);
        map.put (TableConfig.TYPE, type);
        map.put (TableConfig.LENGTH, length);
        tableInfo.add (map);
        TableConfig.RECORDLENGTH = TableConfig.RECORDLENGTH + length;
    }
}
