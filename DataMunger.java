package com.stackroute.datamunger;


/*There are total 5 DataMungertest files:
 * 
 * 1)DataMungerTestTask1.java file is for testing following 3 methods
 * a)getSplitStrings()  b) getFileName()  c) getBaseQuery()
 * 
 * Once you implement the above 3 methods,run DataMungerTestTask1.java
 * 
 * 2)DataMungerTestTask2.java file is for testing following 3 methods
 * a)getFields() b) getConditionsPartQuery() c) getConditions()
 * 
 * Once you implement the above 3 methods,run DataMungerTestTask2.java
 * 
 * 3)DataMungerTestTask3.java file is for testing following 2 methods
 * a)getLogicalOperators() b) getOrderByFields()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask3.java
 * 
 * 4)DataMungerTestTask4.java file is for testing following 2 methods
 * a)getGroupByFields()  b) getAggregateFunctions()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask4.java
 * 
 * Once you implement all the methods run DataMungerTest.java.This test case consist of all
 * the test cases together.
 */

public class DataMunger {

	/*
	 * This method will split the query string based on space into an array of words
	 * and display it on console
	 */

	public String[] getSplitStrings(String queryString) {
		queryString=queryString.toLowerCase();
		String [] arrOfStr = queryString.split(" ", -2);

		return arrOfStr;
	}

	/*
	 * Extract the name of the file from the query. File name can be found after a
	 * space after "from" clause. Note: ----- CSV file can contain a field that
	 * contains from as a part of the column name. For eg: from_date,from_hrs etc.
	 * 
	 * Please consider this while extracting the file name in this method.
	 */

	public String getFileName(String queryString) {
	    int pos1 = queryString.indexOf("from") + 5;
	    int pos2 = queryString.indexOf("csv") + 3;
	    
	    queryString=queryString.substring(pos1, pos2);
		
	    return queryString;
	}

	/*
	 * This method is used to extract the baseQuery from the query string. BaseQuery
	 * contains from the beginning of the query till the where clause
	 * 
	 * Note: ------- 1. The query might not contain where clause but contain order
	 * by or group by clause 2. The query might not contain where, order by or group
	 * by clause 3. The query might not contain where, but can contain both group by
	 * and order by clause
	 */
	
	public String getBaseQuery(String queryString) {

	    int pos1 = queryString.indexOf("from") + 5;
	    int pos2 = queryString.indexOf(" ",pos1);
	    if(pos2 == -1)
	    	pos2 = queryString.length();
	    	
	    
	    queryString = queryString.substring(0,pos2);

		return queryString;
	}

	/*
	 * This method will extract the fields to be selected from the query string. The
	 * query string can have multiple fields separated by comma. The extracted
	 * fields will be stored in a String array which is to be printed in console as
	 * well as to be returned by the method
	 * 
	 * Note: 1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The field
	 * name can contain '*'
	 * 
	 */
	
	public String[] getFields(String queryString) {
		int pos1 = 7;
		int pos2=queryString.indexOf(" ",pos1);
		
		queryString=queryString.substring(pos1, pos2);
		String[] str = queryString.split(",",-2);

		return str;
	}

	/*
	 * This method is used to extract the conditions part from the query string. The
	 * conditions part contains starting from where keyword till the next keyword,
	 * which is either group by or order by clause. In case of absence of both group
	 * by and order by clause, it will contain till the end of the query string.
	 * Note:  1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The query
	 * might not contain where clause at all.
	 */
	
	public String getConditionsPartQuery(String queryString) {

		queryString=queryString.toLowerCase();
		int startpos = queryString.indexOf("where"),endpos;
		if(startpos==-1)
			return "";
		
		startpos+=6;
		
		int GroupBy=queryString.indexOf("group");
		int OrderBy=queryString.indexOf("order");
		
		if(GroupBy==-1&&OrderBy==-1)
			endpos=queryString.length();
		else if(GroupBy==-1&&OrderBy!=-1)
			endpos=OrderBy-1;
		else if(GroupBy!=-1&&OrderBy==-1)
			endpos=GroupBy-1;
		else
			endpos=(GroupBy<OrderBy)?GroupBy:OrderBy;
		
		String str=queryString.substring(startpos, endpos);
		return str;
	}

