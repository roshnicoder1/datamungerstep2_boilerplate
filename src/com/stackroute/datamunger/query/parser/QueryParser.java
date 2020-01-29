package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/*There are total 4 DataMungerTest file:
 * 
 * 1)DataMungerTestTask1.java file is for testing following 4 methods
 * a)getBaseQuery()  b)getFileName()  c)getOrderByClause()  d)getGroupByFields()
 * 
 * Once you implement the above 4 methods,run DataMungerTestTask1.java
 * 
 * 2)DataMungerTestTask2.java file is for testing following 2 methods
 * a)getFields() b) getAggregateFunctions()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask2.java
 * 
 * 3)DataMungerTestTask3.java file is for testing following 2 methods
 * a)getRestrictions()  b)getLogicalOperators()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask3.java
 * 
 * Once you implement all the methods run DataMungerTest.java.This test case consist of all
 * the test cases together.
 */

public class QueryParser extends QueryParameter{

	private QueryParameter queryParameter = new QueryParameter();

	/*
	 * This method will parse the queryString and will return the object of
	 * QueryParameter class
	 */
	public QueryParameter parseQuery(String queryString) {
		queryParameter.queryString=queryString;
		getFileName(queryParameter);
		getBaseQuery(queryParameter);
		getOrderByFields(queryParameter);
		getGroupByFields(queryParameter);
		getFields(queryParameter);
		getAggregateFunctions(queryParameter);
		getRestrictions(queryParameter);
		getLogicalOperators(queryParameter);
		
		
	
		
		
		

		return queryParameter;
	}

	/*
	 * Extract the name of the file from the query. File name can be found after the
	 * "from" clause.
	 */
	
	public String getFileName(QueryParameter qp) {
		String s=qp.queryString;
		String st[]= s.split(" ");
		String st1=" ";
		for(int i=0;i<st.length;i++) {
			if(st[i].equals("from")) {
				st1=st[i+1];
				
		   }
			
		}
		qp.file=st1;
		
		return qp.file;
	}



	/*
	 * 
	 * Extract the baseQuery from the query.This method is used to extract the
	 * baseQuery from the query string. BaseQuery contains from the beginning of the
	 * query till the where clause
	 */

	public String getBaseQuery(QueryParameter qp) {
		String s=qp.queryString;

		String str[]=s.split(" ");
	       String str1="";
	       for(int i=0;i<str.length;i++)
	    	   if(str[i].equals("where") || str[i].equals("order") || str[i].equals("group"))
	    		   break;
	    	   else
	    		   str1=str1+" "+str[i];
	       qp.baseQuery=str1.trim();
			
	       return qp.baseQuery;
		
		
		
	}

	
	/*
	 * extract the order by fields from the query string. Please note that we will
	 * need to extract the field(s) after "order by" clause in the query, if at all
	 * the order by clause exists. For eg: select city,winner,team1,team2 from
	 * data/ipl.csv order by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one order by fields.
	 */
	
	public List<String> getOrderByFields(QueryParameter qp) {
		String s=qp.queryString.toLowerCase();
		String[] fields=s.split(" order by ");
		if(fields.length>1) {
			String[] answer=fields[1].split(",");
			List<String> ans=Arrays.asList(answer);
			qp.orderByFields=ans;
			return qp.orderByFields;
		}else
			return null;
	  }
	
	

	/*
	 * Extract the group by fields from the query string. Please note that we will
	 * need to extract the field(s) after "group by" clause in the query, if at all
	 * the group by clause exists. For eg: select city,max(win_by_runs) from
	 * data/ipl.csv group by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one group by fields.
	 */

	public List<String> getGroupByFields(QueryParameter qp) {

		 String str[]=qp.queryString.split(" ");
		 int i=0;int s=0;
		 String res="";
		 for(i=0;i<str.length;i++)
				 if(str[i].equals("group") && str[i+1].equals("by")) {
					 s++;
					 break;
				 }
		 if(s>0) {
		 i=i+2;
		    for(int j=i;j<str.length;j++)
		    	if(str[j].equals("order"))
		    		break;
		    	else
		    		res=res+" "+str[j];
		    String ss=res.trim().toLowerCase();
		    String sss[]=ss.split(" group by ");
		    List<String> ans=Arrays.asList(sss);
		    qp.groupByFields=ans;
		    return qp.groupByFields;
		 }
		 else
			 return null;

	}
	/*
	 * Extract the selected fields from the query string. Please note that we will
	 * need to extract the field(s) after "select" clause followed by a space from
	 * the query string. For eg: select city,win_by_runs from data/ipl.csv from the
	 * query mentioned above, we need to extract "city" and "win_by_runs". Please
	 * note that we might have a field containing name "from_date" or "from_hrs".
	 * Hence, consider this while parsing.
	 */

