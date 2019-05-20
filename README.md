
### Command for loading data
java dbload -p pagesize datafile
### query without the index
- Equality query:
java dbquery "string to query" pagesize
E.g：java dbquery "1861901/04/2017 08:10:06 PM" 4096

- Range query：
java dbquery "String 1" "String 2" pagesize
E.g：java dbquery "1718510/10/2017 09:56:59 PM" "1719007/27/2017 11:25:02 AM" 4096

### Search with the index
- equality search：
java dbsearch "String to query" pagesize
E.g：java dbsearch "1861901/04/2017 08:10:06 PM" 4096

- Range search： 
java dbsearch "string 1" "String 2" pagesize 
E.g：java dbsearch "1718510/10/2017 09:56:59 PM" "1719007/27/2017 11:25:02 AM" 4096