	/*
	 * This method will extract condition(s) from the query string. The query can
	 * contain one or multiple conditions. In case of multiple conditions, the
	 * conditions will be separated by AND/OR keywords. for eg: Input: select
	 * city,winner,player_match from ipl.csv where season > 2014 and city
	 * ='Bangalore'
	 * 
	 * This method will return a string array ["season > 2014","city ='bangalore'"]
	 * and print the array
	 * 
	 * Note: ----- 1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The query
	 * might not contain where clause at all.
	 */

	public String[] getConditions(String queryString) {
		String str = getConditionsPartQuery(queryString);
		if(str=="")
			return null;

		String[] output=str.split(" and | or ");

		return output;
	}

	/*
	 * This method will extract logical operators(AND/OR) from the query string. The
	 * extracted logical operators will be stored in a String array which will be
	 * returned by the method and the same will be printed Note:  1. AND/OR
	 * keyword will exist in the query only if where conditions exists and it
	 * contains multiple conditions. 2. AND/OR can exist as a substring in the
	 * conditions as well. For eg: name='Alexander',color='Red' etc. Please consider
	 * these as well when extracting the logical operators.
	 * 
	 */

	public String[] getLogicalOperators(String queryString) {
		int pos1=queryString.indexOf(" and ");
		int pos2=queryString.indexOf(" or ");
		String str="";
		if(pos1==-1&&pos2==-1)
			return null;
		else if(pos1==-1)
			str+="or";
		else if(pos2==-1)
			str+="and";
		else
			str+="and or";
		String[] output=str.split(" ",-2);

		return output;
	}

	/*
	 * This method extracts the order by fields from the query string. Note: 
	 * 1. The query string can contain more than one order by fields. 2. The query
	 * string might not contain order by clause at all. 3. The field names,condition
	 * values might contain "order" as a substring. For eg:order_number,job_order
	 * Consider this while extracting the order by fields
	 */

	public String[] getOrderByFields(String queryString) {
		int pos1 = queryString.indexOf("order by");
		if(pos1==-1)
			return null;
		pos1 += 9;
		int pos2 = queryString.indexOf(" ", pos1);
		if(pos2==-1)
			pos2 = queryString.length();
		
	    queryString = queryString.substring(pos1, pos2);
	    String [] str = queryString.split(",",-2);

		return str;
	}

	/*
	 * This method extracts the group by fields from the query string. Note:
	 * 1. The query string can contain more than one group by fields. 2. The query
	 * string might not contain group by clause at all. 3. The field names,condition
	 * values might contain "group" as a substring. For eg: newsgroup_name
	 * 
	 * Consider this while extracting the group by fields
	 */

	public String[] getGroupByFields(String queryString) {

		int pos1 = queryString.indexOf("group by");
		if(pos1==-1)
			return null;
		pos1 += 9;
		int pos2 = queryString.indexOf(" ", pos1);
		if(pos2==-1)
			pos2 = queryString.length();
		
	    queryString = queryString.substring(pos1, pos2);
	    String [] str = queryString.split(",",-2);

		return str;
	}

	/*
	 * This method extracts the aggregate functions from the query string. Note:
	 *  1. aggregate functions will start with "sum"/"count"/"min"/"max"/"avg"
	 * followed by "(" 2. The field names might
	 * contain"sum"/"count"/"min"/"max"/"avg" as a substring. For eg:
	 * account_number,consumed_qty,nominee_name
	 * 
	 * Consider this while extracting the aggregate functions
	 */

	public String[] getAggregateFunctions(String queryString) {
		
		String[] str = getFields(queryString);
		String output="";
		
		for(int i=0;i<str.length;i++)
		{
			if(str[i].startsWith("count",0)||str[i].startsWith("sum",0)||str[i].startsWith("min",0)||
					str[i].startsWith("max",0)||str[i].startsWith("avg",0))
			output += str[i] + " ";
		}
		
		if(output=="")
			return null;
		output=output.substring(0,output.length()-1);
		str=output.split(" ",-2);
		return str;
	}

}