	public List<String> getFields(QueryParameter qp) {
		String s=qp.queryString;
		String st[]= s.split(" ");
		if(st.length==1)
			return null;
		else {
		String st1[]=st[1].split(",");
		 List<String> ans=Arrays.asList(st1);
		 qp.fields=ans;
		 return qp.fields;
		}
		 
		

		
	}
	
	/*
	 * Extract the conditions from the query string(if exists). for each condition,
	 * we need to capture the following: 1. Name of field 2. condition 3. value
	 * 
	 * 
	 * For eg: select city,winner,team1,team2,player_of_match from data/ipl.csv
	 * where season >= 2008 or toss_decision != bat
	 * 
	 * here, for the first condition, "season>=2008" we need to capture: 1. Name of
	 * field: season 2. condition: >= 3. value: 2008
	 * 
	 * the query might contain multiple conditions separated by OR/AND operators.
	 * Please consider this while parsing the conditions.
	 * 
	 */
	
	public List<AggregateFunction> getAggregateFunctions(QueryParameter qp) {
		String s=qp.queryString;
		List<AggregateFunction> aggregateFunctionList = new ArrayList<>();
		

		String str[]=s.split(" ");
		String st[]=str[1].split(",");
		String ss="";
        for(int i=0;i<st.length;i++)
        	if(st[i].charAt(st[i].length()-1)==')') {        		
        		ss=ss+st[i]+" ";
        	}
        if(ss.length()!=0) {
		 
		StringTokenizer multiTokenizer = new StringTokenizer(ss, "()  ");
		
		
		 
		while (multiTokenizer.hasMoreTokens())
		{
		        String var1=multiTokenizer.nextToken().toString();
		        String var2=multiTokenizer.nextToken().toString();
		        aggregateFunctionList.add(new AggregateFunction(var2,var1));
	
		}
		qp.aggregateFunctions=aggregateFunctionList;
		

        return qp.aggregateFunctions;
        }
        else
        	return null;
	
	}
	
	
	public List<Restriction> getRestrictions(QueryParameter qp) {
		String query=qp.queryString;
		List<Restriction> restrictionList = new ArrayList<>();
		String s=query.trim();
        String f[]=s.trim().split("where|WHERE");
        
        if(f.length==1)
            {return qp.restrictions=null ;
            }
        else {
        String s1[]=f[1].trim().split("order by|group by|Group By|GROUP BY|ORDER BY|Order By");
        String s2[]=s1[0].trim().split(" and | or | AND | OR ");
        for(int k=0;k<s2.length;k++) {
        	s2[k]=s2[k].trim();
        	}
       
	            
	            for(String r : s2) {
	            	String cond="";
		            String nam="";
		            String val="";
	            	if(r.contains("<")) {
	            		cond="<";
	            		
	            		
	            		
	            	}
	            	else if(r.contains(">")) {
	            		cond=">";
	            	}
	            	else if(r.contains("=")) {
	            		cond="=";
	            	}
	            	else if(r.contains(">=")) {
	            		cond=">=";
	            	}
	            	else if(r.contains("<=")) {
	            		cond="<=";
	            	}
	            	else if(r.contains("!=")) {
	            		cond="!=";
	            	}
	            	nam=r.split(cond)[0].trim();
	            	val=r.split(cond)[1].trim().replaceAll("'","");
//	            	String val3=val.split("[']")[0];
	            	
	            
	            	
	            	
	            	restrictionList.add(new Restriction(nam.trim(),val,cond.trim()));
	            	}
	            qp.restrictions=restrictionList;
	            
	            
	            

	       return qp.restrictions;
        }
	  
		
	
	}

	

	/*
	 * Extract the logical operators(AND/OR) from the query, if at all it is
	 * present. For eg: select city,winner,team1,team2,player_of_match from
	 * data/ipl.csv where season >= 2008 or toss_decision != bat and city =
	 * bangalore
	 * 
	 * The query mentioned above in the example should return a List of Strings
	 * containing [or,and]
	 */

	/*
	 * Extract the aggregate functions from the query. The presence of the aggregate
	 * functions can determined if we have either "min" or "max" or "sum" or "count"
	 * or "avg" followed by opening braces"(" after "select" clause in the query
	 * string. in case it is present, then we will have to extract the same. For
	 * each aggregate functions, we need to know the following: 1. type of aggregate
	 * function(min/max/count/sum/avg) 2. field on which the aggregate function is
	 * being applied.
	 * 
	 * Please note that more than one aggregate function can be present in a query.
	 * 
	 * 
	 */
	
	public List<String> getLogicalOperators(QueryParameter qp) {
		String query=qp.queryString;
		queryString=query.toLowerCase();
		String[] st = queryString.split(" ");
		String ans = "";
		if (queryString.contains("where ")) {
			for (int i = 0; i < st.length; i++) {
				if (st[i].matches("and|or|not")) {
					ans += st[i] + " ";
				}
			}
			List<String> ans1=Arrays.asList(ans.toString().split(" "));
			qp.logicalOperators=ans1;
			return  qp.logicalOperators;
		}
		return qp.logicalOperators=null;

	}
	
}