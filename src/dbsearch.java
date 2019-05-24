import config.Condition;
import config.TableConfig;
import operate.Search;

import java.io.IOException;


/**
 * - equality search：
 *   java dbsearch "String to query" pagesize
 *   E.g：java dbsearch "1861901/04/2017 08:10:06 PM" 4096
 *
 * - Range search：
 *   java dbsearch "string 1" "String 2" pagesize
 *   E.g：java dbsearch "1718510/10/2017 09:56:59 PM" "1719007/27/2017 11:25:02 AM" 4096
 */
public class dbsearch {
    public static void main(String[] args) {
        if (args.length != 2 && args.length != 3) {
            System.out.println("The parameter must have two(equality) or three(range)");
            return;
        }

        Condition condition = null;
        String size = null;
        if (args.length == 2) {
            condition = Condition.EQUALITY;
            TableConfig.KEYWORDS = args[0].trim ();
            if (TableConfig.KEYWORDS == null || TableConfig.KEYWORDS.isEmpty ()) {
                System.out.println("Keywords is required");
                return;
            }
            size = args[1].trim ();
            if (size == null || size.isEmpty ()) {
                System.out.println("PageSize is required");
                return;
            }
        }else if (args.length == 3) {
            condition = Condition.RANGE;
            String key1 = args[0].trim ();
            String key2 = args[1].trim ();
            if (key1 == null || key1.isEmpty () || key2 == null || key2.isEmpty ()) {
                System.out.println("Keywords is required");
                return;
            }
            int i = key1.compareTo (key2);
            if (i == 0) {
                System.out.println("Two keywords are equal");
                return;
            }
            if (i > 0) {
                TableConfig.RANGS_KEYS[0] = key2;
                TableConfig.RANGS_KEYS[1] = key1;
            } else {
                TableConfig.RANGS_KEYS[0] = key1;
                TableConfig.RANGS_KEYS[1] = key2;
            }

            size = args[2].trim ();
            if (size == null || size.isEmpty ()) {
                System.out.println("PageSize is required");
                return;
            }
        }

        int pageSize = 0;
        try {
            pageSize = Integer.parseInt (size);
        } catch (Exception e) {
            System.out.println("The size of page need int type");
        }
        Search search = null;
        try {
            long start = System.currentTimeMillis ();
            search = new Search (pageSize);
            search.search (condition);
            long stop = System.currentTimeMillis ();
            System.out.println("The number of milliseconds to search the heap file is " + (stop - start) + "ms");

        } catch (Exception e) {
            e.printStackTrace ();
        } finally {
            if (search != null) {
                try {
                    search.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
        }
    }
}
