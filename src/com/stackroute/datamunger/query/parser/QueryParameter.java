package com.stackroute.datamunger.query.parser;

import java.util.List;

/* 
 * This class will contain the elements of the parsed Query String such as conditions,
 * logical operators,aggregate functions, file name, fields group by fields, order by
 * fields, Query Type
 * */
public class QueryParameter {
	protected String queryString;
	protected String file;
	protected String baseQuery;
	protected List<String> fields;
	protected String QUERY_TYPE ;
	protected List<Restriction> restrictions ;
	protected List<String> logicalOperators ;
	protected List<AggregateFunction>  aggregateFunctions;   
	protected List<String> orderByFields;
	protected List<String> groupByFields;
	
	
	public String getFileName() {
		if(this.file!=null)
			return file;
		else
			return null;
	}

	public String getBaseQuery() {
		if(this.baseQuery!=null)
			return baseQuery;
		else
			return null;
	}

	public List<Restriction> getRestrictions() {
		if(this.restrictions!=null)
			return this.restrictions;
		else
			return null;
	}
//	public void getRestriction() {
//		
//	
//	}

	public List<String> getLogicalOperators() {
		if(this.logicalOperators!=null)
			return logicalOperators;
		else
			return null;
	}

	public List<String> getFields() {
		if(this.fields!=null)
			return fields;
		else
			return null;
	}

	public List<AggregateFunction> getAggregateFunctions() {
		if(this.aggregateFunctions!=null)
			return this.aggregateFunctions;
		else
			return null;
	}

	public List<String> getGroupByFields() {
		if(this.groupByFields!=null)
			return groupByFields;
		else
			return null;
	}

	public List<String> getOrderByFields() {
		if(this.orderByFields!=null) {
			return this.orderByFields;
			
		}
		else {
			return null;
		}
		
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public void setBaseQuery(String baseQuery) {
		this.baseQuery = baseQuery;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public void setQUERY_TYPE(String qUERY_TYPE) {
		QUERY_TYPE = qUERY_TYPE;
	}

	public void setRestrictions(List<Restriction> restrictions) {
		this.restrictions = restrictions;
	}

	public void setLogicalOperators(List<String> logicalOperators) {
		this.logicalOperators = logicalOperators;
	}

	public void setAggregateFunctions(List<AggregateFunction> aggregateFunctions) {
		this.aggregateFunctions = aggregateFunctions;
	}

	public void setOrderByFields(List<String> orderByFields) {
		this.orderByFields = orderByFields;
	}

	public void setGroupByFields(List<String> groupByFields) {
		this.groupByFields = groupByFields;
	}
	
	
}