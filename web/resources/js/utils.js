var Utils = {
	getLastedMonthDate:function(){
		 var date = new Date();
		 var month = date.getMonth()-1;
		 var year = date.getFullYear();
		 var day =   date.getDate();
		 return new Date(year,month,day);
		 // return new Date(2009,1,22);
	},
	getLastedYearDate:function(){
		 var date = new Date();
		 var month = date.getMonth();
		 var year = date.getFullYear()-1;
		 var day =   date.getDate();
		 return new Date(year,month,day);
		 // return new Date(2009,1,22);
	}	
};
