package element.pages;

import element.records.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Data file page
 */
public class Page {
    private final List<Record> list;
    private final long pageId;
    private int recordNum;

    public Page(long pageId, int recordNum) {
        this.pageId = pageId;
        this.recordNum = recordNum;
        this.list = new ArrayList<> ();
    }

    public long getPageId() {
        return this.pageId;
    }

    public int getRecordNum() {
        return this.recordNum;
    }

    public List<Record> getList() {
        return this.list;
    }
}
